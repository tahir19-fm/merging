package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel


@Composable
fun DailyTipsCard(
    homeViewModel: HomeViewModel
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    Column(
        verticalArrangement = Arrangement.spacedBy(space = spacing.small, alignment = Alignment.CenterVertically)
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = spacing.small)
                .fillMaxWidth()
                .background(color = Color.White, shape = shape)
        ) {
            Text(
                text = homeViewModel.weatherData?.title ?: "",
                style = MaterialTheme.typography.subtitle1,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = spacing.small, vertical = spacing.small)
            )

            Text(
                text = homeViewModel.weatherData?.message ?: "",
                color = Color.LightGray,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(start = spacing.small, end = spacing.small, bottom = spacing.medium)
            )

        }

        Column(
            modifier = Modifier
                .padding(horizontal = spacing.small)
                .fillMaxWidth()
                .background(color = Color.White, shape = shape),
        ) {
            if (homeViewModel.weatherData?.harvesting.isNullOrEmpty().not()) {
                homeViewModel.weatherData?.harvesting?.let {
                    TipDetails(
                        title = str(id = R.string.harvesting),
                        message = homeViewModel.weatherData?.harvesting
                    )
                }
            }

            if (homeViewModel.weatherData?.irrigation.isNullOrEmpty().not()) {
                homeViewModel.weatherData?.irrigation?.let {
                    TipDetails(
                        title = str(id = R.string.irrigation),
                        message = it
                    )
                }
            }

            if (homeViewModel.weatherData?.weeding.isNullOrEmpty().not()) {
                homeViewModel.weatherData?.weeding?.let {
                    TipDetails(
                        title = str(id = R.string.weeding),
                        message = it
                    )
                }
            }

            if (homeViewModel.weatherData?.pesticideApplication.isNullOrEmpty().not()) {
                homeViewModel.weatherData?.pesticideApplication?.let {
                    TipDetails(
                        title = str(id = R.string.fertilizer_application),
                        message = it
                    )
                }
            }
        }
    }

}

@Composable
fun TipDetails(
    title: String? = null,
    message: String? = null
) {

    val spacing = LocalSpacing.current

    Column {
        Text(
            text = title ?: "",
            style = MaterialTheme.typography.subtitle1,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = spacing.small, vertical = spacing.small)
        )

        Text(
            text = message ?: "",
            color = Color.LightGray,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(start = spacing.small, end = spacing.small, bottom = spacing.small)
        )

    }
}