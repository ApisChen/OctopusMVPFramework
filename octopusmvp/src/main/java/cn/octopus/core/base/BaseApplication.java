package cn.octopus.core.base;

import android.support.multidex.MultiDexApplication;

/**
 * the base application of this framework please extends of this application.
 *
 * Created by JieGuo on 2017/8/2.
 */

public abstract class BaseApplication extends MultiDexApplication {

    private static BaseApplication INSTANCE;

    public static BaseApplication getInstance() {
        return INSTANCE;
    }

    public abstract String getBaseUrl();

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
    }
}
