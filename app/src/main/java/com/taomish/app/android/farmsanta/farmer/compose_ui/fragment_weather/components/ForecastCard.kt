package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.WeatherTemperaturePeriod
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import com.taomish.app.android.farmsanta.farmer.utils.getImageForWeather


@Composable
fun ForecastCard(homeViewModel: HomeViewModel) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .background(Color.RiceFlower, shape = shape)
    ) {
        Text(
            text = str(id = R.string.hourly_forecast),
            color = Color.Cameron,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(spacing.small)
        )
        val filterDate = DateUtil.getDay(
            "dd/MM/yyyy",
             if (homeViewModel.weatherSelectedTag == WEATHER_TOMORROW) 1 else 0
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(homeViewModel.weatherData?.weatherDetails?.hourly?.filter {
                filterDate ==
                        DateUtil.getDate(it.dt?.times(1000L) ?: 0L, "dd/MM/yyyy")
            }.orEmpty()) {
                WeatherTemperaturePeriod(
                    periodText = DateUtil().getHourMinuteFormat(it.dt?.times(1000L) ?: 0L, "hh aa"),
                    periodTextColor = Color.Black,
                    periodTextStyle = MaterialTheme.typography.overline,
                    weatherIconId = getImageForWeather(it.weather?.firstOrNull()?.icon),
                    temperatureText = it.temp?.toString() ?: "",
                    temperatureTextColor = Color.Cameron,
                    temperatureTextStyle = MaterialTheme.typography.caption
                )
            }
        }

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_weather_forecast_graph_line_green),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
    }
}