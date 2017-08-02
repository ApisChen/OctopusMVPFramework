package cn.octopus.core.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * utils
 * Created by JieGuo on 2017/3/16.
 */
public class Util {

    public static String formatString(String formatter, Object... values) {
        if (values == null || values.length < 1) {
            return formatter;
        }
        return String.format(Locale.CHINESE, formatter, values);
    }

    public static boolean hasSDcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();


    }

    public static boolean isChar(String str) {
        String reg = "^[A-Za-z]+$";
        return str.matches(reg);
    }

    public static void hideSoftKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getWindowToken(),
                0);
    }

    public static void showSoftKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
    }

    public static double formatPrice(double allMoney) {
        DecimalFormat df = new DecimalFormat("######0.##");
        if (allMoney > 10000) {
            allMoney = allMoney / 10000;
            allMoney = Double.valueOf(df.format(allMoney));
        } else {
            allMoney = Double.valueOf(df.format(allMoney));
        }
        return allMoney;
    }

    public static String concatString(CharSequence primaryString, CharSequence... str) {
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(primaryString)) {
            builder.append(primaryString);
        }

        for (CharSequence item : str) {
            if (!TextUtils.isEmpty(item)) {
                builder.append(item);
            }
        }
        return builder.toString();
    }
}
