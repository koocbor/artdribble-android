package com.artdribble.android.models

import com.google.gson.annotations.SerializedName

/**
 * Created by robcook on 5/30/17.
 */
data class ArtsyArtistInfo(
        @SerializedName("_embedded") val embedded: ArtsyArtistEmbedded?,
        @SerializedName("_links") val links: ArtsyLinkCollection?
) {

    data class ArtsyArtistEmbedded(
            val artists: List<ArtsyArtist>?
    )
}