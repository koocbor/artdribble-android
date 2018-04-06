package com.artdribble.android.models

import com.artdribble.android.app.ArtDribbleApp
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by robcook on 5/29/17.
 */
data class Dribble(
        val dribbledate: String,
        val artsyArtistInfo: ArtsyArtistInfo?,
        val artsyArtworkSlug : String?,
        val artsyArtworkInfo: ArtsyArtwork
) {

    companion object {
        fun getTodayKey(): String {
            val sdf: SimpleDateFormat = SimpleDateFormat(ArtDribbleApp.FORMAT_DAILY_ARTWORK_KEY, Locale.US)
            val cal: Calendar = Calendar.getInstance()
            return sdf.format(cal.time)
        }
    }

    fun getNotificationMsg(): String {
        var msg: String = ""

        msg += artsyArtistInfo?.embedded?.artists?.get(0)?.name ?: ""

        if (msg.isEmpty()) {
            msg += artsyArtworkInfo?.collecting_institution
        }

        return msg
    }
}