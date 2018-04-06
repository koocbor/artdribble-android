package com.artdribble.android.koin

import android.preference.PreferenceManager
import com.artdribble.android.app.Datastore
import com.artdribble.android.app.GsonAnnotationExclusionPolicy
import com.artdribble.android.utils.LiveNetworkMonitor
import com.artdribble.android.utils.NetworkMonitor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

object AppModule {

    fun getModule() : Module = applicationContext {

        bean { Datastore(PreferenceManager.getDefaultSharedPreferences(androidApplication()),
                get<Gson>())
        }

        bean { getGson() }

        bean { LiveNetworkMonitor(androidApplication()) } bind NetworkMonitor::class
    }



    private fun getGson() : Gson = GsonBuilder()
            .setExclusionStrategies(GsonAnnotationExclusionPolicy())
            .create()
}