package com.wing.baseframe.listener

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

interface PermissionListener {
    //授权成功的接口
     fun onGranted()
    //授权失败的接口
     fun onFailure(failurePression: List<String>)
}