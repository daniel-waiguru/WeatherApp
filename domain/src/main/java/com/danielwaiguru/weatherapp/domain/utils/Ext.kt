/*
 * MIT License
 *
 * Copyright (c) 2024 Daniel Waiguru
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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