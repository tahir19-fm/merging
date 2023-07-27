package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather_forecast.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.IconWithText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.DodgerBlue
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing

@Composable
fun ForecastPeriodItem(
    @DrawableRes iconId: Int,
    periodText: String,
    temperature: String,
    windSpeed: Int,
    humidity: Int
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    Row(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .background(color = Color.DodgerBlue.copy(alpha = .1f), shape = shape),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.padding(start = spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconId),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = periodText,
                color = Color.DodgerBlue,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = spacing.extraSmall),
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_temperature_normal),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = "$temperature${str(id = R.string.celsius)}",
                color = Color.DodgerBlue,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.padding(end = spacing.small),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            IconWithText(
                iconId = R.drawable.ic_wind_speed,
                tint = Color.DodgerBlue,
                text = "$windSpeed Km/h",
                textColor = Color.Black,
                textStyle = MaterialTheme.typography.caption,
            )

            IconWithText(
                iconId = R.drawable.ic_humidity,
                tint = Color.DodgerBlue,
                text = "$humidity%",
                textColor = Color.Black,
                textStyle = MaterialTheme.typography.caption,
            )
        }
    }
}