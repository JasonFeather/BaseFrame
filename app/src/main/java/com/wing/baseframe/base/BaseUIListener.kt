package com.wing.baseframe.base

import android.view.View

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

interface BaseUIListener {
    /**
     * [绑定布局]
     *
     * @return   int 布局ID
     */
    fun bindLayout(): Int
    /**
     * [初始化控件]
     *
     * @param view
     */
    fun initView(view: View?)
    /**
     * [初始化控件之前初始化数据]
     */
    fun initDataBefore() {}
    /**
     * [初始化控件之后初始化数据]
     */
    fun initDataAfter()

    /**
     * [设置监听]
     */
    fun setListener()

    /**
     * View点击
     */
fun widgetClick(v: View)


}