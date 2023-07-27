package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FertilizerCalculatorViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.FertilizerGeneratedReport


@Composable
fun SavedFertilizerRecommendationPage(
    viewModel: FertilizerCalculatorViewModel,
    onPageChange: (Int) -> Unit,
    goToFertilizerRecommendation: (FertilizerGeneratedReport) -> Unit,
) {
    val spacing = LocalSpacing.current
    val isEmpty = false
    if (isEmpty) {
        EmptySavedFertilizer(
            modifier = Modifier.fillMaxSize(),
            onCalculateClicked = { onPageChange(Page.SOIL_HEALTH_CONFIRMATION) }
        )
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = str(id = R.string.saved_fertilizer_recommendations),
                modifier = Modifier.padding(spacing.small)
            )
            LazyVerticalGrid(
                modifier = Modifier.padding(vertical = spacing.medium),
                columns = GridCells.Fixed(2)
            ) {
                items(viewModel.fertilizerReportsSavedList) { report ->
                    val cropName = viewModel.allFertilizerCrops.value[report.cropType]
                        ?.find { it.cropId == report.cropId }?.cropName
                    SavedFertilizerRecommendationItem(
                        fertilizerGeneratedReport = report,
                        cropName = cropName
                    ) { goToFertilizerRecommendation(report) }
                }
            }
        }
    }
}