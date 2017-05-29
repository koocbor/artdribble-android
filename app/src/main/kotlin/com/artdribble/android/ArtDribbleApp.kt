package com.artdribble.android

import android.app.Application
import com.artdribble.android.dagger.AppComponent
import com.artdribble.android.dagger.DaggerAppComponent
import com.artdribble.android.dagger.module.ApiModule
import com.artdribble.android.dagger.module.GsonModule
import javax.inject.Inject


/**
 * Created by robcook on 5/29/17.
 */
class ArtDribbleApp : Application () {

    companion object {
        @JvmStatic lateinit var instance: ArtDribbleApp
        @JvmStatic lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .apiModule(ApiModule())
                .gsonModule(GsonModule())
                .build()

        appComponent.inject(this)
    }
}