package com.artdribble.android.services

import com.artdribble.android.ArtDribbleApp
import com.artdribble.android.Datastore
import com.artdribble.android.api.DribbleApi
import com.artdribble.android.models.Dribble
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by robcook on 6/17/17.
 */
class ArtDribbleFirebaseGetDailyDribbleService : JobService() {

    @Inject
    lateinit var dribbleApi: DribbleApi

    @Inject
    lateinit var datastore: Datastore

    override fun onCreate() {
        super.onCreate()

        ArtDribbleApp.appComponent.inject(this)
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(job: JobParameters?): Boolean {

        val success: Boolean = loadDailyArtwork()

        return false
    }

    private fun loadDailyArtwork(): Boolean {
        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(ArtDribbleApp.FORMAT_DAILY_ARTWORK_KEY, Locale.US)
        val now: Date = Date()
        val key: String = simpleDateFormat.format(now)

        val localDribble: Dribble? = datastore.getDribble()

        if (localDribble != null && localDribble.dribbledate == key) {
            return true
        }

        try {
            val response: Response<Dribble> = dribbleApi.getArtwork(key).execute()

            if (response.isSuccessful) {
                val dribble: Dribble? = response.body()
                if (dribble != null) {
                    datastore.setDribble(dribble)
                    return true
                }
            }

        } catch (e: IOException) {

        }

        return false
    }

}