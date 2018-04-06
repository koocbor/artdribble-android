package com.artdribble.android.utils

import android.content.Context
import android.net.ConnectivityManager
import java.lang.ref.WeakReference

interface NetworkMonitor {
    fun isConnected() : Boolean
}

class LiveNetworkMonitor(context: Context) : NetworkMonitor {

    val weakApplicationContext = WeakReference<Context>(context.applicationContext)

    override fun isConnected(): Boolean {
        weakApplicationContext.get()?.let { ctx ->
            val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }
        return false
    }
}

class NoNetworkConnectionException : Throwable()