package com.artdribble.android.dagger.module

import com.artdribble.android.api.DribbleApi
import com.artdribble.android.mvp.presenter.ArtworkPresenter
import com.artdribble.android.mvp.presenter.impl.ArtworkPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Created by robcook on 5/29/17.
 */
@Module
class PresenterModule {

    @Provides
    fun provideArtworkPresenter(artworkPresenter: ArtworkPresenterImpl): ArtworkPresenter = artworkPresenter
}