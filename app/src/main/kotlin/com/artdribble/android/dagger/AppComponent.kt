package com.artdribble.android.dagger

import com.artdribble.android.dagger.module.ApiModule
import com.artdribble.android.dagger.module.GsonModule
import com.artdribble.android.dagger.module.PresenterModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by robcook on 5/29/17.
 */
@Singleton
@Component(modules = arrayOf(
        ApiModule::class,
        GsonModule::class,
        PresenterModule::class
))
interface AppComponent : ArtDribbleComponent {

}