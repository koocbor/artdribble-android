package com.artdribble.android.api

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOError
import java.io.IOException

/**
 * Created by robcook on 5/29/17.
 */
class RequestHeadersInterceptor(
        val clientId: String,
        val clientSecret: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request();

        val newRequestBuilder = originalRequest.newBuilder();

        val credentials = Credentials.basic(clientId, clientSecret);
        newRequestBuilder.addHeader("Authorization", credentials);

        return chain.proceed(newRequestBuilder.build());
    }
}