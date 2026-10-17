package com.hometheater.subsystem;

public class Amplifier {
    private boolean poweredOn = false;
    private int volume = 0;

    public void turnOn() {
        poweredOn = true;
        System.out.println("Amplifier turned ON");
    }

    public void setVolume(int volume) {
        if (!poweredOn) {
            throw new IllegalStateException(
                    "Cannot change volume: amplifier is OFF"
            );
        }

        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException(
                    "Volume must be between 0 and 100"
            );
        }

        this.volume = volume;
        System.out.println("Volume set to " + volume);
    }

    public void turnOff() {
        poweredOn = false;
        System.out.println("Amplifier turned OFF");
    }

    public boolean isPoweredOn() {
        return poweredOn;
    }

    public int getVolume() {
        return volume;
    }
}
