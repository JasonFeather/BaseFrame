package com.wing.baseframe.modular.mian

import android.Manifest
import android.view.View
import android.widget.ImageView
import com.wing.baseframe.R
import com.wing.baseframe.base.BaseActivity
import com.wing.baseframe.base.BasePresenter
import com.wing.baseframe.http.HttpUtils
import com.wing.baseframe.http.OKHttpCallback
import com.wing.baseframe.http.UrlInterface
import com.wing.baseframe.listener.PermissionListener
import com.wing.baseframe.modular.MainActivityContract
import com.wing.baseframe.modular.MainPresenterImp
import com.wing.baseframe.utils.ToastUtils
import com.wing.baseframe.utils.glide.setImageSrc

class MainActivity() : BaseActivity(), PermissionListener,MainActivityContract.View {


    override fun showLoading() {
    }

    override fun dissLoading() {

    }

    //转换类
    override fun presenterImp() {
    }

    //添加mvp模块
    override fun addMvp(): BasePresenter {
        return MainPresenterImp(this)
    }

    //权限申请成功，
    override fun onGranted() {
        ToastUtils.getToast(this, "skdfjslkdjfl")
        HttpUtils.getHttp(UrlInterface.getText(), null, object : OKHttpCallback {
            override fun onSuccess(result: String) {

            }

            override fun onError(code: Int, message: String) {

            }

            override fun onFinished() {

            }

        })
    }
    //权限申请失败

    override fun onFailure(failurePression: List<String>) {

    }


    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView(view: View?) {
        var imageView = view!!.findViewById<ImageView>(R.id.iv) as ImageView
        //ImageView 的扩展类
        imageView.setImageSrc("https://hcb-picture.oss-cn-beijing.aliyuncs.com/部位.png")
    }

    override fun initDataAfter() {

    }

    override fun setListener() {
        //设置权限
        val stringPermissions = arrayListOf<String>()
        stringPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        this.setPermissionListener(stringPermissions, this)
    }

    override fun widgetClick(v: View) {
    }


}
