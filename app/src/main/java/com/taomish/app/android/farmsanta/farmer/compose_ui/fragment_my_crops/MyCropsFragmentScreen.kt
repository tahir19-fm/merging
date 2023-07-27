package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_crops

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.MyCropsViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_crops.components.*
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun MyCropsFragmentScreen(
    myCropsViewModel: MyCropsViewModel,
    homeViewModel: HomeViewModel,
    onSectionViewMoreClicked: (Screen) -> Unit,
    goToViewAdvisoryFragment: (CropAdvisory) -> Unit,
    goToQueryDetailsFragment: (FarmScouting, Int) -> Unit,
    goToViewPop: (PopDto) -> Unit,
    goToDiseaseDetails: () -> Unit
) {
    val context = LocalContext.current
    
    Column(modifier = Modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CommonTopBar(
                activity = context as AppCompatActivity,
                isAddRequired = false,
                title = str(id = R.string.my_crops),
                addClick = {}
            )
            
            MyCropsRow(
                crops = homeViewModel.myCrops,
                selectedCrop = myCropsViewModel.selectedCrop,
                onSelect = { crop ->
                    myCropsViewModel.selectedCrop.postValue(crop)
                    homeViewModel.cropPops.postValue(
                        homeViewModel.userPops.value?.filter {
                            it.crop == myCropsViewModel.selectedCrop.value?.uuid
                        }.orEmpty()
                    )
                }
            )

            MyCropsDiseasesInCrops(
                homeViewModel = homeViewModel,
                selectedCrop = myCropsViewModel.selectedCrop,
                onDiseaseClicked = goToDiseaseDetails,
                onViewMoreClicked = { onSectionViewMoreClicked(Screen.Diseases) }
            )

            MyCropAdvisories(
                homeViewModel = homeViewModel,
                selectedCrop = myCropsViewModel.selectedCrop,
                onSectionViewMoreClicked = onSectionViewMoreClicked,
                goToViewAdvisoryFragment = goToViewAdvisoryFragment
            )

            MyCropsPops(
                homeViewModel = homeViewModel,
                selectedCrop = myCropsViewModel.selectedCrop,
                onSectionViewMoreClicked = onSectionViewMoreClicked,
                goToViewPop = goToViewPop
            )

            MyCropsQueries(
                homeViewModel = homeViewModel,
                selectedCrop = myCropsViewModel.selectedCrop,
                onSectionViewMoreClicked = onSectionViewMoreClicked,
                goToQueryDetailsFragment = goToQueryDetailsFragment
            )

           /* MyCropMarketAnalysis(
                chartData = myCropsViewModel.chartData,
                onSectionViewMoreClicked = onSectionViewMoreClicked
            )*/
        }
    }
}