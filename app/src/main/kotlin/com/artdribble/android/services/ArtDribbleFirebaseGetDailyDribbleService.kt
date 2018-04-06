package com.artdribble.android.services

import com.artdribble.android.api.DribbleApiRepository
import com.artdribble.android.app.ArtDribbleApp
import com.artdribble.android.app.Datastore
import com.artdribble.android.models.Dribble
import com.artdribble.android.utils.get
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import org.koin.android.ext.android.inject
import java.io.IOException

/**
 * Created by robcook on 6/17/17.
 */
class ArtDribbleFirebaseGetDailyDribbleService : JobService() {

    val dribbleApiRepository : DribbleApiRepository by inject()
    val datastore: Datastore by inject()
    val disposables = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(job: JobParameters?): Boolean {

        val success: Boolean = loadDailyArtwork()

        return false
    }

    private fun loadDailyArtwork(): Boolean {
        val key: String = Dribble.getTodayKey()

        val localDribble: Dribble? = datastore.getDribble()

        if (localDribble != null && localDribble.dribbledate == key) {
            return true
        }

        try {
            disposables.get() += dribbleApiRepository.getArtwork(key)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onSuccess = { dribble: Dribble? -> dribble?.let { datastore.setDribble(it) } },
                            onError = { throwable ->  ArtDribbleApp.logThrowable(throwable) }
                    )
        } catch (e: IOException) {
            ArtDribbleApp.logThrowable(e)
        }

        return false
    }

}