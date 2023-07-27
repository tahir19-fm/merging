package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.IconWithText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.WeatherTemperaturePeriod
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import com.taomish.app.android.farmsanta.farmer.utils.getImageForWeather
import com.taomish.app.android.farmsanta.farmer.utils.getWindDirection


@Composable
fun WeatherCard(
    homeViewModel: HomeViewModel,
    onNavigationItemClicked: (Screen) -> Unit
) {
    val spacing = LocalSpacing.current
    val cardShape = LocalShapes.current.mediumShape
    val smallShape = LocalShapes.current.smallShape
    val brush = Brush.verticalGradient(
        colors = listOf(
            Color.DodgerBlue,
            Color.PitonBlue,
            Color.Malibu
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
            .background(brush = brush, shape = cardShape)
            .clip(cardShape)
            .clickable(
                indication = rememberRipple(
                    bounded = true,
                    color = Color.DodgerBlue.copy(alpha = .2f)
                ),
                interactionSource = MutableInteractionSource(),
                onClick = { onNavigationItemClicked(Screen.Weather) }
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing.small, horizontal = spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {

                Icon(
                    painter = painterResource(id = R.drawable.ic_location_grey),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )

                // City & Country
                Text(
                    text = homeViewModel.weatherData?.weatherDetails?.location?.name?.plus(", ")?.plus(
                        homeViewModel.weatherData?.weatherDetails?.location?.country
                    ) ?: "",
                    style = MaterialTheme.typography.caption,
                    color = Color.White
                )
            }

            // Date
            Text(
                text = DateUtil.getCurrentDay("dd MMM, yyyy"),
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = getImageForWeather(homeViewModel.weatherData?.weatherDetails)),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(48.dp)
                )

                Text(
                    text = homeViewModel.weatherData?.weatherDetails?.current?.weather?.firstOrNull()?.description
                        ?: "",
                    color = Color.White,
                    style = MaterialTheme.typography.overline,
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${homeViewModel.weatherData?.weatherDetails?.current?.temp ?: ""}${stringResource(id = R.string.celsius)}",
                    color = Color.White,
                    style = MaterialTheme.typography.h4
                )

                Text(
                    text = DateUtil.getCurrentDay("hh:mm aa"),
                    color = Color.White,
                    style = MaterialTheme.typography.caption
                )
            }

            Column(
                modifier = Modifier.wrapContentHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                IconWithText(
                    iconId = R.drawable.ic_wind_speed,
                    text = "${homeViewModel.weatherData?.weatherDetails?.current?.windSpeed ?: "-"} Km/h"
                )

                IconWithText(
                    iconId = R.drawable.ic_humidity,
                    text = homeViewModel.weatherData?.weatherDetails?.daily?.firstOrNull()?.humidity?.toString()
                        ?.plus("%") ?: ""
                )

                IconWithText(
                    iconId = R.drawable.ic_compass,
                    text = getWindDirection(
                        homeViewModel.weatherData?.weatherDetails?.daily?.firstOrNull()?.windDeg ?: -1
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth()
                .background(color = Color.DodgerBlue, shape = smallShape),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherTemperaturePeriod(
                periodText = str(id = R.string.morning),
                weatherIconId = R.drawable.ic_weather_sunny,
                temperatureText = homeViewModel.weatherData?.weatherDetails?.daily?.firstOrNull()?.feelsLike
                    ?.morn?.toString() ?: ""
            )

            WeatherTemperaturePeriod(
                periodText = str(id = R.string.afternoon),
                weatherIconId = R.drawable.ic_weather_rainy,
                temperatureText = homeViewModel.weatherData?.weatherDetails?.daily?.firstOrNull()?.feelsLike
                    ?.day?.toString() ?: ""
            )

            WeatherTemperaturePeriod(
                periodText = str(id = R.string.evening),
                weatherIconId = R.drawable.ic_weather_cloudy,
                temperatureText = homeViewModel.weatherData?.weatherDetails?.daily?.firstOrNull()?.feelsLike
                    ?.eve?.toString() ?: ""
            )

            WeatherTemperaturePeriod(
                periodText = str(id = R.string.night),
                weatherIconId = R.drawable.ic_weather_night_cloudy,
                temperatureText = homeViewModel.weatherData?.weatherDetails?.daily?.firstOrNull()?.feelsLike
                    ?.night?.toString() ?: ""
            )
        }

        if (homeViewModel.weatherData != null) {
            Text(
                text = str(id = R.string.view_more),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(spacing.medium)
                    .fillMaxWidth()
                    .clip(RectangleShape)
            )
        }
    }
}