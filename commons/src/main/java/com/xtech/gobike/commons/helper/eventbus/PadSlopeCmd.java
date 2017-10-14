package com.xtech.gobike.commons.helper.eventbus;


public class PadSlopeCmd extends PadCmd {
    private int slope;

    public PadSlopeCmd(int slope) {
        this.slope = slope;
    }

    public int getSlope() {
        return slope;
    }

    public void setSlope(int slope) {
        this.slope = slope;
    }

    @Override
    public String toString() {
        return "PadSlopeCmd{" +
                "slope=" + slope +
                '}';
    }
}
