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

package com.danielwaiguru.weatherapp.domain.models

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val userLocation: UserLocation,
    val city: String,
    val country: String,
    val conditionId: Int,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val date: Long,
    val lastUpdateAt: Long,
) {
    data class City(
        val id: Int,
        val name: String,
        val country: String,
    )
    data class Temperature(
        val temp: Double,
        val tempMin: Double,
        val tempMax: Double,
    )
}
