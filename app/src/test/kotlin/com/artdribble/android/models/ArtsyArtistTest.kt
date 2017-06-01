package com.artdribble.android.models

import com.artdribble.android.test.utils.*
import com.github.javafaker.Faker
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by robcook on 5/31/17.
 */
class ArtsyArtistTest {

    lateinit var faker: Faker

    @Before
    fun setUp() {
        faker = Faker()
    }

    @Test
    fun getBirthdateAndHometown() {
        var artist: ArtsyArtist = faker.newArtist()

        assertNotNull(artist)

        var birthdateAndHometown: String = "b.${artist.birthdate} ${artist.hometown}"
        assertEquals(birthdateAndHometown, artist.getBirthdateAndHometown())
    }

}