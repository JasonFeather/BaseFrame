package com.wing.baseframe.baseDialog

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import java.lang.ref.WeakReference


/**
 * 姓名：mengc
 * 日期：2018/8/9
 * 功能：获取弹窗类，在此处配置弹窗即可。。
 */

 object BaseDialogUtils {

    /**
     * 获取对应的弹窗
     *
     * @return
     */
    fun getDialog(baseDialogFragment: BaseDialogFragment, baseDialogBean: BaseDialogBean): BaseDialogFragment {
        baseDialogFragment.initDate(baseDialogBean)
        return baseDialogFragment
    }

    /**
     * 显示弹窗
     *
     * @param baseDialogFragment
     * @param context
     */
    fun showDialogFragmentActivity(baseDialogFragment: BaseDialogFragment, context: Context) {
        val weakReference = WeakReference(baseDialogFragment)
        val manager = (context as FragmentActivity).supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(weakReference.get()!!, java.lang.Long.toString(System.currentTimeMillis()))
        transaction.commitAllowingStateLoss()
    }


    fun showDialogFragment(baseDialogFragment: BaseDialogFragment, context: Fragment) {
        val weakReference = WeakReference(baseDialogFragment)
        val supportFragmentManager = context.childFragmentManager
        weakReference.get()!!.show(supportFragmentManager, java.lang.Long.toString(System.currentTimeMillis()))
    }

    fun showDialog(baseDialogFragment: BaseDialogFragment, context: Context) {
        if (context is FragmentActivity) {
            showDialogFragmentActivity(baseDialogFragment, context)
        }
    }
}
