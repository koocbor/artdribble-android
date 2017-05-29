package com.artdribble.android.dagger.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by robcook on 5/29/17.
 */
@Module
class GsonModule() {

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder()
                .create()
}