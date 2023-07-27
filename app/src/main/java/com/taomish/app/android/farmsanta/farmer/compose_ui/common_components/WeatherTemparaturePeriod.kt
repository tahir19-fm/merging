package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun WeatherTemperaturePeriod(
    periodText: String,
    periodTextColor: Color = Color.White,
    periodTextStyle: TextStyle = MaterialTheme.typography.overline,
    @DrawableRes weatherIconId: Int,
    iconSize: Dp = 24.dp,
    iconVerticalPadding: Dp = LocalSpacing.current.extraSmall,
    temperatureText: String,
    temperatureTextColor: Color = Color.White,
    temperatureTextStyle: TextStyle = MaterialTheme.typography.caption,
    showCelsius: Boolean = true
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.padding(spacing.extraSmall),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = periodText, color = periodTextColor, style = periodTextStyle)

        Icon(
            painter = painterResource(id = weatherIconId),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(vertical = iconVerticalPadding)
                .size(iconSize)
        )

        Text(
            text = "$temperatureText${if (showCelsius) stringResource(id = R.string.celsius) else ""}",
            color = temperatureTextColor,
            fontWeight = FontWeight.Bold,
            style = temperatureTextStyle
        )
    }
}