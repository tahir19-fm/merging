package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components.FarmerPager
import com.taomish.app.android.farmsanta.farmer.utils.postValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PopDetailViewFragmentScreen(
    viewModel: POPViewModel,
    onSectionItemClicked: (IPMType) -> Unit,
    goToZoomImage: (String) -> Unit,
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(viewModel.selected.value)
    val scope = rememberCoroutineScope()
    val tabs = listOf(
        str(id = R.string.climate) to R.drawable.climate,
        str(id = R.string.cultivars) to R.drawable.cultivars,
        str(id = R.string.seed_treatment) to R.drawable.seed_treatment,
        str(id = R.string.cropping_process) to R.drawable.cropping_process,
        str(id = R.string.ipm) to R.drawable.ipm,
        str(id = R.string.nutrition) to R.drawable.nutrition,
        str(id = R.string.harvest) to R.drawable.ic_harvest,
    )
    viewModel.selected.postValue(pagerState.currentPage)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        CommonTopBar(
            title = stringResource(id = R.string.back),
            activity = context as AppCompatActivity,
            isAddRequired = false,
            addClick = {}
        )

        ScrollableTabs(
            modifier = Modifier.fillMaxWidth(),
            selected = viewModel.selected,
            tabs = tabs,
            state = pagerState,
            scope = scope,
            textStyle = MaterialTheme.typography.caption
        )

        FarmerPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            tabs = { tabs.size }
        ) { page ->
            when (page) {
                0 -> ClimateTabContent(viewModel = viewModel)
                1 -> CultivarsTabContent(
                    cultivars = viewModel.popDetails.value?.cultivars.orEmpty(),
                    onZoomImage = goToZoomImage
                )
                2 -> SeedTreatmentTabContent(
                    seedTreatments = viewModel.popDetails.value?.seedTreatments.orEmpty(),
                    onZoomImage = goToZoomImage
                )
                3 -> CroppingProcessTabContent(
                    cropName = viewModel.crops[viewModel.pop.value?.crop]?.cropName ?: "N/A",
                    croppingProcessDto = viewModel.popDetails.value?.croppingProcessDto,
                    states = viewModel.rowStates
                )
                4 -> IPMTabContent(
                    viewModel = viewModel,
                    onSectionItemClicked = onSectionItemClicked
                )
                5 -> NutritionTabContent(viewModel = viewModel, onZoomImage = goToZoomImage)
                6 -> HarvestTabContent(
                    pop = viewModel.pop.value,
                    harvesting = viewModel.popDetails.value?.harvestingDescription,
                    postHarvesting = viewModel.popDetails.value?.postHarvestingDescription,
                    onZoomImage = goToZoomImage
                )
            }
        }
    }

}