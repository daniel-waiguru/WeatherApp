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

package com.danielwaiguru.weatherapp.data.models.responses

import com.danielwaiguru.weatherapp.data.models.dtos.CloudsDto
import com.danielwaiguru.weatherapp.data.models.dtos.CoordinatesDto
import com.danielwaiguru.weatherapp.data.models.dtos.Main
import com.danielwaiguru.weatherapp.data.models.dtos.Rain
import com.danielwaiguru.weatherapp.data.models.dtos.Sys
import com.danielwaiguru.weatherapp.data.models.dtos.WeatherInfoDto
import com.danielwaiguru.weatherapp.data.models.dtos.Wind
import com.squareup.moshi.Json

data class WeatherDto(
    val base: String,
    val clouds: CloudsDto,
    val cod: Int,
    @field:Json(name = "coord")
    val coordinates: CoordinatesDto,
    @field:Json(name = "dt")
    val date: Long,
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Rain,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<WeatherInfoDto>,
    val wind: Wind
)
