package com.xtech.gobike.commons.helper.eventbus;

public class PadSignalEvent extends SignalEvent {
    private PadCmd PadCmd;

    public PadCmd getPadCmd() {
        return PadCmd;
    }

    public void setPadCmd(PadCmd padCmd) {
        this.PadCmd = padCmd;
    }
}
