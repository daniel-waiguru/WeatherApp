package com.danielwaiguru.weatherapp.domain.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.getDayName(): String {
    val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
    val date = Date(this * 1000)
    return sdf.format(date)
//    val utcDate = Date(this)
//    val formatter = SimpleDateFormat("EEEE", Locale.getDefault())
//    formatter.timeZone = TimeZone.getTimeZone("UTC")
//    val c = Calendar.getInstance().apply {
//        set(Calendar.MILLISECOND, this@getDayName.toInt())
//    }
//    val formattedUtcDate = formatter.format(utcDate)
//    val timeZone = TimeZone.getDefault()
//    formatter.timeZone = timeZone
//    return formatter.format(formatter.parse(formattedUtcDate))
}