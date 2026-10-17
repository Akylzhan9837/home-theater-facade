
package com.hometheater.subsystem;

public class Projector {
    private boolean poweredOn = false;

    public void turnOn() {
        poweredOn = true;
        System.out.println("Projector turned ON");
    }

    public void setWideScreenMode() {
        if (!poweredOn) {
            throw new IllegalStateException(
                    "Cannot set widescreen mode: projector is OFF"
            );
        }

        System.out.println("Projector set to widescreen mode");
    }

    public void turnOff() {
        poweredOn = false;
        System.out.println("Projector turned OFF");
    }

    public boolean isPoweredOn() {
        return poweredOn;
    }
}
