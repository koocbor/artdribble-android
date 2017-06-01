package com.artdribble.android.models

import com.google.gson.annotations.SerializedName

/**
 * Created by robcook on 5/30/17.
 */
data class ArtsyArtist(
        val birthdate: String?,
        val gender: String?,
        val hometown: String?,
        @SerializedName("image_versions") val imageVersions: List<String>?,
        @SerializedName("_links") val links: ArtsyLinkCollection?,
        val name: String?,
        val nationality: String?
) {

    fun getBirthdateAndHometown(): String? {
        var s: String? = null

        if (birthdate != null) {
            s = "b.$birthdate"
        }

        if (hometown != null) {
            if (s != null) {
                s += " $hometown"
            } else {
                s = "$hometown"
            }
        }

        return s
    }
}