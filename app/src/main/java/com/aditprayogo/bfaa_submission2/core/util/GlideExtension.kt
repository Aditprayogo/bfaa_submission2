package com.aditprayogo.bfaa_submission2.core.util

import android.widget.ImageView
import com.aditprayogo.bfaa_submission2.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

fun ImageView.load(imgResource : Any?) {
    Glide.with(context.applicationContext)
        .load(imgResource)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade(getDrawableFactory()))
        .placeholder(R.drawable.ic_user)
        .into(this)
}

private fun getDrawableFactory() : DrawableCrossFadeFactory {
    return DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
}