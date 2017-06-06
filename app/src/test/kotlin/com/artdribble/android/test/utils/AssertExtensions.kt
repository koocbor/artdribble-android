package com.artdribble.android.test.utils

import com.artdribble.android.models.ArtsyLinkCollection
import junit.framework.Assert
import junit.framework.Assert.assertEquals

/**
 * Created by robcook on 6/5/17.
 */
class AssertExtensions {

    fun assertArtsyLinkCollectionEqual(
            collection1: ArtsyLinkCollection?,
            collection2: ArtsyLinkCollection?
    ) {

        assertEquals(collection1?.artists, collection2?.artists)
        assertEquals(collection1?.genes, collection2?.genes)
        assertEquals(collection1?.image, collection2?.image)
        assertEquals(collection1?.next, collection2?.next)
        assertEquals(collection1?.partner, collection2?.partner)
        assertEquals(collection1?.permalink, collection2?.permalink)
        assertEquals(collection1?.published_artworks, collection2?.published_artworks)
        assertEquals(collection1?.self, collection2?.self)
        assertEquals(collection1?.similar_artworks, collection2?.similar_artworks)
        assertEquals(collection1?.similar_contemporary_artists, collection2?.similar_contemporary_artists)
        assertEquals(collection1?.thumbnail, collection2?.thumbnail)
    }
}