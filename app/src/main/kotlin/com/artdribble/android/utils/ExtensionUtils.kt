package com.artdribble.android.utils

import android.content.Context
import com.artdribble.android.R
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException

fun CompositeDisposable.get(): CompositeDisposable {
    if (this.isDisposed) {
        return CompositeDisposable()
    }
    return this
}

fun Throwable?.errorMsg(context: Context?) : String =
        when (this) {
            is HttpException -> this.errorMsg(context)
            is NoNetworkConnectionException -> context?.getString(R.string.err_no_connection) ?: "No network connection found. Please check your network connection and try again."
            else -> context?.getString(R.string.err_default) ?: "An error occurred"
        }