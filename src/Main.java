
package com.hometheater;

import com.hometheater.facade.HomeTheaterFacade;

public class Main {

    public static void main(String[] args) {
        HomeTheaterFacade homeTheater = new HomeTheaterFacade();

        homeTheater.watchMovie("Interstellar");

        System.out.println();

        homeTheater.endMovie();
    }
}
