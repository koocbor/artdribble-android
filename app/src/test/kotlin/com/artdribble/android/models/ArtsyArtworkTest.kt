package com.artdribble.android.models

import com.artdribble.android.test.utils.AssertExtensions
import com.artdribble.android.test.utils.newArtsyLinkCollection
import com.github.javafaker.Faker
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by robcook on 6/5/17.
 */
class ArtsyArtworkTest {

    val assertExtension: AssertExtensions = AssertExtensions()
    lateinit var faker: Faker
    val sdf: SimpleDateFormat = SimpleDateFormat("yyyyMMdd", Locale.US)

    @Before
    fun setUp() {
        faker = Faker()
    }

    @Test
    fun testConstructor() {
        val id: String = faker.idNumber().valid()
        val collectingInstitution: String = faker.ancient().god()
        val createdAt: Date = faker.date().past(2000, TimeUnit.DAYS)
        val dateTxt: String = sdf.format(Date())
        val imageVersions: List<String> = mutableListOf<String>("large", "med", "small")
        var links: ArtsyLinkCollection = faker.newArtsyLinkCollection()
        val medium: String = faker.code().isbnGroup()
        val signature: String = faker.artist().name()
        val slug: String = faker.lorem().characters(10, 25)
        val title: String = faker.ancient().hero()

        val artwork: ArtsyArtwork = ArtsyArtwork(
                id,
                collectingInstitution,
                createdAt,
                dateTxt,
                imageVersions,
                links,
                medium,
                signature,
                slug,
                title
        )

        assertNotNull(artwork)
        assertEquals(id, artwork.id)
        assertEquals(collectingInstitution, artwork.collecting_institution)
        assertEquals(createdAt, artwork.created_at)
        assertEquals(dateTxt, artwork.date)
        assertEquals(imageVersions, artwork.image_versions)
        assertEquals(links, artwork.links)
        assertExtension.assertArtsyLinkCollectionEqual(links, artwork.links)
        assertEquals(medium, artwork.medium)
        assertEquals(signature, artwork.signature)
        assertEquals(slug, artwork.slug)
        assertEquals(title, artwork.title)

        assertEquals(links.image?.href, artwork.getImgUrlLargestAvailable())
    }
}