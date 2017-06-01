package com.artdribble.android.mvp.view

import com.artdribble.android.models.ArtsyArtist
import com.artdribble.android.models.ArtsyArtwork

/**
 * Created by robcook on 5/29/17.
 */
interface ArtworkView : BaseView {

    fun displayArtistInfo(info: List<ArtsyArtist>?)
    fun displayArtworkInfo(info: ArtsyArtwork)
    fun displayImage(url: String?)

}