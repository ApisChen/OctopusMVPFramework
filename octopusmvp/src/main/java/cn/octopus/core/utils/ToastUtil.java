package cn.octopus.core.utils;

import android.widget.Toast;

import cn.octopus.core.base.BaseApplication;

/**
 * Created by JieGuo on 2017/8/2.
 */

public class ToastUtil {

    public static void show(String message) {
        Toast.makeText(BaseApplication.getInstance(), message, Toast.LENGTH_SHORT)
                .show();
    }

    public static void show(String message, int duration) {
        Toast.makeText(BaseApplication.getInstance(), message, duration)
                .show();
    }
}
