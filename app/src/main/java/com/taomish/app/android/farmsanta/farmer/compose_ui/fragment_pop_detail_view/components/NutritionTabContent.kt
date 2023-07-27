package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SelectableChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components.Tabs
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.notNull
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun NutritionTabContent(viewModel: POPViewModel, onZoomImage: (String) -> Unit) {
    val spacing = LocalSpacing.current
    val state = rememberPagerState()
    val scope = rememberCoroutineScope()
    val selected = remember { mutableStateOf(0) }
    selected.postValue(state.currentPage)
    var selectedSection by remember { mutableStateOf(NutritionSection.NutritionDeficiency) }
    val getCropName: (String?) -> String = { viewModel.crops[it]?.cropName.notNull() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.large)
    ) {
        item {
            Row(modifier = Modifier.padding(spacing.small)) {
                SelectableChip(
                    text = str(id = R.string.nutrition_deficiency),
                    isSelected = selectedSection == NutritionSection.NutritionDeficiency
                ) { selectedSection = NutritionSection.NutritionDeficiency }

                SelectableChip(
                    text = str(id = R.string.standard_nutrition),
                    isSelected = selectedSection == NutritionSection.StandardNutrition
                ) { selectedSection = NutritionSection.StandardNutrition }
            }

            if (selectedSection == NutritionSection.NutritionDeficiency)
                Spacer(modifier = Modifier.height(spacing.medium))
        }

        if (selectedSection == NutritionSection.StandardNutrition) {
            items(viewModel.popDetails.value?.cropNutritions.orEmpty()) { cropNutrition ->
                NutritionItem(getCropName = getCropName, cropNutrition = cropNutrition)
            }
        } else {

            item {
                viewModel.popDetails.value?.deficiencyDto?.let { deficiencyDto ->
                    Tabs(
                        selected = selected,
                        tabs = deficiencyDto.map { it.nutrient ?: it.nutrientId ?: "" },
                        state = state,
                        scope = scope
                    )

                    HorizontalPager(
                        state = state,
                        count = deficiencyDto.size,
                        verticalAlignment = Alignment.Top
                    ) { page ->
                        DeficiencyItem(
                            deficiencyDto = deficiencyDto[page],
                            onZoomImage = onZoomImage
                        )
                    }
                }
            }
        }
    }
}