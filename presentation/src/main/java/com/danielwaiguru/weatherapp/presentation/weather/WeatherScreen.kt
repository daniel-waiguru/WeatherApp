package com.danielwaiguru.weatherapp.presentation.weather

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danielwaiguru.weatherapp.domain.models.Weather

@Composable
fun WeatherRoute(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.currentWeatherUIState.collectAsStateWithLifecycle()
    WeatherScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        state = uiState
    )
}

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    state: WeatherScreenState
) {

}


@Composable
fun CurrentWeatherComponent(
    modifier: Modifier = Modifier,
    weather: Weather?
) {

}