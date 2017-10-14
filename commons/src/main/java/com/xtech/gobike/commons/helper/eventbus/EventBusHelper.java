package com.xtech.gobike.commons.helper.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dafei on 17/07/2017.
 */

public class EventBusHelper {
    private static EventBus defaultInstance;

    public static void setEventBus(EventBus eventBus) {
        defaultInstance = eventBus;
    }

    public static void sendKeyboardEvent(PadSignalEvent padSignalEvent) {
        defaultInstance.post(padSignalEvent);
    }

    public static void sendTimerStopEvent() {
        TimerStopEvent timerStopEvent = new TimerStopEvent();
        defaultInstance.post(timerStopEvent);
    }

    public static void sendRankEvent(RankEvent event) {
        defaultInstance.post(event);

    }
}
