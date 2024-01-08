/*
 * MIT License
 *
 * Copyright (c) 2024 Daniel Waiguru
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.danielwaiguru.weatherapp.presentation.permissions.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.danielwaiguru.weatherapp.presentation.R
import com.danielwaiguru.weatherapp.presentation.navigation.AppNavigationDestination
import com.danielwaiguru.weatherapp.presentation.permissions.PermissionsRoute
import com.danielwaiguru.weatherapp.presentation.weather.navigation.WeatherScreenDestination

object PermissionsScreenDestination : AppNavigationDestination {
    override val route: String = "com.danielwaiguru.weatherapp.PermissionsScreen"
    override val destination: String = "com.danielwaiguru.weatherapp.PermissionsScreenDestination"
    override val title: Int = R.string.grant_permissions
}

fun NavGraphBuilder.permissionsScreen(navController: NavHostController) {
    composable(route = PermissionsScreenDestination.route) {
        PermissionsRoute(
            onNavigateToWeather = {
                navController.navigate(WeatherScreenDestination.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        )
    }
}