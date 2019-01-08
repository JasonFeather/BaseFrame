package com.wing.baseframe.modular

import com.wing.baseframe.base.BaseActivity
import com.wing.baseframe.base.BasePresenter
import com.wing.baseframe.base.BaseView

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

interface MainActivityContract {
    abstract class Presenter constructor(var view: BaseView) : BasePresenter {
        init {
            showView()
        }
    }

    interface View : BaseView {
    }
}