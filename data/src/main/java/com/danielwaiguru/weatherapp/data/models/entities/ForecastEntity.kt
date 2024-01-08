package com.danielwaiguru.weatherapp.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "weather_forecast")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val date: Long,
    val temp: Double,
    val conditionId: Int
)