package com.artdribble.android.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.artdribble.android.ArtDribbleApp
import com.artdribble.android.api.DribbleApi
import javax.inject.Inject

/**
 * Created by robcook on 5/29/17.
 */

open class BaseActivity : AppCompatActivity() {

    @Inject
    protected lateinit var api: DribbleApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArtDribbleApp.appComponent.inject(this)
    }
}
