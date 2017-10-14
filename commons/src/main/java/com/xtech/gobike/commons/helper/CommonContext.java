package com.xtech.gobike.commons.helper;

import android.content.Context;

/**
 * Created by dafei on 13/07/2017.
 */

public class CommonContext {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        CommonContext.context = context;
    }
}
