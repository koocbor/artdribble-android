package com.artdribble.android.test.utils

import com.artdribble.android.models.*
import com.github.javafaker.Faker
import org.apache.tools.ant.taskdefs.Local
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

fun Faker.newArtsyArtistInfo() : ArtsyArtistInfo {
    val embedded: ArtsyArtistInfo.ArtsyArtistEmbedded = ArtsyArtistInfo.ArtsyArtistEmbedded(
            mutableListOf<ArtsyArtist>(newArtist())
    )
    return ArtsyArtistInfo(
            embedded,
            newArtsyLinkCollection()
    )
}

fun Faker.newArtsyArtwork() : ArtsyArtwork {

    val sdf: SimpleDateFormat = SimpleDateFormat("yyyyMMdd", Locale.US)

    return ArtsyArtwork(
            this.idNumber().valid(),
            this.gameOfThrones().dragon(),
            Date(),
            sdf.format(Date()),
            mutableListOf<String>("large", "medium", "small"),
            newArtsyLinkCollection(),
            this.stock().nyseSymbol(),
            null,
            this.lorem().characters(10, 25),
            this.ancient().titan()
    )
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