package com.artdribble.android.models

import com.artdribble.android.test.utils.newArtsyArtistInfo
import com.artdribble.android.test.utils.newArtsyArtwork
import com.github.javafaker.Faker
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by robcook on 6/5/17.
 */
class DribbleTest {

    lateinit var faker: Faker
    lateinit var sdf: SimpleDateFormat

    @Before
    fun setup() {
        faker = Faker()
        sdf = SimpleDateFormat("yyyyMMdd", Locale.US)
    }

    @Test
    fun testConstructor() {
        val dribbleDate: String = sdf.format(Date())
        val artistInfo: ArtsyArtistInfo = faker.newArtsyArtistInfo()
        val slug: String = faker.lorem().characters(10, 25)
        val artwork: ArtsyArtwork = faker.newArtsyArtwork()

        var dribble: Dribble = Dribble(
                dribbleDate,
                artistInfo,
                slug,
                artwork
        )

        assertNotNull(dribble)
        assertEquals(dribbleDate, dribble.dribbledate)
        assertEquals(artistInfo, dribble.artsyArtistInfo)
        assertEquals(slug, dribble.artsyArtworkSlug)
        assertEquals(artwork, dribble.artsyArtworkInfo)
    }
}