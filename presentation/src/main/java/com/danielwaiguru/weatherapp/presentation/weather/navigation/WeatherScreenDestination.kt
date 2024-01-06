package com.danielwaiguru.weatherapp.presentation.weather.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.danielwaiguru.weatherapp.presentation.navigation.AppNavigationDestination


object WeatherScreenDestination: AppNavigationDestination {
    override val route: String = "com.danielwaiguru.weatherapp.WeatherScreen"
    override val destination: String = "com.danielwaiguru.weatherapp.WeatherScreenDestination"
    override val title: Int
        get() = TODO("Not yet implemented")
}

fun NavGraphBuilder.weatherScreen() {
    composable(route = WeatherScreenDestination.route) {

    }
}