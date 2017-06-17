package com.artdribble.android.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by robcook on 6/17/17.
 */

object DateHelper {
    const val DF_HOUR_MIN_AMPM_STRING = "h:mm a"
    const val DF_MONTH_DAY_YEAR_STRING = "MM/dd/yyyy"

    @JvmField val DF_HOUR_MIN_AMPM_FORMAT = object : ThreadLocal<DateFormat>() {
        override fun initialValue(): DateFormat {
            return SimpleDateFormat(DF_HOUR_MIN_AMPM_STRING, Locale.US)
        }
    }

    @JvmField val DF_MONTH_DAY_YEAR_FORMAT = object : ThreadLocal<DateFormat>() {
        override fun initialValue(): DateFormat {
            return SimpleDateFormat(DF_MONTH_DAY_YEAR_STRING, Locale.US)
        }
    }
}



fun Date.asDateString(): String = DateHelper.DF_MONTH_DAY_YEAR_FORMAT.get().format(this)

fun Date.asTimeString(): String = DateHelper.DF_HOUR_MIN_AMPM_FORMAT.get().format(this)
