package com.artdribble.android.services

import android.annotation.TargetApi
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import com.artdribble.android.api.DribbleApiRepository
import com.artdribble.android.app.ArtDribbleApp
import com.artdribble.android.app.Datastore
import com.artdribble.android.models.Dribble
import com.artdribble.android.utils.get
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import org.koin.android.ext.android.inject

/**
 * Created by robcook on 6/17/17.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class ArtDribbleJobSchedulerGetDailyDribbleService : JobService() {

    val dribbleApiRepository: DribbleApiRepository by inject()
    val datastore: Datastore by inject()
    var disposables = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        loadDailyArtwork()
        return true
    }

    private fun loadDailyArtwork() {
        val key: String = Dribble.getTodayKey()

        val localDribble: Dribble? = datastore.getDribble()

        if (key == localDribble?.dribbledate) {
            return
        }

        disposables.get() += dribbleApiRepository.getArtwork(key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy (
                        onSuccess = { dribble: Dribble? ->  dribble?.let {
                            datastore.setDribble(it)
                            jobFinished(null, false)
                        } },
                        onError = { throwable ->
                            ArtDribbleApp.logThrowable(throwable)
                            jobFinished(null, true)
                        }
                )
    }

}