package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.NextChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.PreviousChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FertilizerCalculatorViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.FertilizerGeneratedReport
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FertilizerCalculatorFragmentScreen(
    viewModel: FertilizerCalculatorViewModel,
    goToFertilizerRecommendation: () -> Unit,
    goToViewFertilizerRecommendation: (FertilizerGeneratedReport) -> Unit,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val onPageChange: (Int) -> Unit = { no ->
        focusManager.clearFocus()
        if (no in 0..5) {
            scope.launch { pagerState.animateScrollToPage(no) }
        }
    }
    BackHandler(pagerState.currentPage != Page.SAVED_FERTILIZER_RECOMMENDATION) {
        if (pagerState.currentPage == Page.SELECT_CROPS && !viewModel.hasReport.value) {
            onPageChange(pagerState.currentPage - 2)
        } else {
            onPageChange(pagerState.currentPage - 1)
        }
        viewModel.selectedCropTab.postValue(0)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (pagerState.currentPage == Page.SAVED_FERTILIZER_RECOMMENDATION) {
                FirstPageFarmerMascot { onPageChange(1) }
            } else {
                LargeFarmerMascot()
            }

            HorizontalPager(
                modifier = Modifier
                    .padding(spacing.small)
                    .fillMaxSize(),
                state = pagerState,
                count = 6,
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    Page.SAVED_FERTILIZER_RECOMMENDATION -> {
                        SavedFertilizerRecommendationPage(
                            viewModel = viewModel,
                            onPageChange = onPageChange,
                            goToFertilizerRecommendation = {
                                viewModel.isSavedFertilizer.postValue(true)
                                goToViewFertilizerRecommendation(it)
                            }
                        )
                    }
                    Page.SOIL_HEALTH_CONFIRMATION -> {
                        SoilHealthConfirmationPage(hasReport = viewModel.hasReport)
                    }
                    Page.SOIL_HEALTH_FORM -> {
                        SoilHealthFormPage(viewModel = viewModel, onDone = onPageChange)
                    }
                    Page.SELECT_CROPS -> SelectCropsPage(viewModel = viewModel)
                    Page.CROP_INPUTS -> {
                        CropInputsPage(viewModel = viewModel, onPageChange = onPageChange)
                    }

                    Page.FERTILIZER_SOURCE_SELECTION -> {
                        FertilizerSourceSelectionPage(viewModel = viewModel)
                    }
                }
            }
        }

        if (
            pagerState.currentPage != Page.SAVED_FERTILIZER_RECOMMENDATION &&
            !viewModel.isKeyboardOpened.value
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(spacing.small),
                horizontalArrangement = if (pagerState.currentPage == 1) Arrangement.End else Arrangement.SpaceBetween
            ) {

                if (pagerState.currentPage != Page.SOIL_HEALTH_CONFIRMATION) {
                    PreviousChip(
                        backgroundColor = Color.Limeade,
                        enabled = pagerState.currentPage != Page.SOIL_HEALTH_CONFIRMATION,
                        onClick = {
                            if (pagerState.currentPage == Page.SELECT_CROPS && !viewModel.hasReport.value) {
                                scope.launch { pagerState.scrollToPage(pagerState.currentPage - 2) }
                            } else {
                                onPageChange(pagerState.currentPage - 1)
                            }
                            if (viewModel.selectedCropTab.value == 3) {
                                viewModel.selectedCropTab.postValue(0)
                            }
                        }
                    )
                }

                NextChip(
                    textId = if (
                        pagerState.currentPage == Page.FERTILIZER_SOURCE_SELECTION ||
                        (pagerState.currentPage == Page.CROP_INPUTS && viewModel.isFruitCropSelected)
                    ) R.string.create else R.string.next,
                    backgroundColor = Color.Limeade,
                    enabled = true,
                    onClick = {
                        if (pagerState.currentPage == Page.CROP_INPUTS) {
                            if (viewModel.isFruitCropSelected) {
                                if (viewModel.cropArea.value.isEmpty() && viewModel.ageOfCropSelected.value.isEmpty()) {
                                    context.showToast(
                                        "${context.getString(R.string.please_enter_crop_area)} " +
                                                context.getString(R.string.or_age_of_crop)
                                    )
                                } else goToFertilizerRecommendation()
                            } else {
                                if (viewModel.cropArea.value.isEmpty()) {
                                    context.showToast(R.string.please_enter_crop_area)
                                } else {
                                    onPageChange(pagerState.currentPage + 1)
                                }
                            }
                        } else if (pagerState.currentPage == Page.SELECT_CROPS) {
                            if (viewModel.selectedCrop.value != null || viewModel.selectedFruitCrop.value != null) {
                                onPageChange(pagerState.currentPage + 1)
                            } else {
                                context.showToast(context.getString(R.string.please_select_crop))
                            }
                        } else if (pagerState.currentPage == Page.FERTILIZER_SOURCE_SELECTION) {
                            if (viewModel.validate()) {
                                viewModel.isSavedFertilizer.postValue(false)
                                goToFertilizerRecommendation()
                            } else {
                                context.showToast(R.string.please_select_all_fertilizers)
                            }
                        } else if (pagerState.currentPage == Page.SOIL_HEALTH_CONFIRMATION && !viewModel.hasReport.value) {
                            scope.launch { pagerState.scrollToPage(pagerState.currentPage + 2) }
                        } else {
                            onPageChange(pagerState.currentPage + 1)
                        }

                    }
                )
            }
        }
    }
}