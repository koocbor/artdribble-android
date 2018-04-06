package com.artdribble.android.api.interceptors

import com.artdribble.android.utils.NetworkMonitor
import com.artdribble.android.utils.NoNetworkConnectionException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkStateInterceptor(val networkMonitor: NetworkMonitor) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        if (networkMonitor.isConnected()) {
            return chain!!.proceed(chain.request())
        } else {
            throw NoNetworkConnectionException()
        }
    }
}