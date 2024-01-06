package com.danielwaiguru.weatherapp.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danielwaiguru.weatherapp.domain.models.Coordinates

@Entity(tableName = "current_weather")
data class WeatherEntity(
    val description: String,
    val icon: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val coordinates: Coordinates,
    val city: CityEntity,
    val temperature: TemperatureEntity,
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