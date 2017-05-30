package com.artdribble.android.mvp.presenter

import com.artdribble.android.mvp.view.ArtworkView

/**
 * Created by robcook on 5/29/17.
 */

interface ArtworkPresenter {

    fun loadDailyArtwork()
    fun setView(view: ArtworkView)

}
