package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SelectableChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Denim
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.DodgerBlue
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel

const val WEATHER_TODAY = 0
const val WEATHER_TOMORROW = 1
const val WEATHER_WEEK = 2

@Composable
fun WeatherPeriodTags(
    homeViewModel: HomeViewModel
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SelectableChip(
            text = str(id = R.string.today),
            isSelected = homeViewModel.weatherSelectedTag == WEATHER_TODAY,
            selectedBackgroundColor = Color.DodgerBlue,
            unselectedBackgroundColor = Color.Denim.copy(alpha = .2f),
            unselectedContentColor = Color.White,
            onClick = {
                homeViewModel.weatherSelectedTag = WEATHER_TODAY
            }
        )

        SelectableChip(
            text = str(id = R.string.tomorrow),
            isSelected = homeViewModel.weatherSelectedTag == WEATHER_TOMORROW,
            selectedBackgroundColor = Color.DodgerBlue,
            unselectedBackgroundColor = Color.Denim.copy(alpha = .2f),
            unselectedContentColor = Color.White,
            onClick = {
                homeViewModel.weatherSelectedTag = WEATHER_TOMORROW
            }
        )

        SelectableChip(
            text = str(id = R.string.seven_days),
            isSelected = homeViewModel.weatherSelectedTag == WEATHER_WEEK,
            selectedBackgroundColor = Color.DodgerBlue,
            unselectedBackgroundColor = Color.Denim.copy(alpha = .2f),
            unselectedContentColor = Color.White,
            onClick = {
                homeViewModel.weatherSelectedTag = WEATHER_WEEK
            }
        )
    }
}