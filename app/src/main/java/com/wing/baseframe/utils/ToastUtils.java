package com.wing.baseframe.utils;

import android.content.Context;
import android.graphics.Color;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;

/**
 * 姓名：mengc
 * 日期：2018/11/19
 * 功能：
 */

public class ToastUtils {
    public static SuperToast superToast;
    public static void getToast(Context context, String text) {
        if (superToast == null) {
            superToast = new SuperToast(context)
                    .setText(text)
                    .setTextSize(Style.TEXTSIZE_VERY_LARGE)
                    .setDuration(Style.DURATION_VERY_SHORT)
                    .setFrame(Style.FRAME_KITKAT)
                    .setColor(Color.parseColor("#FF9C27B0"))
                    .setAnimations(Style.ANIMATIONS_SCALE)
                    .setColor(Color.parseColor("#FF9C27B0"));
        } else {
            superToast.setText(text);
        }
        if (!superToast.isShowing()) {
            superToast.show();
        }
    }
}
