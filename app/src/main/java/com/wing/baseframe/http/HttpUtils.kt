package com.wing.baseframe.http

import android.content.Context
import android.text.TextUtils
import com.alibaba.fastjson.JSON
import com.feather.LogControl
import com.feather.State.ErrorLog
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.tsy.sdk.myokhttp.MyOkHttp
import com.tsy.sdk.myokhttp.response.JsonResponseHandler
import com.tsy.sdk.myokhttp.response.RawResponseHandler
import com.wing.baseframe.base.GlobalContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap
import java.util.concurrent.TimeUnit

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

object HttpUtils {
     var mMyOkhttp: MyOkHttp? = null

     private fun getOKHttp(): MyOkHttp? {
        if (null == mMyOkhttp) {
            synchronized(MyOkHttp::class.java) {
                if (null == mMyOkhttp) {
                    mMyOkhttp = MyOkHttp(setHttpConfig())
                }
            }
        }
        return this!!.mMyOkhttp
    }


    private fun setHttpConfig(): OkHttpClient {
        //持久化存储cookie
        val cookieJar =
            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(GlobalContext.applicationContext))

        //log拦截器
        val logging = HttpLoggingInterceptor { message ->
            LogControl.setLogDate(ErrorLog::class.java).ShowLogAndWrite("OKHttp_HttpLoggingInterceptor", message)
        }
        logging.level = HttpLoggingInterceptor.Level.BODY
        //自定义OkHttp
        return OkHttpClient.Builder()
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS)
            .cookieJar(cookieJar)       //设置开启cookie
            .addInterceptor(logging)//设置开启log
            .build()
    }

    fun getHttp(
        context: Context, path: String, map: HashMap<String, String>, okHttpCallback: OKHttpCallback
    ): MyOkHttp? {
        val http = getOKHttp()
        if (http != null) {
            http.get().url(path).params(map).headers(getHeaders()).enqueue(object : RawResponseHandler() {
                override fun onSuccess(statusCode: Int, response: String) {
                    LogControl.setLogDate(ErrorLog::class.java)
                        .ShowLogAndWrite("OKHttp_onSuccess", "00000000000 $response")
                    if (statusCode == 200) {
                        try {
                            val resposeDataEntity = JSON.parseObject(response, ResposeDataEntity::class.java)
                            val code = resposeDataEntity.code
                            var data = resposeDataEntity.data
                            if (TextUtils.isEmpty(data)) {
                                data = ""
                            }
                            if (code == 1) {
                                if (data != null) {
                                    okHttpCallback.onSuccess(data)
                                }
                            } else if (code == 420) {
                                okHttpCallback.onError(code, resposeDataEntity.msg!!)
                            } else {
                                okHttpCallback.onError(code, resposeDataEntity.msg!!)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            okHttpCallback.onError(statusCode, "网络异常（数据解析错误）")
                        }

                    } else {
                        okHttpCallback.onError(statusCode, "网络异常")
                    }

                    okHttpCallback.onFinished()
                }

                override fun onFailure(statusCode: Int, error_msg: String) {
                    LogControl.setLogDate(ErrorLog::class.java).ShowLogAndWrite("OKHttp_error_msg", error_msg)
                    okHttpCallback.onError(statusCode, "网络异常")
                    okHttpCallback.onFinished()
                }
            })
        }
        return http
    }

    fun getHttp(path: String, map: HashMap<String, String>?, okHttpCallback: OKHttpCallback): MyOkHttp {
        val http = getOKHttp()
        if (http != null) {
            http.get().url(path).params(map).headers(getHeaders()).enqueue(object : RawResponseHandler() {
                override fun onSuccess(statusCode: Int, response: String) {
                    if (statusCode == 200) {
                        try {
                            val resposeDataEntity = JSON.parseObject(response, ResposeDataEntity::class.java)
                            val code = resposeDataEntity.code
                            var data = resposeDataEntity.data
                            if (code == 1) {
                                if (data != null) {
                                    okHttpCallback.onSuccess(data)
                                }
                            } else if (code == 420) {
                                okHttpCallback.onError(code, resposeDataEntity.msg!!)
                            } else {
                                okHttpCallback.onError(code, resposeDataEntity.msg!!)
                            }
                        } catch (e: Exception) {
                            okHttpCallback.onError(statusCode, "网络异常（数据解析错误）")
                        }

                    } else {
                        okHttpCallback.onError(statusCode, "网络异常")
                    }
                    okHttpCallback.onFinished()
                }

                override fun onFailure(statusCode: Int, error_msg: String) {
                    okHttpCallback.onError(statusCode, "网络异常")
                    okHttpCallback.onFinished()
                }
            })
        }
        return http!!
    }


    fun postHttp(
        context: Context,
        path: String,
        map: HashMap<String, String>?,
        okHttpCallback: OKHttpCallback
    ): MyOkHttp? {
        val http = getOKHttp()

        val jsonObject = JSONObject()

        if (map != null) {
            for (key in map.keys) {
                try {
                    jsonObject.put(key, map[key])
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }

        LogControl.setLogDate(ErrorLog::class.java).ShowLogAndWrite(
            "OKHttp_post",
            "00000000000 " + "________path" + path + "________" + jsonObject.toString() + "________" + getHeaders().toString()
        )
        if (http != null) {
            http.post().url(path).jsonParams(jsonObject.toString()).tag(context).headers(getHeaders()).enqueue(object :
                JsonResponseHandler() {
                override fun onSuccess(statusCode: Int, response: JSONObject?) {

                    LogControl.setLogDate(ErrorLog::class.java).ShowLogAndWrite(
                        "OKHttp_post_onSuccess",
                        "00000000000 " + response!!
                    )
                    if (statusCode == 200) {
                        try {
                            val resposeDataEntity = JSON.parseObject(response.toString(), ResposeDataEntity::class.java)
                            val code = resposeDataEntity.code
                            var data = resposeDataEntity.data
                            if (TextUtils.isEmpty(data)) {
                                data = ""
                            }
                            if (code == 1) {
                                if (data != null) {
                                    okHttpCallback.onSuccess(data)
                                }
                            } else if (code == 420) {
                                okHttpCallback.onError(code, resposeDataEntity.msg!!)
                            } else {
                                okHttpCallback.onError(code, resposeDataEntity.msg!!)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            okHttpCallback.onError(statusCode, "网络异常（数据解析错误）")
                        }

                    } else {
                        okHttpCallback.onError(statusCode, "网络异常")
                    }
                    okHttpCallback.onFinished()
                }

                override fun onFailure(statusCode: Int, error_msg: String) {
                    LogControl.setLogDate(ErrorLog::class.java).ShowLogAndWrite(
                        "OKHttp_post_onFailure",
                        "00000000000 $error_msg"
                    )
                    okHttpCallback.onError(statusCode, "网络异常")
                    okHttpCallback.onFinished()
                }
            })
        }
        return http
    }


    private fun getHeaders(): Map<String, String> {
        val headers = HashMap<String, String>()
        return headers
    }
}