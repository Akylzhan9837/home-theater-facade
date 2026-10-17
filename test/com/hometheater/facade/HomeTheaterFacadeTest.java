
package com.hometheater.facade;

import com.hometheater.exception.HomeTheaterException;
import com.hometheater.subsystem.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HomeTheaterFacadeTest {

    @Test
    void shouldStartMovieSuccessfully() {
        Amplifier amplifier = new Amplifier();
        DvdPlayer dvdPlayer = new DvdPlayer();
        Lights lights = new Lights();
        Projector projector = new Projector();
        Screen screen = new Screen();

        HomeTheaterFacade homeTheater =
                new HomeTheaterFacade(
                        amplifier, dvdPlayer, lights,
                        projector, screen
                );

        homeTheater.watchMovie("Interstellar");

        assertTrue(homeTheater.isMoviePlaying());
        assertTrue(amplifier.isPoweredOn());
        assertTrue(projector.isPoweredOn());
        assertTrue(dvdPlayer.isPoweredOn());
        assertTrue(screen.isLowered());
        assertEquals(20, lights.getBrightness());
        assertEquals("Interstellar", dvdPlayer.getCurrentMovie());
    }

    @Test
    void shouldEndMovieSuccessfully() {
        Amplifier amplifier = new Amplifier();
        DvdPlayer dvdPlayer = new DvdPlayer();
        Lights lights = new Lights();
        Projector projector = new Projector();
        Screen screen = new Screen();

        HomeTheaterFacade homeTheater =
                new HomeTheaterFacade(
                        amplifier, dvdPlayer, lights,
                        projector, screen
                );

        homeTheater.watchMovie("Interstellar");
        homeTheater.endMovie();

        assertFalse(homeTheater.isMoviePlaying());
        assertFalse(amplifier.isPoweredOn());
        assertFalse(projector.isPoweredOn());
        assertFalse(dvdPlayer.isPoweredOn());
        assertFalse(screen.isLowered());
        assertEquals(100, lights.getBrightness());
        assertNull(dvdPlayer.getCurrentMovie());
    }

    @Test
    void shouldRejectEmptyMovieTitle() {
        HomeTheaterFacade homeTheater = createHomeTheater();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> homeTheater.watchMovie("")
        );

        assertEquals(
                "Movie title cannot be empty",
                exception.getMessage()
        );
        assertFalse(homeTheater.isMoviePlaying());
    }

    @Test
    void shouldRejectStartingSecondMovie() {
        HomeTheaterFacade homeTheater = createHomeTheater();

        homeTheater.watchMovie("Interstellar");

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> homeTheater.watchMovie("Avatar")
        );

        assertEquals(
                "A movie is already playing",
                exception.getMessage()
        );
        assertTrue(homeTheater.isMoviePlaying());
    }

    @Test
    void shouldRejectEndingMovieWhenNotPlaying() {
        HomeTheaterFacade homeTheater = createHomeTheater();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                homeTheater::endMovie
        );

        assertEquals(
                "No movie is currently playing",
                exception.getMessage()
        );
        assertFalse(homeTheater.isMoviePlaying());
    }

    @Test
    void shouldShutdownDevicesWhenDvdPlayerFails() {
        Amplifier amplifier = new Amplifier();
        Lights lights = new Lights();
        Projector projector = new Projector();
        Screen screen = new Screen();

        DvdPlayer brokenDvdPlayer = new DvdPlayer() {
            @Override
            public void play(String movie) {
                throw new IllegalStateException(
                        "DVD player hardware failure"
                );
            }
        };

        HomeTheaterFacade homeTheater =
                new HomeTheaterFacade(
                        amplifier, brokenDvdPlayer,
                        lights, projector, screen
                );

        HomeTheaterException exception = assertThrows(
                HomeTheaterException.class,
                () -> homeTheater.watchMovie("Interstellar")
        );

        assertEquals(
                "Failed to start movie: Interstellar",
                exception.getMessage()
        );
        assertEquals(
                "DVD player hardware failure",
                exception.getCause().getMessage()
        );

        assertFalse(homeTheater.isMoviePlaying());
        assertFalse(amplifier.isPoweredOn());
        assertFalse(projector.isPoweredOn());
        assertFalse(brokenDvdPlayer.isPoweredOn());
        assertFalse(screen.isLowered());
        assertEquals(100, lights.getBrightness());
    }

    private HomeTheaterFacade createHomeTheater() {
        return new HomeTheaterFacade(
                new Amplifier(),
                new DvdPlayer(),
                new Lights(),
                new Projector(),
                new Screen()
        );
    }
}
