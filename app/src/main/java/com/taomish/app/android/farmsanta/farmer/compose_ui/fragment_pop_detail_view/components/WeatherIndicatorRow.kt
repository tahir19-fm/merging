package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.WeatherTemperaturePeriod
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*
import com.taomish.app.android.farmsanta.farmer.models.api.pop.ClimateDto


@Composable
fun WeatherIndicatorRow(climateDto: ClimateDto?) {
    val spacing = LocalSpacing.current
    val smallShape = LocalShapes.current.smallShape
    LazyRow(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .background(color = Color.Cameron.copy(alpha = .2f), shape = smallShape),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            WeatherTemperaturePeriod(
                periodText = str(id = R.string.temperature),
                periodTextColor = Color.Sunshade,
                weatherIconId = R.drawable.ic_temperature,
                iconSize = 40.dp,
                iconVerticalPadding = spacing.small,
                temperatureText = "${climateDto?.growingTemperature?.min ?: ""}-${climateDto?.growingTemperature?.max ?: ""}",
                temperatureTextColor = Color.Sunshade,
                temperatureTextStyle = MaterialTheme.typography.overline
            )

            WeatherTemperaturePeriod(
                periodText = str(id = R.string.rainfall),
                periodTextColor = Color.Cameron,
                weatherIconId = R.drawable.ic_rainfall,
                iconSize = 40.dp,
                iconVerticalPadding = spacing.small,
                temperatureText = "${climateDto?.averageRain?.min ?: ""}-${climateDto?.averageRain?.max ?: ""} Cm",
                temperatureTextColor = Color.Cameron,
                temperatureTextStyle = MaterialTheme.typography.overline,
                showCelsius = false
            )

            WeatherTemperaturePeriod(
                periodText = str(id = R.string.sowing),
                periodTextColor = Color.OutrageousOrange,
                weatherIconId = R.drawable.ic_sowing,
                iconSize = 40.dp,
                iconVerticalPadding = spacing.small,
                temperatureText = "${climateDto?.sowingTemperature?.min ?: ""}-${climateDto?.sowingTemperature?.max ?: ""}",
                temperatureTextColor = Color.OutrageousOrange,
                temperatureTextStyle = MaterialTheme.typography.overline
            )

            WeatherTemperaturePeriod(
                periodText = str(id = R.string.harvesting),
                periodTextColor = Color.Thunderbird,
                weatherIconId = R.drawable.ic_harvesting_svg,
                iconSize = 40.dp,
                iconVerticalPadding = spacing.small,
                temperatureText = "${climateDto?.harvestTemperature?.min ?: ""}-${climateDto?.harvestTemperature?.max ?: ""}",
                temperatureTextColor = Color.Thunderbird,
                temperatureTextStyle = MaterialTheme.typography.overline
            )
        }

    }
}