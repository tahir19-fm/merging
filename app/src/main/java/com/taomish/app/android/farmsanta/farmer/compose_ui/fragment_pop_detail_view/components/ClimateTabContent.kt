package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun ClimateTabContent(viewModel: POPViewModel) {
    val spacing = LocalSpacing.current
    val climateDto = viewModel.popDetails.value?.climateDtos?.firstOrNull()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.large)
    ) {
        item {


            Spacer(modifier = Modifier.height(spacing.medium))

            WeatherIndicatorRow(climateDto)

            Spacer(modifier = Modifier.height(spacing.medium))


            Text(
                text = climateDto?.remarks?.replace("\n", "")?.trim() ?: "",
                color = Color.Black,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(spacing.small)
            )

        }
    }
}