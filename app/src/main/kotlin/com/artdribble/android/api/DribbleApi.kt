package com.artdribble.android.api

import com.artdribble.android.BuildConfig
import com.artdribble.android.models.Dribble
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by robcook on 5/29/17.
 */
class DribbleApi(
        val clientId: String,
        val clientSecret: String,
        val host: String,
        val gson: Gson) {

    interface DribbleService {

        @GET("v1/artwork/{dte}")
        fun getArtwork(@Path("dte") dte: String): Call<Dribble>

    }

    private fun getDribbleService() : DribbleService {

        val okHttpClientBuilder : OkHttpClient.Builder = OkHttpClient.Builder()

        okHttpClientBuilder.addInterceptor(RequestHeadersInterceptor(clientId, clientSecret))

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor : HttpLoggingInterceptor = HttpLoggingInterceptor();
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl(host)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(DribbleService::class.java)
    }

    fun getArtwork(dte: String) : Call<Dribble> {
        return getDribbleService().getArtwork(dte)
    }

}