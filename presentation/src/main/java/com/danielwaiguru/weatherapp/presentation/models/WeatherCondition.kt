package com.danielwaiguru.weatherapp.presentation.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.danielwaiguru.weatherapp.designsystem.theme.cloudyColor
import com.danielwaiguru.weatherapp.designsystem.theme.rainyColor
import com.danielwaiguru.weatherapp.designsystem.theme.sunnyColor
import com.danielwaiguru.weatherapp.presentation.R

enum class WeatherCondition(
    @StringRes val nameId: Int,
    @DrawableRes val backgroundId: Int,
    @DrawableRes val iconId: Int,
    val backgroundColor: Color
) {
    RAINY(
        nameId = R.string.rainy,
        backgroundId = R.drawable.sea_rainy,
        iconId = R.drawable.rain,
        backgroundColor = rainyColor
    ),
    CLOUDY(
        nameId = R.string.cloudy,
        backgroundId = R.drawable.sea_cloudy,
        iconId = R.drawable.partlysunny,
        backgroundColor = cloudyColor
    ),
    SUNNY(
        nameId = R.string.sunny,
        backgroundId = R.drawable.sea_sunnypng,
        iconId = R.drawable.clear,
        backgroundColor = sunnyColor
    )
}