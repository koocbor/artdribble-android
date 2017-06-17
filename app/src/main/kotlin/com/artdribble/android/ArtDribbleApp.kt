package com.artdribble.android

import android.app.Application
import com.artdribble.android.dagger.AppComponent
import com.artdribble.android.dagger.DaggerAppComponent
import com.artdribble.android.dagger.module.ApiModule
import com.artdribble.android.dagger.module.DatastoreModule
import com.artdribble.android.dagger.module.GsonModule

/**
 * Created by robcook on 5/29/17.
 */
class ArtDribbleApp : Application () {

    companion object {
        @JvmStatic lateinit var instance: ArtDribbleApp
        @JvmStatic lateinit var appComponent: AppComponent
        val DEFAULT_NOTIFY_TIME = "8:00 AM"
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .apiModule(ApiModule())
                .datastoreModule(DatastoreModule(this))
                .gsonModule(GsonModule())
                .build()

        appComponent.inject(this)
    }
}