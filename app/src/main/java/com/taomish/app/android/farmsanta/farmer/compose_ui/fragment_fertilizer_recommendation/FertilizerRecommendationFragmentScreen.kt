package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_recommendation

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FertilizerCalculatorViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_recommendation.components.FertilizerRecommendationBottomSheet
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_recommendation.components.FertilizerTable
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_recommendation.components.UreaFertilizerSchedule
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FertilizerRecommendationFragmentScreen(viewModel: FertilizerCalculatorViewModel) {
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val sheetShape = LocalShapes.current.topRoundedMediumShape
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    BackHandler(sheetState.isVisible) { scope.launch { sheetState.hide() } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Limeade)
    ) {

        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                FertilizerRecommendationBottomSheet(
                    selectedCrop = viewModel.allCropsList.find { it.uuid == viewModel.fertilizerGeneratedReport?.cropId }?.cropName ?: "",
                    onCloseClicked = { scope.launch { sheetState.hide() } }
                )
            },
            sheetShape = sheetShape,
            scrimColor = Color.Cameron.copy(alpha = .2f),
            sheetBackgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = spacing.medium)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(spacing.medium)
            ) {
                CommonTopBar(
                    activity = context as AppCompatActivity,
                    title = str(id = R.string.fertilizer_recommendation),
                    isAddRequired = false,
                    backgroundColor = Color.Limeade,
                    tintColor = Color.White,
                    addClick = {}
                )

                FertilizerTable(viewModel = viewModel)

                UreaFertilizerSchedule(viewModel = viewModel)

//                FertilizerApplicationGuideline()
            }
        }

//        if (viewModel.isSavedFertilizer.value.not()) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.BottomCenter)
//            ) {
//                Button(
//                    modifier = Modifier.fillMaxWidth(.5f),
//                    shape = RectangleShape,
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
//                    onClick = onClickCancel
//                ) {
//                    Text(
//                        text = str(id = R.string.cancel),
//                        style = MaterialTheme.typography.body2,
//                        color = Color.Gray,
//                        modifier = Modifier.padding(vertical = spacing.medium)
//                    )
//                }
//
//                Button(
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RectangleShape,
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Limeade),
//                    onClick = {
//                        if (sheetState.isVisible) {
//                            viewModel.clearValues()
//                            onClickSave()
//                        } else {
//                            scope.launch { sheetState.show() }
//                        }
//                    }
//                ) {
//                    Text(
//                        text = str(id = R.string.save),
//                        style = MaterialTheme.typography.body2,
//                        color = Color.White,
//                        modifier = Modifier.padding(vertical = spacing.medium)
//                    )
//                }
//            }
//        }
    }
}