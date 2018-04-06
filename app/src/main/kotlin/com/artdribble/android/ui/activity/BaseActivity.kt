package com.artdribble.android.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.artdribble.android.app.Datastore
import org.koin.android.ext.android.inject

/**
 * Created by robcook on 5/29/17.
 */

open class BaseActivity : AppCompatActivity() {

    val datastore: Datastore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    inline fun consumeMenuItem(f: () -> Unit): Boolean {
        f()
        return true
    }
}
