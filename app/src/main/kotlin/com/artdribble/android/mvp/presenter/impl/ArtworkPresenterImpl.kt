package com.artdribble.android.mvp.presenter.impl

import com.artdribble.android.api.DribbleApi
import com.artdribble.android.models.ArtsyArtwork
import com.artdribble.android.models.Dribble
import com.artdribble.android.mvp.presenter.ArtworkPresenter
import com.artdribble.android.mvp.view.ArtworkView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by robcook on 5/29/17.
 */
class ArtworkPresenterImpl : ArtworkPresenter {

    val DATE_KEY_FORMAT = "yyyyMMdd"

    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(DATE_KEY_FORMAT, Locale.US)
    val dribbleApi: DribbleApi
    var dribble: Dribble? = null
    var weakView: WeakReference<ArtworkView>? = null

    @Inject
    constructor(dribbleApi: DribbleApi) {
        this.dribbleApi = dribbleApi
    }

    override fun loadDailyArtwork() {
        var now: Date = Date()
        var key: String = simpleDateFormat.format(now)
        dribbleApi.getArtwork(key).enqueue(object : Callback<Dribble> {

            override fun onResponse(call: Call<Dribble>?, response: Response<Dribble>?) {

                if (response != null && response.isSuccessful()) {
                    dribble = response.body()
                    displayArtwork()
                }
            }

            override fun onFailure(call: Call<Dribble>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun setView(view: ArtworkView) {
        weakView = WeakReference(view)
    }

    private fun displayArtwork() {
        if (weakView?.get() == null || dribble?.artsyArtworkInfo == null) {
            return;
        }

        weakView?.get()?.displayArtworkInfo(dribble?.artsyArtworkInfo as ArtsyArtwork)
        weakView?.get()?.displayImage(dribble?.artsyArtworkInfo?.getImgUrlLargestAvailable())
        weakView?.get()?.displayArtistInfo(dribble?.artsyArtistInfo?.embedded?.artists)
    }

}