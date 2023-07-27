package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FertilizerCalculatorViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components.FilterChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components.FarmerPager
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components.Tabs
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SelectCropsPage(
    viewModel: FertilizerCalculatorViewModel,
) {
    val spacing = LocalSpacing.current
    val state = rememberPagerState()
    val scope = rememberCoroutineScope()
    val fruitCrops = viewModel.fertilizerFruitDetails
    viewModel.selectedCropTab.postValue(state.currentPage)
    LaunchedEffect(true) {
        scope.launch {
            state.animateScrollToPage(viewModel.selectedCropTab.value)
        }
    }


    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = str(id = R.string.select_crops),
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.clickable { }
            )
        }

        if (viewModel.selectedCrop.value != null) {
            FilterChip(
                item = viewModel.selectedCrop.value!!,
                getText = { viewModel.selectedCrop.value?.cropName ?: "" },
                textStyle = MaterialTheme.typography.caption,
                backgroundColor = Color.Limeade,
                onDelete = { viewModel.selectedCrop.postValue(null) }
            )
        } else if (viewModel.selectedFruitCrop.value != null) {
            FilterChip(
                item = viewModel.selectedFruitCrop.value!!,
                getText = { viewModel.selectedFruitCrop.value?.cropName ?: "" },
                textStyle = MaterialTheme.typography.caption,
                backgroundColor = Color.Limeade,
                onDelete = { viewModel.selectedFruitCrop.postValue(null) }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${str(id = R.string.all_crops)}: ",
                color = Color.Cameron,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth(.2f)
            )

            Tabs(
                modifier = Modifier.fillMaxWidth(),
                selected = viewModel.selectedCropTab,
                tabs = viewModel.fertilizerCropTypes.map { it.label },
                state = state,
                scope = scope,
                textStyle = MaterialTheme.typography.body2,
                selectedTabColor = Color.Limeade
            )
        }

        FarmerPager(
            state = state,
            tabs = { viewModel.fertilizerCropTypes.size }
        ) { page ->
            if (page != 3) {
                FertilizerCropSelection(
                    crops = viewModel.allFertilizerCrops.value[page.plus(1)]
                        ?: viewModel.allFertilizerCrops.value[0].orEmpty(),
                    getCropName = { it.cropName }
                ) {
                    viewModel.selectedFruitCrop.postValue(null)
                    viewModel.selectedCrop.postValue(it)
                    viewModel.fruitCropNameSelected.value = ""
                    viewModel.selectedCropType.value = it.fertilizerType.toIntOrNull()
                    viewModel.selectedCropName = it.cropName
                }
            } else {
                FertilizerCropSelection(
                    crops = fruitCrops,
                    getCropName = { it.cropName ?: "" }
                ) {
                    viewModel.selectedCrop.postValue(null)
                    viewModel.selectedFruitCrop.postValue(it)
                    viewModel.fruitCropNameSelected.value = it.cropName ?: ""
                    viewModel.selectedCropType.value = 4
                    viewModel.selectedCropName = it.cropName ?: ""
                }
            }
        }

    }
}