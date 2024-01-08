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
class WeatherDaoTest: BaseDbTest() {
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