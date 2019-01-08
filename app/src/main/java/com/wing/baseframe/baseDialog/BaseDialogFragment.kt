package com.wing.baseframe.baseDialog

import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.wing.baseframe.utils.BarUtils

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

abstract class BaseDialogFragment  : DialogFragment(),  DialogInterface.OnKeyListener, View.OnClickListener {
    private var mShowListner: ShowListner? = null
    private var mControlDissmisface: ControlDissmisface? = null
    private var mBaseDialogBean: BaseDialogBean? = null
    var pDissMissType = ""
    override fun onKey(dialog: DialogInterface, keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            !isKeyBackCanceled(true)
        } else false
    }

    /**
     * 获取布局ID
     */
     abstract fun getContentViewLayoutID(): Int

    /**
     * 界面初始化
     */
    protected abstract fun onInit(view: View, savedInstanceState: Bundle?)

    /**
     * 获取Dialog宽度
     *
     * @return
     */
    protected abstract fun getDialogLayoutWidthSize(): Int

    /**
     * 获取Dialog高度
     *
     * @return
     */
    protected abstract fun getDialogLayoutHeightSize(): Int

    /**
     * 设置Dialog是否允许点击外部消失
     *
     * @return if `true`, 允许消失.
     * if {@false}, 不允许消失.
     */
    protected abstract fun isDialogCanceledOnTouchOutside(): Boolean

    /**
     * 设置Dialog是否允许点击消失
     *
     * @return if `true`, 允许消失.
     * if {@false}, 不允许消失.
     */
    abstract fun isDialogCancelable(): Boolean

    /**
     * 是否允许back键取消Dialog
     *
     * @return
     */
    protected fun isKeyBackCanceled(b: Boolean): Boolean {
        return b
    }

    /**
     * 设置Dialog动画
     *
     * @return
     */
    abstract fun getWindowAnimationsId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * Dialog背景样式
     *
     * @return
     */
    protected fun getDialogBackgroundDrawable(): Drawable {
        return ColorDrawable(Color.TRANSPARENT)
    }

    protected fun getSystemUiVisibility(): Int {
        return (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onStart() {
        super.onStart()
        dialog.setOnKeyListener(this)
        dialog.window!!.setLayout(getDialogLayoutWidthSize(), getDialogLayoutHeightSize())
        val bg = getDialogBackgroundDrawable()
        dialog.window!!.setBackgroundDrawable(bg ?: ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.decorView.systemUiVisibility = getSystemUiVisibility()
        val windowParams = dialog.window!!.attributes
        windowParams.dimAmount = 0f
        if (getWindowAnimationsId() != 0) {
            windowParams.windowAnimations = getWindowAnimationsId()
        }
        windowParams.flags = windowParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        dialog.window!!.attributes = windowParams
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return if (getContentViewLayoutID() != 0) {
            inflater.inflate(getContentViewLayoutID(), container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.setCanceledOnTouchOutside(isDialogCanceledOnTouchOutside())
        dialog.setCancelable(isDialogCancelable())
        onInit(view, savedInstanceState)
        if (mBaseDialogBean == null) {
            setDate(BaseDialogBean())
        } else {
            setDate(mBaseDialogBean!!)
        }
        if (mShowListner != null) {
            mShowListner!!.isShowing()
        }

    }

    protected abstract fun setDate(baseDialogBean: BaseDialogBean)


    override fun onDestroyView() {
        super.onDestroyView()
        if (mControlDissmisface != null) {
            mControlDissmisface!!.setDissMiss(this, pDissMissType)
            this.onDestroy()
        }
        BarUtils.hideSystemUI(activity as AppCompatActivity?)
    }

    fun setShowingListner(showingListner: ShowListner) {
        this.mShowListner = showingListner
    }

    override fun onClick(view: View) {
        widgetClick(view)
    }

    abstract fun widgetClick(v: View)

    fun isShowing(): Boolean {
        return if (this.dialog != null) {
            this.dialog.isShowing
        } else false
    }

    fun setDissMissListner(controlDissmisface: ControlDissmisface) {
        this.mControlDissmisface = controlDissmisface
    }

    fun initDate(baseDialogBean: BaseDialogBean) {
        this.mBaseDialogBean = baseDialogBean
    }

    /**
     * 触摸监听
     * @param view
     */
    fun setonTouch(view: View) {}


    interface GetBurBitmap {
        fun getBitMap(bitmap: Bitmap?)
    }
}