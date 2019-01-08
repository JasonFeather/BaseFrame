package com.wing.baseframe.utils

import android.app.Activity
import android.view.View

/**
 *姓名：mengc
 *日期：2019/1/8
 *功能：
 */

object BarUtils {
    /**
     * 隐藏状态栏和虚拟按键拦
     *
     * @param activity
     */
    fun hideSystemUI(activity: Activity?) {
        if (activity == null) return
        val decorView = activity.window.decorView
        val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.systemUiVisibility = flags
        decorView.onFocusChangeListener =
                View.OnFocusChangeListener { v, hasFocus -> decorView.systemUiVisibility = flags }
    }
}