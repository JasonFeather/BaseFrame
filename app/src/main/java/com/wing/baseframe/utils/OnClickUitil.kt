package com.wing.baseframe.utils

import java.util.concurrent.atomic.AtomicBoolean

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

object OnClickUitil {
    private var lastClick: Long = 0
    private val p = AtomicBoolean(true)
    private fun setAtomic(): Boolean {
        return p.compareAndSet(true, false)
    }

    private fun reSetAtomic(): Boolean {
        return p.compareAndSet(false, true)
    }

    fun fastClick(): Boolean {
        if (setAtomic()) {
            reSetAtomic()
            if (System.currentTimeMillis() - lastClick <= 500) {
                return true
            }
            lastClick = System.currentTimeMillis()
            return false

        }
        return false
    }
}