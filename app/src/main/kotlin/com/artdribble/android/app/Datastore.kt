package com.artdribble.android.app

import android.content.SharedPreferences
import android.text.TextUtils
import com.artdribble.android.models.Dribble
import com.google.gson.Gson

/**
 * Created by robcook on 5/29/17.
 */
class Datastore(val sharedPrefs: SharedPreferences,
                val gson: Gson) {


    private val DAILY_DRIBBLE: String = "Daily:Dribble"
    private val DO_NOTIFICATION: String = "Do:Notification"
    private val NOTIFICATION_TIME: String = "Notification:Time"

    private fun getEditor() : SharedPreferences.Editor = sharedPrefs.edit()

    private fun getPrefs() : SharedPreferences = sharedPrefs

    fun getDribble(): Dribble? {
        val dribbleJson: String = getPrefs().getString(DAILY_DRIBBLE, "")
        if (TextUtils.isEmpty(dribbleJson)) {
            return null
        }

        return gson.fromJson(dribbleJson, Dribble::class.java)
    }

    fun setDribble(dribble: Dribble?) {
        if (dribble == null) {
            getEditor().remove(DAILY_DRIBBLE).apply()
            return
        }

        getEditor().putString(DAILY_DRIBBLE, gson.toJson(dribble)).apply()
    }

    fun getDoNotification() : Boolean {
        return getPrefs().getBoolean(DO_NOTIFICATION, true)
    }

    fun setDoNotification(notify: Boolean) {
        getEditor().putBoolean(DO_NOTIFICATION, notify).apply()
    }

    fun getNotificationTime() : String {
        return getPrefs().getString(NOTIFICATION_TIME, ArtDribbleApp.DEFAULT_NOTIFY_TIME)
    }

    fun setNotificationTime(timeStr: String) {
        getEditor().putString(NOTIFICATION_TIME, timeStr).apply()
    }
}