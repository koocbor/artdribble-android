package com.artdribble.android.ui.activity

import android.os.Bundle
import android.widget.ImageView
import com.artdribble.android.ArtDribbleApp

import com.artdribble.android.R
import com.artdribble.android.mvp.presenter.ArtworkPresenter
import com.artdribble.android.mvp.view.ArtworkView
import com.bumptech.glide.Glide
import javax.inject.Inject

class MainActivity : BaseActivity(),
        ArtworkView {
    lateinit var artImage: ImageView

    @Inject
    protected lateinit var presenter: ArtworkPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArtDribbleApp.appComponent.inject(this)

        setContentView(R.layout.activity_main)

        initViews()
        presenter.setView(this)

        presenter.loadDailyArtwork()
    }

    private fun initViews() {
        artImage = findViewById(R.id.art_image) as ImageView
    }

    // region ArtworkView
    override fun toggleProgress(show: Boolean) {

    }

    override fun displayImage(url: String?) {
        Glide.with(this)
                .load(url)
                .into(artImage)
    }
    // endregion
}
