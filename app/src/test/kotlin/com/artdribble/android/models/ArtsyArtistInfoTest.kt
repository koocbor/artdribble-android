package com.artdribble.android.models

import com.artdribble.android.test.utils.AssertExtensions
import com.artdribble.android.test.utils.newArtist
import com.artdribble.android.test.utils.newArtsyLinkCollection
import com.github.javafaker.Faker
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * Created by robcook on 6/5/17.
 */
class ArtsyArtistInfoTest {

    lateinit var assertExtension: AssertExtensions
    lateinit var faker: Faker

    @Before
    fun setUp() {
        assertExtension = AssertExtensions()
        faker = Faker()
    }

    @Test
    fun testConstructor() {
        var artsyArtistList: List<ArtsyArtist> = mutableListOf<ArtsyArtist>(faker.newArtist())

        val embedded: ArtsyArtistInfo.ArtsyArtistEmbedded = ArtsyArtistInfo.ArtsyArtistEmbedded(
            artsyArtistList
        )
        val links: ArtsyLinkCollection = faker.newArtsyLinkCollection()

        val artistInfo: ArtsyArtistInfo = ArtsyArtistInfo(
                embedded,
                links
        )

        assertNotNull(artistInfo)
        assertEquals(embedded, artistInfo.embedded)
        assertEquals(links, artistInfo.links)
        assertExtension.assertArtsyLinkCollectionEqual(links, artistInfo.links)

        assertNotNull(artistInfo.embedded)
        assertEquals(artsyArtistList, embedded.artists)
    }
}