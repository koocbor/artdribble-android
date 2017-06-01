package com.artdribble.android.models

/**
 * Created by robcook on 5/29/17.
 */
data class ArtsyLinkCollection(
        val artists: ArtsyLink? = null,
        val genes: ArtsyLink? = null,
        val image: ArtsyLink? = null,
        val partner: ArtsyLink? = null,
        val permalink: ArtsyLink? = null,
        val published_artworks: ArtsyLink? = null,
        val next: ArtsyLink? = null,
        val self: ArtsyLink? = null,
        val similar_artworks: ArtsyLink? = null,
        val similar_contemporary_artists: ArtsyLink? = null,
        val thumbnail: ArtsyLink? = null
)