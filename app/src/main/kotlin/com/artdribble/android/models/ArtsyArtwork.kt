package com.artdribble.android.models

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by robcook on 5/29/17.
 */
data class ArtsyArtwork(
        val id: String,
        val collecting_institution: String?,
        val created_at: Date,
        val date: String,
        val image_versions: List<String>?,
        @SerializedName("_links") val links: ArtsyLinkCollection,
        val medium: String,
        val signature: String?,
        val slug: String,
        val title: String?
        ) {

    fun getImgUrlLargestAvailable(): String? {

        val templated: Boolean = links.image?.templated ?: false
        var imgLink: String? = links.image?.href
        if (imgLink == null) {
            return null
        }

        if (templated && image_versions != null && image_versions.isNotEmpty()) {
            imgLink = imgLink.replace("{image_version}", image_versions[0])
        }

        return imgLink
    }
}