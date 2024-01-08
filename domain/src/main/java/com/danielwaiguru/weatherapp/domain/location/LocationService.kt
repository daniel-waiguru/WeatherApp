package com.danielwaiguru.weatherapp.domain.location

import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper

interface LocationService {
    suspend fun getCurrentLocation(): ResultWrapper<UserLocation?>
    val isGpsEnabled: Boolean
}
