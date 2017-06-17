package com.artdribble.android

import android.annotation.TargetApi
import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobInfo.NETWORK_TYPE_ANY
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import com.artdribble.android.dagger.AppComponent
import com.artdribble.android.dagger.DaggerAppComponent
import com.artdribble.android.dagger.module.ApiModule
import com.artdribble.android.dagger.module.DatastoreModule
import com.artdribble.android.dagger.module.GsonModule
import com.artdribble.android.services.ArtDribbleFirebaseGetDailyDribbleService
import com.artdribble.android.services.ArtDribbleJobSchedulerGetDailyDribbleService
import com.firebase.jobdispatcher.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by robcook on 5/29/17.
 */
class ArtDribbleApp : Application () {

    companion object {
        @JvmStatic lateinit var instance: ArtDribbleApp
        @JvmStatic lateinit var appComponent: AppComponent
        val DEFAULT_NOTIFY_TIME = "8:00 AM"
        val FORMAT_DAILY_ARTWORK_KEY = "yyyyMMdd"
        val JOB_ID = 1442
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        initAppComponent()

        scheduleDailyDribble()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .apiModule(ApiModule())
                .datastoreModule(DatastoreModule(this))
                .gsonModule(GsonModule())
                .build()

        appComponent.inject(this)
    }

    private fun scheduleDailyDribble() {

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun scheduleLollipopPlus(msToMidnight: Long) {

        val jobInfo: JobInfo = JobInfo.Builder(JOB_ID, ComponentName(this, ArtDribbleJobSchedulerGetDailyDribbleService::class.java))
                .setMinimumLatency(msToMidnight)
                .setRequiredNetworkType(NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build()

        val jobScheduler: JobScheduler? = getSystemService(Context.JOB_SCHEDULER_SERVICE) as? JobScheduler
        jobScheduler?.schedule(jobInfo)
    }
}