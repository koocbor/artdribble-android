package com.artdribble.android.koin

import com.artdribble.android.BuildConfig
import com.artdribble.android.api.DribbleApiRepository
import com.artdribble.android.api.DribbleApiService
import com.artdribble.android.api.interceptors.NetworkStateInterceptor
import com.artdribble.android.api.interceptors.RequestHeadersInterceptor
import com.artdribble.android.utils.NetworkMonitor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiModule {

    fun getModule() : Module = applicationContext {

        bean { DribbleApiRepository(get<DribbleApiService>()) }

        bean { getDribbleService(get<Gson>(),
                get<NetworkMonitor>()) }

    }

    private fun getDribbleService(gson: Gson,
                                  networkMonitor: NetworkMonitor) : DribbleApiService {

        val clientId: String = BuildConfig.DRIBBLE_CLIENT_ID
        val clientSecret: String = BuildConfig.DRIBBLE_CLIENT_SECRET
        val host: String = BuildConfig.DRIBBLE_API_HOST
        val okHttpClientBuilder : OkHttpClient.Builder = OkHttpClient.Builder()
                .addInterceptor(NetworkStateInterceptor(networkMonitor))
                .addInterceptor(RequestHeadersInterceptor(clientId, clientSecret))

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl(host)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(io.reactivex.schedulers.Schedulers.io()))
                .build()

        return retrofit.create(DribbleApiService::class.java)
    }
}