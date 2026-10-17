
package com.hometheater.subsystem;

public class DvdPlayer {
    private boolean poweredOn = false;
    private String currentMovie = null;

    public void turnOn() {
        poweredOn = true;
        System.out.println("DVD Player turned ON");
    }

    public void play(String movie) {
        if (!poweredOn) {
            throw new IllegalStateException(
                    "Cannot play movie: DVD player is OFF"
            );
        }

        if (movie == null || movie.isBlank()) {
            throw new IllegalArgumentException(
                    "Movie title cannot be empty"
            );
        }

        currentMovie = movie;
        System.out.println("Playing movie: " + movie);
    }

    public void stop() {
        if (currentMovie != null) {
            System.out.println("Stopped movie: " + currentMovie);
            currentMovie = null;
        }
    }

    public void turnOff() {
        stop();
        poweredOn = false;
        System.out.println("DVD Player turned OFF");
    }

    public boolean isPoweredOn() {
        return poweredOn;
    }

    public String getCurrentMovie() {
        return currentMovie;
    }
}
