package com.artdribble.android.dagger.module

import android.app.Application
import android.preference.PreferenceManager
import com.artdribble.android.Datastore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by robcook on 5/31/17.
 */
@Module
class DatastoreModule(val application: Application) {

    @Provides
    @Singleton
    fun providesDatastore(gson: Gson): Datastore {
        return Datastore(PreferenceManager.getDefaultSharedPreferences(application), gson)
    }
}