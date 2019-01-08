package com.wing.baseframe.http

import com.wing.baseframe.BuildConfig

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

object ConfigCommon {

    private val APP_DOMAIN_TEST = "http://dev.zuul.hcb66.com" //测试
    private val APP_DOMAIN_RELEASE = "http://newapi.hcb66.com"//正式

    val APP_ID_TEST = "5b1754183aa3f37aa05bb0f4"//测试
    val APP_ID_RELEASE = "5b1754183aa3f37aa05bb0f4"//正式


    fun getDomainUrl(): String {
        return APP_DOMAIN_TEST
    }

}