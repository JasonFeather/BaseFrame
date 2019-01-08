package com.wing.baseframe.utils;

import android.app.Activity;
import android.view.View;

public final class BarUtils {

    /**
     * 隐藏状态栏和虚拟按键拦
     *
     * @param activity
     */
    public static void hideSystemUI(Activity activity) {
        if (activity == null) return;
        final View decorView = activity.getWindow().getDecorView();
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        decorView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                decorView.setSystemUiVisibility(flags);
            }
        });
    }

}