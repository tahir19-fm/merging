package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather_forecast

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather_forecast.components.ForecastPeriodItem
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import com.taomish.app.android.farmsanta.farmer.utils.getImageForWeather


@Composable
fun WeatherForecastFragmentScreen(homeViewModel: HomeViewModel) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        itemsIndexed(
            homeViewModel.weatherData?.weatherDetails?.daily?.drop(1).orEmpty()
        ) { index, it ->
            ForecastPeriodItem(
                iconId = getImageForWeather(it.weather?.firstOrNull()?.icon),
                periodText = if (index == 0)
                    stringResource(id = R.string.tomorrow)
                else
                    DateUtil.getDate(
                        it.dt?.times(1000L) ?: 0,
                        "dd/MM/yyyy"
                    ),
                temperature = "${it.temp.max}",
                windSpeed = it.windSpeed?.toInt() ?: 0,
                humidity = it.humidity ?: 0
            )
        }

    }

}