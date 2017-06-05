package com.artdribble.android.models

import com.artdribble.android.test.utils.*
import com.github.javafaker.Faker
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by robcook on 5/31/17.
 */
class ArtsyArtistTest {

    lateinit var faker: Faker
    lateinit var sdf: SimpleDateFormat

    @Before
    fun setUp() {
        faker = Faker()
        sdf = SimpleDateFormat("yyyy", Locale.US)
    }

    @Test
    fun testGetBirthdateAndHometown() {
        var artist: ArtsyArtist = faker.newArtist()

        assertNotNull(artist)

        var birthdateAndHometown: String = "b.${artist.birthdate} ${artist.hometown}"
        assertEquals(birthdateAndHometown, artist.getBirthdateAndHometown())

        var artistNoBirthdate : ArtsyArtist = ArtsyArtist(
                null,
                faker.demographic().sex(),
                faker.address().cityName(),
                null,
                null,
                faker.superhero().name(),
                faker.address().country()
        )

        birthdateAndHometown = "${artistNoBirthdate.hometown}"
        assertEquals(birthdateAndHometown, artistNoBirthdate.getBirthdateAndHometown())


        var artistNoHometown : ArtsyArtist = ArtsyArtist(
                sdf.format(faker.birthdate()),
                faker.demographic().sex(),
                null,
                null,
                null,
                faker.superhero().name(),
                faker.address().country()
        )

        birthdateAndHometown = "b.${artistNoHometown.birthdate}"
        assertEquals(birthdateAndHometown, artistNoHometown.getBirthdateAndHometown())
    }

    @Test
    fun testConstructor() {

        var birthdate: String = sdf.format(faker.birthdate())
        var gender: String = faker.demographic().sex()
        var hometown: String = faker.address().cityName()
        var imageSizeList: MutableList<String> = mutableListOf("large", "med", "small")
        var links: ArtsyLinkCollection = faker.newArtsyLinkCollection()
        var name: String = faker.superhero().name()
        var nationality: String = faker.address().country()


        var artist: ArtsyArtist = ArtsyArtist(
                birthdate,
                gender,
                hometown,
                imageSizeList,
                links,
                name,
                nationality
        )

        assertNotNull(artist)
        assertEquals(birthdate, artist.birthdate)
        assertEquals(gender, artist.gender)
        assertEquals(hometown, artist.hometown)
        assertEquals(imageSizeList.size, artist.imageVersions?.size)
        assertEquals(name, artist.name)
        assertEquals(nationality, artist.nationality)

        assertEquals(links.artists?.href, artist.links?.artists?.href)
        assertEquals(links.genes?.href, artist.links?.genes?.href)
        assertEquals(links.image?.href, artist.links?.image?.href)
        assertEquals(links.image?.templated, artist.links?.image?.templated)
        assertEquals(links.partner?.href, artist.links?.partner?.href)
        assertEquals(links.permalink?.href, artist.links?.permalink?.href)
        assertEquals(links.published_artworks?.href, artist.links?.published_artworks?.href)
        assertEquals(links.next?.href, artist.links?.next?.href)
        assertEquals(links.self?.href, artist.links?.self?.href)
        assertEquals(links.similar_artworks?.href, artist.links?.similar_artworks?.href)
        assertEquals(links.similar_contemporary_artists?.href, artist.links?.similar_contemporary_artists?.href)
        assertEquals(links.thumbnail?.href, artist.links?.thumbnail?.href)

    }

}