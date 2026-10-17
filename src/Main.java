package com.hometheater;

import com.hometheater.facade.HomeTheaterFacade;
import com.hometheater.subsystem.*;

public class Main {

    public static void main(String[] args) {

        HomeTheaterFacade homeTheater =
                new HomeTheaterFacade(
                        new Amplifier(),
                        new DvdPlayer(),
                        new Lights(),
                        new Projector(),
                        new Screen()
                );

        homeTheater.watchMovie("Interstellar");

        System.out.println();

        homeTheater.endMovie();
    }
}
