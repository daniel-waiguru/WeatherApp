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

package com.danielwaiguru.weatherapp.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danielwaiguru.weatherapp.designsystem.components.ProgressIndicator
import com.danielwaiguru.weatherapp.designsystem.previews.DevicePreviews
import com.danielwaiguru.weatherapp.designsystem.testtags.TestTags
import com.danielwaiguru.weatherapp.designsystem.theme.WeatherAppTheme
import com.danielwaiguru.weatherapp.designsystem.utils.Dimensions
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.models.WeatherForecast
import com.danielwaiguru.weatherapp.presentation.R
import com.danielwaiguru.weatherapp.presentation.models.WeatherCondition
import com.danielwaiguru.weatherapp.presentation.utils.DateUtils
import com.danielwaiguru.weatherapp.presentation.utils.getWeatherCondition

@Composable
fun WeatherRoute(
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    val uiState by viewModel.currentWeatherUIState.collectAsStateWithLifecycle()
    WeatherScreen(
        modifier =
            Modifier
                .fillMaxSize(),
        state = uiState,
        onRefresh = viewModel::onRefresh
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    state: WeatherScreenState,
    onRefresh: () -> Unit
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(state.errorMessage) {
        if (state.errorMessage != null) {
            snackbarHostState.showSnackbar(state.errorMessage)
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier.testTag(TestTags.SNACKBAR_TAG)
            )
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.statusBars)
    ) { paddingValues ->
        val pullToRefreshState = rememberPullToRefreshState()
        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            indicator = {
                Indicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .statusBarsPadding()
                        .testTag(TestTags.PULL_TO_REFRESH_INDICATOR_TAG),
                    isRefreshing = true,
                    state = pullToRefreshState
                )
            }
        ) {
            when {
                state.isLoadingWithNoData -> {
                    ProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                state.currentWeather != null -> WeatherView(
                    state = state,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun WeatherView(
    modifier: Modifier = Modifier,
    state: WeatherScreenState
) {
    state.currentWeather?.let { currentWeather ->
        val weatherCondition by remember(currentWeather.conditionId) {
            derivedStateOf { currentWeather.conditionId.getWeatherCondition() }
        }

        LazyColumn(
            modifier = modifier
                .background(weatherCondition.backgroundColor)
        ) {
            item {
                CurrentWeatherComponent(
                    weather = currentWeather,
                    weatherCondition = weatherCondition,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .fillParentMaxHeight(0.45f)
                            .testTag(TestTags.CURRENT_WEATHER_COMPONENT_TAG),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(weatherCondition.backgroundColor),
                ) {
                    TempItem(
                        temp = state.currentWeather.tempMin,
                        text = stringResource(id = R.string.min),
                        modifier =
                            Modifier
                                .padding(10.dp),
                    )
                    TempItem(
                        temp = state.currentWeather.temp,
                        text = stringResource(id = R.string.current),
                        modifier =
                            Modifier
                                .padding(10.dp),
                    )
                    TempItem(
                        temp = state.currentWeather.tempMax,
                        text = stringResource(id = R.string.max),
                        modifier =
                            Modifier
                                .padding(10.dp),
                    )
                }
            }
            item {
                HorizontalDivider(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            weatherForecastSection(state.forecasts)
        }
    }
}

private fun LazyListScope.weatherForecastSection(
    forecasts: List<WeatherForecast>,
) {
    items(forecasts, key = { it.id }) { forecast ->
        ForecastItem(
            forecast = forecast,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = Dimensions.PaddingSmall),
        )
    }
}

@Composable
private fun ForecastItem(
    modifier: Modifier = Modifier,
    forecast: WeatherForecast
) {
    val weatherCondition by remember(forecast.conditionId) {
        derivedStateOf { forecast.conditionId.getWeatherCondition() }
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = forecast.day,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f)
        )
        Image(
            painter = painterResource(id = weatherCondition.iconId),
            contentDescription = stringResource(id = weatherCondition.nameId),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(30.dp)
                .weight(1f)

        )
        Text(
            text = "${forecast.temp}°C",
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@DevicePreviews
@Composable
private fun ForecastItemPreview() {
    WeatherAppTheme {
        ForecastItem(
            forecast = WeatherForecast(
                id = 3406,
                date = 1444,
                temp = 6.7,
                day = "fabellas",
                conditionId = 600
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun CurrentWeatherComponent(
    modifier: Modifier = Modifier,
    weather: Weather,
    weatherCondition: WeatherCondition
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = weatherCondition.backgroundId),
            contentDescription = weather.description,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .offset(y = 3.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .padding(Dimensions.PaddingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${weather.temp}°C",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .testTag(TestTags.TEMP_TEXT_TAG)
            )
            Text(
                text = weatherCondition.name,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp
                ),
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = "Last Updated: ${DateUtils.toFormattedDate(weather.lastUpdateAt)}",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = Dimensions.PaddingLarge, start = Dimensions.PaddingMedium),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "${weather.city}, ${weather.country}",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = Dimensions.PaddingLarge),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun TempItem(
    temp: Double,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$temp°C",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@DevicePreviews
@Composable
private fun TempItemPreview() {
    WeatherAppTheme {
        TempItem(
            temp = 12.0,
            text = "min",
            modifier = Modifier
                .padding(10.dp)
        )
    }
}