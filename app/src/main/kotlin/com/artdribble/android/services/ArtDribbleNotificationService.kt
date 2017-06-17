package com.artdribble.android.services

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.artdribble.android.ArtDribbleApp
import com.artdribble.android.Datastore
import com.artdribble.android.R
import com.artdribble.android.models.Dribble
import com.artdribble.android.ui.activity.MainActivity
import com.artdribble.android.utils.asDateString
import java.util.*
import javax.inject.Inject

/**
 * Created by robcook on 6/17/17.
 */
class ArtDribbleNotificationService : IntentService("ArtDribbleNotificationService") {

    @Inject
    lateinit var datastore: Datastore

    override fun onCreate() {
        super.onCreate()
        ArtDribbleApp.appComponent.inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {


        val key = Dribble.getTodayKey()
        val dribble: Dribble? = datastore.getDribble()

        if (dribble?.dribbledate != key) {
            return
        }

        val intent: Intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val title: String = dribble.artsyArtworkInfo.title ?: "ArtDribble for ${Date().asDateString()}"
        val msg: String = dribble.getNotificationMsg()

        val notifyCompat: Notification = NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentInfo(msg)
                .setSmallIcon(R.drawable.ic_notification)
                .build()

        val notifyManager: NotificationManager? = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        notifyManager?.notify(0, notifyCompat)

    }

}