package com.xtech.gobike.commons.helper.eventbus;


public class PadSpeedCmd extends PadCmd {
    private int speed;

    public PadSpeedCmd(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
