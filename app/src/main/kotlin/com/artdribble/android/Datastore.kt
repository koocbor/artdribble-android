package com.artdribble.android

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

    private fun getEditor() : SharedPreferences.Editor = sharedPrefs.edit()

    private fun getPrefs() : SharedPreferences = sharedPrefs

    fun getDribble(): Dribble? {
        var dribbleJson: String = getPrefs().getString(DAILY_DRIBBLE, "")
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
}