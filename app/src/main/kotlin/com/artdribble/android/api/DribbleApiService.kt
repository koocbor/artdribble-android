package com.artdribble.android.api

import com.artdribble.android.models.Dribble
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DribbleApiService {

    @GET("v1/artwork/{dte}")
    fun getArtwork(@Path("dte") dte: String): Single<Dribble>
}