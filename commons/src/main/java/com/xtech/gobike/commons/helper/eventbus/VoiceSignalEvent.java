package com.xtech.gobike.commons.helper.eventbus;

public class VoiceSignalEvent extends SignalEvent {
    private VoiceCmd voiceCmd;

    public VoiceCmd getVoiceCmd() {
        return voiceCmd;
    }

    public void setVoiceCmd(VoiceCmd voiceCmd) {
        this.voiceCmd = voiceCmd;
    }
}
