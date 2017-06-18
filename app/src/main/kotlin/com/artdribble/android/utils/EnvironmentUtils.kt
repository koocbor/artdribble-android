package com.artdribble.android.utils

import android.content.Context
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by robcook on 6/18/17.
 */

fun Context.readTextFile(inputStream: InputStream?): String? {

    if (inputStream == null) {
        return null
    }

    val outputStream = ByteArrayOutputStream()

    val buf = ByteArray(1024)
    var len: Int = inputStream.read(buf)
    try {
        while (len != -1) {
            outputStream.write(buf, 0, len)
            len = inputStream.read(buf)
        }
        outputStream.close()
        inputStream.close()
    } catch (e: IOException) {
        return null
    }

    return outputStream.toString()
}