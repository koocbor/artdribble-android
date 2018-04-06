package com.artdribble.android.koin

import com.artdribble.android.api.DribbleApiRepository
import com.artdribble.android.app.Datastore
import com.artdribble.android.mvp.presenter.ArtworkPresenter
import com.artdribble.android.mvp.presenter.impl.ArtworkPresenterImpl
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

object PresenterModule {

    fun getModule() : Module = applicationContext {

        bean { ArtworkPresenterImpl(get<DribbleApiRepository>(),
                get<Datastore>()) } bind ArtworkPresenter::class
    }
}