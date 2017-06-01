package com.artdribble.android.test.utils

import com.artdribble.android.models.ArtsyArtist
import com.github.javafaker.Faker
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by robcook on 5/31/17.
 */

fun Faker.newArtist(): ArtsyArtist {

    var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy", Locale.US)
    var c: Calendar = Calendar.getInstance()
    c.add(Calendar.YEAR, -18)
    var birthdate: Date = this.date().past(300 * 365, TimeUnit.DAYS, c.time)

    val artist: ArtsyArtist = ArtsyArtist(
            simpleDateFormat.format(birthdate),
            this.demographic().sex(),
            this.address().cityName(),
            null,
            null,
            this.artist().name(),
            this.address().country()
    )

    return artist
}