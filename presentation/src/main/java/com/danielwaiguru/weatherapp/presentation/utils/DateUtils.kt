package com.danielwaiguru.weatherapp.presentation.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    fun toFormattedDate(timeInMillis: Long): String {
        return try {
            val outDateFormat = SimpleDateFormat(
                "d MMM, yyyy, HH:mm",
                Locale.getDefault()
            )
            val outTimeFormat = SimpleDateFormat(
                "HH:mm",
                Locale.getDefault()
            )
            if (DateUtils.isToday(timeInMillis)) {
                outTimeFormat.format(timeInMillis)
            } else {
                outDateFormat.format(timeInMillis)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}