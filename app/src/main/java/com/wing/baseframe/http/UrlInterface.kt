package com.wing.baseframe.http

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

object UrlInterface {

    fun getText():String{
        return ConfigCommon.getDomainUrl()+"/game/appHappyShy/actionList"
    }

}