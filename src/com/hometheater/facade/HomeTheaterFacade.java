package com.hometheater.facade;

import com.hometheater.exception.HomeTheaterException;
import com.hometheater.subsystem.Amplifier;
import com.hometheater.subsystem.DvdPlayer;
import com.hometheater.subsystem.Lights;
import com.hometheater.subsystem.Projector;
import com.hometheater.subsystem.Screen;

public class HomeTheaterFacade {

    private final Amplifier amplifier;
    private final DvdPlayer dvdPlayer;
    private final Lights lights;
    private final Projector projector;
    private final Screen screen;

    private boolean moviePlaying = false;

    public HomeTheaterFacade(
            Amplifier amplifier,
            DvdPlayer dvdPlayer,
            Lights lights,
            Projector projector,
            Screen screen) {

        this.amplifier = amplifier;
        this.dvdPlayer = dvdPlayer;
        this.lights = lights;
        this.projector = projector;
        this.screen = screen;
    }

    public void watchMovie(String movie) {

        if (moviePlaying) {
            throw new IllegalStateException(
                    "A movie is already playing"
            );
        }

        if (movie == null || movie.isBlank()) {
            throw new IllegalArgumentException(
                    "Movie title cannot be empty"
            );
        }

        try {
            System.out.println("Preparing home theater...");

            lights.dim(20);
            screen.down();

            projector.turnOn();
            projector.setWideScreenMode();

            amplifier.turnOn();
            amplifier.setVolume(50);

            dvdPlayer.turnOn();
            dvdPlayer.play(movie);

            moviePlaying = true;

            System.out.println("Enjoy your movie!");

        } catch (RuntimeException exception) {

            moviePlaying = false;

            try {
                dvdPlayer.turnOff();
            } catch (RuntimeException cleanupException) {
                exception.addSuppressed(cleanupException);
            }

            try {
                amplifier.turnOff();
            } catch (RuntimeException cleanupException) {
                exception.addSuppressed(cleanupException);
            }

            try {
                projector.turnOff();
            } catch (RuntimeException cleanupException) {
                exception.addSuppressed(cleanupException);
            }

            try {
                screen.up();
            } catch (RuntimeException cleanupException) {
                exception.addSuppressed(cleanupException);
            }

            try {
                lights.turnOn();
            } catch (RuntimeException cleanupException) {
                exception.addSuppressed(cleanupException);
            }

            throw new HomeTheaterException(
                    "Failed to start movie: " + movie,
                    exception
            );
        }
    }

    public void endMovie() {

        if (!moviePlaying) {
            throw new IllegalStateException(
                    "No movie is currently playing"
            );
        }

        System.out.println("Shutting down home theater...");

        dvdPlayer.stop();
        dvdPlayer.turnOff();

        amplifier.turnOff();
        projector.turnOff();

        screen.up();
        lights.turnOn();

        moviePlaying = false;

        System.out.println("Home theater is OFF");
    }

    public boolean isMoviePlaying() {
        return moviePlaying;
    }
}
