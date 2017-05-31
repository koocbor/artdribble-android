package com.artdribble.android.utils

import android.graphics.drawable.Drawable
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener

/**
 * Created by robcook on 5/30/17.
 */

/**
 * Extension to load url into image using Glide
 */
fun ImageView.loadUrl(url: String?) {
    Glide.with(context)
            .load(url)
            .into(this)
}

fun ImageView.loadUrl(url: String?, requestListener: RequestListener<Drawable>) {
    Glide.with(context)
            .load(url)
            .listener(requestListener)
            .into(this)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean  = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}