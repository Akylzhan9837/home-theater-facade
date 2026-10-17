
package com.hometheater.subsystem;

public class Screen {
    private boolean lowered = false;

    public void down() {
        lowered = true;
        System.out.println("Screen lowered");
    }

    public void up() {
        lowered = false;
        System.out.println("Screen raised");
    }

    public boolean isLowered() {
        return lowered;
    }
}
