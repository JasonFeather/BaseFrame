package com.wing.baseframe.http

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

interface OKHttpCallbackAndProgress {

    /**
     * 通讯成功，返回正常的数据时回调的方法
     *
     * @param result 返回信息
     */
    abstract fun onSuccess(result: String)

    /**
     * 请求失败、拦截到错误等，回调的方法
     *
     * @param message 提示信息
     * * @param code 返回码
     */
    abstract fun onError(code: Int, message: String)

    /**
     * 请求结束回调的方法
     */
    abstract fun onFinished()

    /**
     * 进度
     * @param currentBytes
     * @param totalBytes
     */
    abstract fun onProgress(currentBytes: Long, totalBytes: Long)

}