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

package com.danielwaiguru.weatherapp.data.sources.local.daos

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danielwaiguru.weatherapp.data.base.BaseDbTest
import com.danielwaiguru.weatherapp.testing.testData.testForecastEntity
import com.danielwaiguru.weatherapp.testing.testData.testWeatherEntity
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class WeatherDaoTest : BaseDbTest() {
    private lateinit var weatherDao: WeatherDao

    @Before
    fun setUp() {
        weatherDao = db.weatherDao()
    }

    @Test
    fun weatherDao_saves_and_retrieves_current_weather_data_correctly() {
        runTest {
            weatherDao.saveWeather(testWeatherEntity())
            val weatherEntity = weatherDao.getCurrentWeather().first()
            Truth.assertThat(weatherEntity).isNotNull()
            Truth.assertThat(weatherEntity?.id).isEqualTo(testWeatherEntity().id)
        }
    }

    @Test
    fun weatherDao_saves_and_retrieves_forecast_weather_data_correctly() {
        runTest {
            weatherDao.saveWeatherForecast(listOf(testForecastEntity(id = null)))
            val forecastEntity = weatherDao.getWeatherForecast().first()
            Truth.assertThat(forecastEntity).isNotEmpty()
        }
    }

    @Test
    fun weatherDao_assigns_valid_ID_to_forecast_entity() {
        runTest {
            weatherDao.saveWeatherForecast(listOf(testForecastEntity(id = null)))
            val forecastEntity = weatherDao.getWeatherForecast().first()
            Truth.assertThat(forecastEntity[0].id).isNotNull()
        }
    }

    @Test
    fun weatherDao_deletes_weather_forecast_records() {
        runTest {
            weatherDao.saveWeatherForecast(listOf(testForecastEntity(id = null)))
            val forecastEntity = weatherDao.getWeatherForecast().first()
            Truth.assertThat(forecastEntity[0].id).isNotNull()
            weatherDao.deleteAllForecasts()
            val forecastEntities = weatherDao.getWeatherForecast().first()
            Truth.assertThat(forecastEntities).isEmpty()
        }
    }

    @Test
    fun weatherDao_deletes_current_weather_record() {
        runTest {
            weatherDao.saveWeather(testWeatherEntity())
            val weatherEntity = weatherDao.getCurrentWeather().first()
            Truth.assertThat(weatherEntity).isNotNull()
            weatherDao.deleteAll()
            val weatherNullEntity = weatherDao.getCurrentWeather().first()
            Truth.assertThat(weatherNullEntity).isNull()
        }
    }
}