package com.artdribble.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.artdribble.android.R
import com.artdribble.android.models.ArtsyArtist

import kotlinx.android.synthetic.main.view_artist_info.view.*

/**
 * Created by robcook on 5/30/17.
 */
class ArtistInfoView : LinearLayout {

    var artist: ArtsyArtist? = null

    constructor(context: Context): super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {

        val rootView: View = LayoutInflater.from(context).inflate(R.layout.view_artist_info, this, true)

    }

    fun displayArtist(artist: ArtsyArtist) {
        this.artist = artist;

        artist_name.text = artist.name
        if (artist.getBirthdateAndHometown() != null) {
            artist_birthdate.text = artist.getBirthdateAndHometown()
            artist_birthdate.visibility = View.VISIBLE
        } else {
            artist_birthdate.visibility = View.GONE
        }

        if (artist.nationality != null) {
            artist_nationality.text = artist.nationality;
            artist_nationality.visibility = View.VISIBLE
        } else {
            artist_nationality.visibility = View.GONE
        }
    }
}