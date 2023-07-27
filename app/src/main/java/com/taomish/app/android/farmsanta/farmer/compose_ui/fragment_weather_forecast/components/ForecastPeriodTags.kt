package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather_forecast.components

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
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun ForecastPeriodTags() {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SelectableChip(
            text = str(id = R.string.today),
            isSelected = true,
            selectedBackgroundColor = Color.Denim,
            unselectedBackgroundColor = Color.Denim.copy(alpha = .2f),
            onClick = {}
        )

        SelectableChip(
            text = str(id = R.string.tomorrow),
            isSelected = false,
            selectedBackgroundColor = Color.Denim,
            unselectedBackgroundColor = Color.Denim.copy(alpha = .2f),
            onClick = {}
        )

        SelectableChip(
            text = str(id = R.string.seven_days),
            isSelected = false,
            selectedBackgroundColor = Color.Denim,
            unselectedBackgroundColor = Color.Denim.copy(alpha = .2f),
            onClick = {}
        )
    }
}