package com.wing.baseframe.baseDialog

import android.content.Context
import java.util.HashMap

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

public class DialogControl constructor(var mContext:Context) {
    var mDialogHashMap = HashMap<String, BaseDialogFragment>()

    /**
     *
     * @param baseDialogFragment  dialog 类型
     * @param baseDialogBean dialog 显示参数
     */
    fun getNewDialog(baseDialogFragment: BaseDialogFragment, baseDialogBean: BaseDialogBean) {
        val baseDialog = BaseDialogUtils.getDialog(baseDialogFragment, baseDialogBean)
        BaseDialogUtils.showDialog(baseDialog, this!!.mContext!!)
        baseDialog.setShowingListner(object : ShowListner {
            override fun isShowing() {
                judgeIsEject(mDialogHashMap, baseDialog)
            }
        })
    }


    fun getNewDialog(
        baseDialogFragment: BaseDialogFragment,
        baseDialogBean: BaseDialogBean,
        controlDissmisface: ControlDissmisface
    ) {
        val baseDialog = BaseDialogUtils.getDialog(baseDialogFragment, baseDialogBean)
        BaseDialogUtils.showDialog(baseDialog, this!!.mContext!!)
        baseDialog.setShowingListner(object : ShowListner {
            override fun isShowing() {
                judgeIsEject(mDialogHashMap, baseDialog, controlDissmisface)
            }
        })
    }


    private fun judgeIsEject(dialogHashMap: HashMap<String, BaseDialogFragment>, baseDialog: BaseDialogFragment) {
        for (key in dialogHashMap.keys) {
            val baseDialog1 = dialogHashMap[key]
            if (baseDialog1 != null) {
                if (baseDialog1.isShowing()) {
                    baseDialog1.dismiss()
                }
            }
        }
        dialogHashMap.clear()
        val l = System.currentTimeMillis()
        val s = java.lang.Long.toString(l)
        dialogHashMap[s] = baseDialog

    }

    private fun judgeIsEject(
        dialogHashMap: HashMap<String, BaseDialogFragment>,
        baseDialog: BaseDialogFragment,
        controlDissmisface: ControlDissmisface
    ) {
        for (key in dialogHashMap.keys) {
            val baseDialog1 = dialogHashMap[key]
            if (baseDialog1 != null) {
                if (baseDialog1.isShowing()) {
                    baseDialog1.dismiss()
                }
            }
        }
        dialogHashMap.clear()
        val l = System.currentTimeMillis()
        val s = java.lang.Long.toString(l)
        dialogHashMap[s] = baseDialog
        baseDialog.setDissMissListner(controlDissmisface)

    }

    fun dissMiss() {
        for (key in mDialogHashMap.keys) {
            var baseDialog1: BaseDialogFragment? = mDialogHashMap[key]
            if (baseDialog1!!.isShowing()) {
                baseDialog1.dismissAllowingStateLoss()
                baseDialog1.onDestroy()
                baseDialog1 = null
            }
        }
    }
}
