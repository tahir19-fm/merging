package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farm_location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farm_location.components.AddLandLocationForm
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farm_location.components.TrackFarmLocationPage
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view.components.ImmutableList
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.toLatLng
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddFarmLocationFragmentScreen(
    viewModel: SignUpAndEditProfileViewModel,
    onTrackLocationClicked: () -> Unit,
    onAddFarmClicked: () -> Unit
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val onPageChange: (Int) -> Unit = { scope.launch { pagerState.animateScrollToPage(it) } }
    LaunchedEffect(key1 = Unit) {
        if (viewModel.isEditingFarm || viewModel.coordinates.value.isNotEmpty()) {
            onPageChange(1)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.RiceFlower)
        ) {
            RemoteImage(
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(104.dp),
                imageLink = "",
                error = R.mipmap.ic_sign_up_location_field_foreground
            )
        }


        HorizontalPager(
            state = pagerState,
            count = 2,
            userScrollEnabled = false
        ) { page ->
            if (page == 0) {
                TrackFarmLocationPage(
                    onTrackLocationClicked = onTrackLocationClicked,
                    onPageChange = onPageChange
                )
            } else {
                AddLandLocationForm(
                    viewModel = viewModel,
                    onPageChange = onPageChange,
                    onAddFarmClicked = onAddFarmClicked,
                    onEditLocationClicked = {
                        if (viewModel.selectedLandIndex > -1 && viewModel.selectedLandIndex < viewModel.lands.size) {
                            viewModel.coordinates.postValue(
                                ImmutableList(viewModel.lands[viewModel.selectedLandIndex]
                                    .coordinates?.map { it.toLatLng() }.orEmpty())
                            )
                            viewModel.lands[viewModel.selectedLandIndex].coordinates = emptyList()
                            onTrackLocationClicked()
                        }
                    }
                )
            }
        }
    }
}