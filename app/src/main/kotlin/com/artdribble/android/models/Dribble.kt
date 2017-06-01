package com.artdribble.android.models

/**
 * Created by robcook on 5/29/17.
 */
data class Dribble(
        val dribbledate: String,
        val artsyArtistInfo: ArtsyArtistInfo?,
        val artsyArtworkSlug : String?,
        val artsyArtworkInfo: ArtsyArtwork
)