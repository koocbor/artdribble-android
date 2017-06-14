package com.artdribble.android.ui.activity

import android.os.Bundle
import android.view.MenuItem
import com.artdribble.android.R
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * Created by robcook on 6/5/17.
 */
class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initActionBar()
        initSettings()
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId) {
        android.R.id.home -> consumeMenuItem { onBackPressed() }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    private fun initSettings() {
        var doNotify: Boolean = datastore.getDoNotification()
        var notifyTime: String = datastore.getNotificationTime()

        settings_do_notify_cb.isChecked = doNotify
        settings_notify_time.text = notifyTime
    }
}