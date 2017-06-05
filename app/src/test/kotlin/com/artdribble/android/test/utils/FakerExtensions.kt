package com.artdribble.android.test.utils

import com.artdribble.android.models.ArtsyArtist
import com.artdribble.android.models.ArtsyLink
import com.artdribble.android.models.ArtsyLinkCollection
import com.github.javafaker.Faker
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by robcook on 5/31/17.
 */

fun Faker.birthdate(): Date {

    var c: Calendar = Calendar.getInstance()
    c.add(Calendar.YEAR, -18)
    return this.date().past(300 * 365, TimeUnit.DAYS, c.time)
}

fun Faker.newArtist(): ArtsyArtist {
    var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy", Locale.US)

    val artist: ArtsyArtist = ArtsyArtist(
            simpleDateFormat.format(this.birthdate()),
            this.demographic().sex(),
            this.address().cityName(),
            null,
            null,
            this.artist().name(),
            this.address().country()
    )

    return artist
}

fun Faker.newArtsyLink(isTemplated: Boolean?): ArtsyLink {
    return ArtsyLink(
            this.internet().url(),
            isTemplated ?: false
    )
}

fun Faker.newArtsyLinkCollection(): ArtsyLinkCollection {
    return ArtsyLinkCollection(
            newArtsyLink(false),
            newArtsyLink(false),
            newArtsyLink(true),
            newArtsyLink(false),
            newArtsyLink(false),
            newArtsyLink(false),
            newArtsyLink(false),
            newArtsyLink(false),
            newArtsyLink(false),
            newArtsyLink(false),
            newArtsyLink(false)
    )
}