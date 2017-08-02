package cn.octopus.core.events;

import android.util.Log;


import org.greenrobot.eventbus.EventBus;

import cn.octopus.core.BuildConfig;

/**
 * Created by JieGuo on 2017/4/18.
 */

public class EventBusDelegate {

    private static final String TAG = "EventBusDelegate";

    public static void register(Object context) {
        try {
            EventBus.getDefault().register(context);
        } catch (Throwable e) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "error", e);
            }
            // ignore
        }
    }

    public static void unregister(Object context) {
        try {
            EventBus.getDefault().unregister(context);
        } catch (Throwable e) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "error", e);
            }
            // ignore
        }
    }
}
