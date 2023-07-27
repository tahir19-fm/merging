package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.IconWithText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather.components.DailyTipsCard
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather.components.ForecastCard
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather.components.WEATHER_TODAY
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather.components.WEATHER_WEEK
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather.components.WeatherPeriodTags
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather_forecast.WeatherForecastFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.GraniteGray
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import com.taomish.app.android.farmsanta.farmer.utils.getImageForWeather


@Composable
fun WeatherFragmentScreen(homeViewModel: HomeViewModel, goToWeatherForecast: () -> Unit) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (homeViewModel.weatherSelectedTag != WEATHER_WEEK) {
            RemoteImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(if (homeViewModel.weatherSelectedTag == WEATHER_TODAY) .75f else .60f),
                imageLink = "",
                error = R.drawable.ic_day_weather_background,
                contentScale = ContentScale.FillBounds
            )
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CommonTopBar(
                activity = context as AppCompatActivity,
                title = str(id = R.string.weather_forecast),
                isAddRequired = false,
                backgroundColor = Color.Transparent,
                tintColor = if (homeViewModel.weatherSelectedTag != WEATHER_WEEK) Color.White else Color.GraniteGray,
                addClick = {}
            )
            WeatherPeriodTags(
                homeViewModel = homeViewModel
            )
            if (homeViewModel.weatherSelectedTag != WEATHER_WEEK) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
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

                                Text(
                                    text = homeViewModel.weatherData?.weatherDetails?.location?.name
                                        ?.plus(", ")
                                        ?.plus(
                                            homeViewModel.weatherData?.weatherDetails?.location?.country
                                        ) ?: "",
                                    style = MaterialTheme.typography.caption,
                                    color = Color.White
                                )
                            }

                            Text(
                                text = str(
                                    id = if (homeViewModel.weatherSelectedTag == WEATHER_TODAY)
                                        R.string.today else R.string.tomorrow
                                ),
                                style = MaterialTheme.typography.caption,
                                color = Color.White
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = spacing.small, horizontal = spacing.large),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = getImageForWeather(homeViewModel.weatherData?.weatherDetails)),
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(88.dp)
                                )

                                Text(
                                    text = homeViewModel.getWeatherDescription(),
                                    color = Color.White,
                                    style = MaterialTheme.typography.overline,
                                    textAlign = TextAlign.Justify
                                )
                            }

                            Text(
                                text = "${homeViewModel.getTemperature()}" +
                                        stringResource(id = R.string.celsius),
                                color = Color.White,
                                style = MaterialTheme.typography.h3
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(spacing.small),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconWithText(
                                iconId = R.drawable.ic_wind_speed,
                                iconSize = 24.dp,
                                text = "${homeViewModel.getWindSpeed()} Km/h"
                            )

                            IconWithText(
                                iconId = R.drawable.ic_humidity,
                                iconSize = 24.dp,
                                text = homeViewModel.getHumidity().toString().plus("%")
                            )

                            IconWithText(
                                iconId = R.drawable.ic_compass,
                                iconSize = 24.dp,
                                text = homeViewModel.getWindDirection()
                            )
                        }

                        if (homeViewModel.weatherSelectedTag == WEATHER_TODAY) {

                            Text(
                                text = str(id = R.string.today_s_tip),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier
                                    .padding(spacing.small)
                                    .fillMaxWidth()
                            )

                            DailyTipsCard(homeViewModel)

                            Spacer(modifier = Modifier.height(spacing.large))
                        }

                        ForecastCard(homeViewModel = homeViewModel)
                    }
                }
            } else {
                WeatherForecastFragmentScreen(homeViewModel = homeViewModel)
            }
        }
    }

}


