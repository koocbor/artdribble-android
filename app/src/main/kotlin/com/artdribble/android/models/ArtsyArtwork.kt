package com.artdribble.android.models

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by robcook on 5/29/17.
 */
data class ArtsyArtwork(
        val id: String,
        val collecting_institution: String?,
        val created_at: Date,
        val date: String,
        val medium: String,
        val signature: String?,
        val slug: String,
        val title: String?
        ) {
}