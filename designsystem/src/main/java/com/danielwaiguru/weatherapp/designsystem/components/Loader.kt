package com.danielwaiguru.weatherapp.designsystem.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.danielwaiguru.weatherapp.designsystem.testtags.TestTags

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    stockWidth: Dp = 2.dp,
    color: Color = MaterialTheme.colorScheme.primary
) {
    CircularProgressIndicator(
        modifier = modifier
            .testTag(TestTags.ProgressIndicator),
        color = color,
        strokeWidth = stockWidth
    )
}
