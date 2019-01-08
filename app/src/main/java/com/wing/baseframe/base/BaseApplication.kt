package com.wing.baseframe.base

import android.app.Activity
import android.app.Application
import android.content.Context
import com.feather.LogControl
import java.util.ArrayList

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.applicationContext=this
        LogControl.init(getApplicationContext(),"aaa",false);
    }

    companion object {
        var list = ArrayList<Activity>()
        fun addActivity(activity: Activity) {
            list.add(activity)
        }

        fun getActivityList(): List<Activity> {
            return list
        }



    }



}