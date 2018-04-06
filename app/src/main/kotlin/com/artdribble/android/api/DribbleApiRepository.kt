package com.artdribble.android.api

import com.artdribble.android.models.Dribble
import io.reactivex.Single

class DribbleApiRepository(val apiService: DribbleApiService) {

    fun getArtwork(dte: String) : Single<Dribble> {
        return apiService.getArtwork(dte)
    }
}