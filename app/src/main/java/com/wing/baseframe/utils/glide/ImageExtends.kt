package com.wing.baseframe.utils.glide

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.wing.baseframe.R

/**
 *姓名：mengc
 *日期：2019/1/7
 *功能：
 */

public fun ImageView.setImageSrc(url:String){
    GlideApp.with(context)
        .load(url)
        .thumbnail(/*sizeMultiplier=*/0.25f)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(
            Glide.with(context)
                .load(url)
        )
        .fallback(R.drawable.hcb_place_app_icon)
        .into(this)
}