package com.artdribble.android

import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * Created by robcook on 5/29/17.
 */
class Datastore(val sharedPrefs: SharedPreferences,
                val gson: Gson) {

    private val DAILY_DRIBBLE: String = "Daily:Dribble"

    private fun getEditor() : SharedPreferences.Editor = sharedPrefs.edit()

    private fun getPrefs() : SharedPreferences = sharedPrefs


}