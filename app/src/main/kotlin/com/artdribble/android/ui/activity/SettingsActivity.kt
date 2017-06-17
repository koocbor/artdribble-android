package com.artdribble.android.ui.activity

import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.widget.TimePicker
import com.artdribble.android.ArtDribbleApp
import com.artdribble.android.R
import kotlinx.android.synthetic.main.activity_settings.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by robcook on 6/5/17.
 */
class SettingsActivity : BaseActivity() {

    val FORMAT_HOUR_MINUTE_AMPM = "h:mm a"

    var doNotify: Boolean = true
    var notifyTime: String = ArtDribbleApp.DEFAULT_NOTIFY_TIME

    var colorActive: Int = 0
    var colorInactive: Int = 0

    var sdf: SimpleDateFormat = SimpleDateFormat(FORMAT_HOUR_MINUTE_AMPM, Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        colorActive = ContextCompat.getColor(this, R.color.colorBrown)
        colorInactive = ContextCompat.getColor(this, R.color.colorBrownInactive)

        initActionBar()
        initSettings()
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId) {
        android.R.id.home -> consumeMenuItem { onBackPressed() }
        else -> super.onOptionsItemSelected(item)
    }

    private fun bindNotifySettings() {
        settings_notify_time.isClickable = doNotify

        if (doNotify) {
            settings_notify_time.setBackgroundColor(colorActive)
        } else {
            settings_notify_time.setBackgroundColor(colorInactive)
        }
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    private fun initSettings() {
        doNotify = datastore.getDoNotification()
        notifyTime = datastore.getNotificationTime()

        settings_do_notify_cb.isChecked = doNotify
        settings_notify_time.text = notifyTime
        if (doNotify) {
            settings_notify_time.setBackgroundColor(colorActive)
        } else {
            settings_notify_time.setBackgroundColor(colorInactive)
        }

        settings_notification_title.setOnClickListener({ settings_do_notify_cb.performClick() })
        settings_do_notify_cb.setOnClickListener({ toggleDoNotify() })
        settings_notify_time.setOnClickListener({ showTimePickerDialog() })
    }

    private fun showTimePickerDialog() {
        if (!doNotify) {
            return
        }

        val dteNotifyTime: Date = sdf.parse(notifyTime)
        val calNotifyTime: Calendar = Calendar.getInstance()
        calNotifyTime.time = dteNotifyTime
        val hourOfDay: Int = calNotifyTime.get(Calendar.HOUR_OF_DAY)
        val minute: Int = calNotifyTime.get(Calendar.MINUTE)

        TimePickerDialog(this, R.style.ArtDribble_Dialog, { view: TimePicker, selectedHour: Int, selectedMinute: Int ->
            val cal: Calendar = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, selectedHour)
            cal.set(Calendar.MINUTE, selectedMinute)
            notifyTime = sdf.format(cal.time)
            settings_notify_time.text = notifyTime
            datastore.setNotificationTime(notifyTime)
        }, hourOfDay, minute, false)
                .show()
    }

    private fun toggleDoNotify() {
        doNotify = settings_do_notify_cb.isChecked
        datastore.setDoNotification(doNotify)
        bindNotifySettings()
    }
}