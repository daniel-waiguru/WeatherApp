package com.danielwaiguru.weatherapp.testing.location

import com.danielwaiguru.weatherapp.domain.location.LocationService
import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import com.danielwaiguru.weatherapp.testing.testData.testLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class TestLocationService: LocationService {
    private val currentLocation = MutableStateFlow<ResultWrapper<UserLocation?>>(
        ResultWrapper.Success(testLocation)
    )
    private val isGpsEnabledResult = MutableStateFlow(true)
    override suspend fun getCurrentLocation(): ResultWrapper<UserLocation?> = currentLocation.first()

    override val isGpsEnabled: Boolean
        get() = isGpsEnabledResult.value

    fun setLocationResult(result: ResultWrapper<UserLocation?>) {
        currentLocation.update { result }
    }

    fun setGpsResult(enabled: Boolean) {
        isGpsEnabledResult.update { enabled }
    }
}