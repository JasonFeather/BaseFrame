package com.wing.baseframe.base

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

interface BasePresenter {
    //初始化服务器数据
    fun initDate(url:String)
    //显示实现类
    fun showView()
}