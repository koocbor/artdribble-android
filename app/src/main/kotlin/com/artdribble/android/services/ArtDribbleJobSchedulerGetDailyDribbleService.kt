package com.artdribble.android.services

import android.annotation.TargetApi
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import com.artdribble.android.ArtDribbleApp
import com.artdribble.android.Datastore
import com.artdribble.android.api.DribbleApi
import com.artdribble.android.models.Dribble
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by robcook on 6/17/17.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class ArtDribbleJobSchedulerGetDailyDribbleService : JobService() {

    @Inject
    lateinit var dribbleApi: DribbleApi

    @Inject
    lateinit var datastore: Datastore

    private var dribbleCall: Call<Dribble>? = null

    override fun onCreate() {
        super.onCreate()
        ArtDribbleApp.appComponent.inject(this)
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        if (dribbleCall != null) {
            dribbleCall?.cancel()
        }
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {

        loadDailyArtwork()
        return true

    }

    private fun loadDailyArtwork() {
        val key: String = Dribble.getTodayKey()

        val localDribble: Dribble? = datastore.getDribble()

        if (localDribble != null && localDribble.dribbledate == key) {
            return
        }

        dribbleCall = dribbleApi.getArtwork(key)
        dribbleCall?.enqueue(object : Callback<Dribble> {

            override fun onResponse(call: Call<Dribble>?, response: Response<Dribble>?) {

                if (response != null && response.isSuccessful) {
                    val dribble: Dribble? = response.body()
                    if (dribble != null) {
                        datastore.setDribble(dribble)
                        jobFinished(null, false)
                    } else {
                        jobFinished(null, true)
                    }
                }
            }

            override fun onFailure(call: Call<Dribble>?, t: Throwable?) {
                jobFinished(null, true)
            }
        })
    }

}