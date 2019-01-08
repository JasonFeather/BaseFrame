package com.wing.baseframe.base

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.wing.baseframe.baseDialog.DialogControl
import com.wing.baseframe.listener.PermissionListener
import com.wing.baseframe.utils.BarUtils
import com.wing.baseframe.utils.OnClickUitil
import com.wing.baseframe.utils.ToastUtils
import java.util.ArrayList
import android.R.attr.delay
import android.os.Handler
import android.support.v4.os.HandlerCompat.postDelayed


/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：acitivity基础类
 */

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener, BaseUIListener, MvpBaseLister {

    var pDialogControl: DialogControl? = null

    private var mPermissions: List<String>? = null
    /**
     * 是否允许全屏,此处全屏是指将状态栏显示
     */
    private var mOnlyShowStatusBar = false
    /**
     * 是否允许全屏,此处全屏是指将状态栏都隐藏掉
     */
    private var mAllowFullScreen = true
    /**
     * 当前Activity渲染的视图View
     */
    private var mContextView: View? = null
    /**
     * 日志输出标志
     */
    protected val TAG = this.javaClass.simpleName
    /**
     * 运行时权限的监听回调
     */
    private var mListener: PermissionListener? = null

    /**
     * Activity 管理器
     */
    private val mActivityManager: ActivityManager? = null
    protected var pAllowTime = true //判断是否允许开启强制退出的定时器
    var mPresenter: BasePresenter? = null

    init {
        mPresenter = addMvp()
        
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.addActivity(this)
        if (mAllowFullScreen) {
            //隐藏状态栏
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            //屏幕常亮
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            //隐藏底部
            BarUtils.hideSystemUI(this)
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null)

        setContentView(mContextView)
        initDataBefore()
        initView(mContextView)
        pDialogControl = DialogControl(this)
        initDataAfter()
        setListener()
        window.decorView.post {
            mListener?.apply {
                requestRunTimePermission(this@BaseActivity)
            }
        }


    }


    override fun onClick(view: View) {
        if (OnClickUitil.fastClick()) {
            return
        }
        widgetClick(view)
    }

    override fun onRestart() {
        super.onRestart()

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        pDialogControl = null
    }

    fun setPermissionListener(permissions: List<String>, listener: PermissionListener?) {
        this.mPermissions = permissions;
        this.mListener = listener
    }

    /**
     * 6.0以上检查申请权限时调用`
     *
     * @param activity 当前使用的ActivitypressionList.add(mPermissions!![i])
     * @param permissions 需要申请检查的权限集合
     * @param listener 是否成功的回调方法
     */
    fun requestRunTimePermission(activity: Activity) {
        val pressionList = arrayListOf<String>()
        for (i in this.mPermissions!!.indices) {
            if (ContextCompat.checkSelfPermission(activity, mPermissions!![i]) != PackageManager.PERMISSION_GRANTED) {

            }
        }

        if (pressionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, pressionList.toTypedArray(), 1)
        } else {
            //do something
            if (mListener == null) return
            mListener!!.onGranted()
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val failureList = ArrayList<String>()
        when (requestCode) {
            1 -> if (grantResults.size > 0) {
                for (i in grantResults.indices) {
                    val granted = grantResults[i]
                    val pression = permissions[i]
                    if (granted != PackageManager.PERMISSION_GRANTED) {
                        failureList.add(pression)
                    }
                }
                if (failureList.isEmpty()) {
                    if (mListener == null) return
                    mListener!!.onGranted()
                } else {
                    if (mListener == null) return
                    var isNeedStartSetting = false
                    for (i in failureList.indices) {
                        isNeedStartSetting = shouldShowRequestPermissionRationale(failureList[i])
                        if (!isNeedStartSetting) {
                            break
                        }
                    }
                    if (!isNeedStartSetting) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        ToastUtils.getToast(this, "请在设置页面允许申请的权限！")
                        startActivityForResult(intent, 1)
                    } else {
                        mListener!!.onFailure(failureList)
                        ToastUtils.getToast(this, "请允许申请的权限！")
                    }
                }
            }
            else -> {
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            requestRunTimePermission(this)
        }
    }


}

