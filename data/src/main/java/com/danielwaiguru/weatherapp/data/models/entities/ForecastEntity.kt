package com.danielwaiguru.weatherapp.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "weather_forecast")
data class ForecastEntity(
    val icon: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val date: Long,
    val temp: Double,
    val main: String,
    val lastUpdatedAt: Long
)