package com.artdribble.android.mvp.view

import com.artdribble.android.ui.activity.BaseActivity

/**
 * Created by robcook on 5/29/17.
 */
interface BaseView {

    fun getBaseActivity() : BaseActivity?
    fun toggleProgress(show: Boolean)

}