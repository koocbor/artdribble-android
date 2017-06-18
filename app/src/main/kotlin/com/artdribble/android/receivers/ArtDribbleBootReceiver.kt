package com.artdribble.android.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.artdribble.android.ArtDribbleApp

/**
 * Created by robcook on 6/18/17.
 */
class ArtDribbleBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val artDribbleApp: ArtDribbleApp? = context?.applicationContext as? ArtDribbleApp
        artDribbleApp?.scheduleNotification()
    }
}