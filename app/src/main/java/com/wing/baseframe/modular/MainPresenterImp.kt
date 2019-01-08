package com.wing.baseframe.modular

import com.wing.baseframe.base.BaseActivity
import com.wing.baseframe.base.BaseView

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：Mainactivity的实现类
 */

class MainPresenterImp constructor(view: BaseView) : MainActivityContract.Presenter(view) {

    override fun showView() {
        view as MainActivityContract.View

    }

    override fun initDate(url: String) {

    }
}