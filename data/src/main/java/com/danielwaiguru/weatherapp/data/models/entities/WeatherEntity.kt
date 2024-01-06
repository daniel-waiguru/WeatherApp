package com.danielwaiguru.weatherapp.data.models.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class WeatherEntity(
    val description: String,
    val icon: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @Embedded
    val coordinates: CoordinatesEntity,
    val city: String,
    val country: String,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val date: Long,
    val lastUpdateAt: Long
) {
    data class CityEntity(
        val id: Int,
        val name: String,
        val country: String
    )
    data class TemperatureEntity(
        val temp: Double,
        val tempMin: Double,
        val tempMax: Double,
    )
}