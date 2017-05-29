package com.artdribble.android.dagger.module

import com.artdribble.android.BuildConfig
import com.artdribble.android.api.DribbleApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by robcook on 5/29/17.
 */
@Module
class ApiModule {

    @Provides
    @Singleton
    fun providesApiHost(): String = BuildConfig.DRIBBLE_API_HOST

    @Provides
    fun provideDribbleApi(apiHost: String, gson: Gson): DribbleApi {
        val clientId: String = BuildConfig.DRIBBLE_CLIENT_ID
        val clientSecret: String = BuildConfig.DRIBBLE_CLIENT_SECRET
        return DribbleApi(clientId, clientSecret, apiHost, gson)
    }

}