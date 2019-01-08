package com.wing.baseframe.base

/**
 *姓名：mengc
 *日期：2019/1/8
 *功能：
 */

interface MvpBaseLister  {

    /**
     * 添加mvp模块
     */

    fun addMvp():BasePresenter
    /**
     * 实现类转换
     */
    fun presenterImp()

}