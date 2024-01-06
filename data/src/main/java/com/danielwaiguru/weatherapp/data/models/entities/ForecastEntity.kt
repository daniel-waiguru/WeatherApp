package com.danielwaiguru.weatherapp.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danielwaiguru.weatherapp.domain.models.Coordinates
@Entity(tableName = "weather_forecast")
data class ForecastEntity(
    val description: String,
    val icon: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val coordinates: Coordinates,
    val city: WeatherEntity.CityEntity,
    val temperature: WeatherEntity.TemperatureEntity,
    val date: Long
)