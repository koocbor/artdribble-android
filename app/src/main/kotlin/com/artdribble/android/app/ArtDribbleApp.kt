package com.artdribble.android.app

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.artdribble.android.BuildConfig
import com.artdribble.android.koin.ApiModule
import com.artdribble.android.koin.AppModule
import com.artdribble.android.koin.PresenterModule
import com.artdribble.android.receivers.ArtDribbleBootReceiver
import com.artdribble.android.services.ArtDribbleFirebaseGetDailyDribbleService
import com.artdribble.android.services.ArtDribbleJobSchedulerGetDailyDribbleService
import com.artdribble.android.services.ArtDribbleNotificationService
import com.firebase.jobdispatcher.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by robcook on 5/29/17.
 */
class ArtDribbleApp : Application () {

    companion object {
        val LOG_TAG = "ArtDribble"

        val DEFAULT_NOTIFY_TIME = "8:00 AM"
        val FORMAT_DAILY_ARTWORK_KEY = "yyyyMMdd"
        val FORMAT_HOUR_MINUTE_AMPM = "h:mm a"
        val JOB_ID = 1442
        val NOTIFICATION_ID = 164

        /**
         * Wrapper to log a debug message if the build is a non-release build.
         * @param message
         */
        fun log(message: String) {
            if (BuildConfig.IS_DEBUG) {
                Log.d(LOG_TAG, message)
            }
        }

        /**
         * Wrapper to print a throwable stack trace to Logcat if this is a non-release
         * build.  Will also send exception to Crashlytics if enabled.
         *
         * @param t - the throwable to log.
         * @param tag - the tag to use if logging to Logcat.
         */
        fun logThrowable(t: Throwable?, tag: String = LOG_TAG) {
            if (BuildConfig.IS_DEBUG) {
                val sw = StringWriter()
                val pw = PrintWriter(sw)
                t?.printStackTrace(pw)
                Log.e(tag, sw.toString())
            }
            // TODO: Add Crashlytics
            /*
            t?.let {
                CrashlyticsCore.getInstance().logException(it)
            }
             */
        }
    }

    val datastore: Datastore by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(AppModule.getModule(), ApiModule.getModule(), PresenterModule.getModule()))

        scheduleDailyDribbleDownload()
        scheduleNotification()
    }

    private fun scheduleDailyDribbleDownload() {

        val now: Date = Date()
        val calMidnight: Calendar = Calendar.getInstance()
        calMidnight.add(Calendar.DAY_OF_YEAR, 1)
        calMidnight.set(Calendar.HOUR_OF_DAY, 0)
        calMidnight.set(Calendar.MINUTE, 0)
        calMidnight.set(Calendar.SECOND, 0)
        calMidnight.set(Calendar.MILLISECOND, 0)

        val msToMidnight: Long = calMidnight.timeInMillis - now.time

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scheduleLollipopPlus(msToMidnight)
        } else {
            schedulePreLollipop(msToMidnight)
        }

    }

    fun scheduleNotification() {

        val sdf: SimpleDateFormat = SimpleDateFormat(FORMAT_HOUR_MINUTE_AMPM, Locale.US)
        val notifyTime: String = datastore.getNotificationTime()
        val calTime: Calendar = Calendar.getInstance()
        calTime.time = sdf.parse(notifyTime)
        val cal: Calendar = Calendar.getInstance()
        cal.timeInMillis = System.currentTimeMillis()
        cal.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY))
        cal.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE))

        val alarmManager: AlarmManager? = getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        if (alarmManager == null) {
            return
        }

        val repeatFrequency: Long = 24 * 60 * 60 * 1000

        val notifyIntent: Intent = Intent(this, ArtDribbleNotificationService::class.java)
        val alarmIntent: PendingIntent = PendingIntent.getService(this, 0, notifyIntent, 0)

        // If an alarm exists for this pendingIntent - cancel it
        alarmManager.cancel(alarmIntent)

        val receiver: ComponentName = ComponentName(this, ArtDribbleBootReceiver::class.java)
        val pm: PackageManager = getPackageManager()
        if (datastore.getDoNotification()) {
            alarmManager.setInexactRepeating(AlarmManager.RTC, cal.timeInMillis, repeatFrequency, alarmIntent)

            // enable boot receiver in case user reboots device so alarm manager can be re-created
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP)
        } else {
            // disable boot receiver in case user reboots device so alarm manager can be re-created
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun scheduleLollipopPlus(msToMidnight: Long) {

        val jobInfoBuilder = JobInfo.Builder(JOB_ID, ComponentName(this, ArtDribbleJobSchedulerGetDailyDribbleService::class.java))
                .setMinimumLatency(msToMidnight)
                .setPersisted(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            jobInfoBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        } else {
            jobInfoBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
        }

        val jobScheduler: JobScheduler? = getSystemService(Context.JOB_SCHEDULER_SERVICE) as? JobScheduler
        jobScheduler?.schedule(jobInfoBuilder.build())
    }

    private fun schedulePreLollipop(msToMidnight: Long) {

        val secToMidnight: Int = (msToMidnight / 1000L).toInt()
        val tolerence: Int = (TimeUnit.HOURS.toSeconds(1)).toInt()

        val jobDispatcher: FirebaseJobDispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        val dailyDribbleJob: Job = jobDispatcher.newJobBuilder()
                .setService(ArtDribbleFirebaseGetDailyDribbleService::class.java)
                .setTrigger(Trigger.executionWindow(secToMidnight, tolerence))
                .setRecurring(true)
                .setReplaceCurrent(true)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .build()

        jobDispatcher.mustSchedule(dailyDribbleJob)
    }
}