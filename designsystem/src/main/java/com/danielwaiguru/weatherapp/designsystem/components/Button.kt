package com.danielwaiguru.weatherapp.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.danielwaiguru.weatherapp.designsystem.previews.DevicePreviews
import com.danielwaiguru.weatherapp.designsystem.theme.WeatherAppTheme

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    onClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(
        top = 15.dp,
        bottom = 15.dp
    )
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .testTag("primary_button"),
        shape = shape,
        contentPadding = contentPadding
    ) {
        Text(
            text = text
        )
    }
}

@DevicePreviews
@Composable
private fun PrimaryButtonPreview() {
    WeatherAppTheme {
        PrimaryButton(
            text = "Click",
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}
