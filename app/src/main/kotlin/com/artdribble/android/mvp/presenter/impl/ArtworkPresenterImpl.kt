package com.artdribble.android.mvp.presenter.impl

import com.artdribble.android.app.ArtDribbleApp
import com.artdribble.android.app.Datastore
import com.artdribble.android.api.DribbleApiRepository
import com.artdribble.android.models.ArtsyArtwork
import com.artdribble.android.models.Dribble
import com.artdribble.android.mvp.presenter.ArtworkPresenter
import com.artdribble.android.mvp.view.ArtworkView
import com.artdribble.android.utils.get
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by robcook on 5/29/17.
 */
class ArtworkPresenterImpl(val dribbleRepository: DribbleApiRepository,
                           val datastore: Datastore) : BasePresenterImpl(), ArtworkPresenter {

    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(ArtDribbleApp.FORMAT_DAILY_ARTWORK_KEY, Locale.US)
    var weakView: WeakReference<ArtworkView>? = null

    override fun onResume() {
        super.onResume()
        loadDailyArtwork()
    }

    override fun setView(view: ArtworkView) {
        weakView = WeakReference(view)
    }

    private fun displayArtwork(dribble: Dribble?) {
        if (weakView?.get() == null || dribble?.artsyArtworkInfo == null) {
            return
        }

        weakView?.get()?.displayArtworkInfo(dribble.artsyArtworkInfo as ArtsyArtwork)
        weakView?.get()?.displayImage(dribble.artsyArtworkInfo.getImgUrlLargestAvailable())
        weakView?.get()?.displayArtistInfo(dribble.artsyArtistInfo?.embedded?.artists)
    }

    private fun loadDailyArtwork() {
        val now = Date()
        val key: String = simpleDateFormat.format(now)

        val localDribble: Dribble? = datastore.getDribble()

        if (key == localDribble?.dribbledate) {
            displayArtwork(localDribble)
            return
        }

        getArtworkFromApi(key)
    }

    // region API
    private fun getArtworkFromApi(key: String) {
        disposables.get() += dribbleRepository.getArtwork(key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = this::displayArtwork,
                        onError = { throwable -> onApiError(throwable, weakView?.get(), true) }
                )
    }
    // endregion
}