package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.HorizontalSlideAnimation
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calender.components.AllCropModalSheetContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*
import com.taomish.app.android.farmsanta.farmer.fragments.CropCalendarFragment
import com.taomish.app.android.farmsanta.farmer.utils.asString
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.toLocalDateTime
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction3

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun CropCalendarFragmentScreen(
    viewModel: CropCalendarViewModel,
    goToAddCropCalendarFragment: () -> Unit,
    initData: () -> Unit,
    getCropCalendarsForCrop: () -> Unit,
    goToCroppingStageDetailsFragment: () -> Unit,
    updateCropCalendar: KFunction3<String, Int, Int, Unit>
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val sheetShape = LocalShapes.current.topRoundedMediumShape
    val scope = rememberCoroutineScope()
    val currentTaskPosition = remember { mutableStateOf(0F) }
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val startDate = viewModel.selectedCropCalendar.value?.startDate?.toLocalDateTime()
    val cropId = viewModel.selectedCropCalendar.value?.cropId ?: ""
    val stageId = viewModel.selectedCropCalendar.value?.stageId ?: 0
    val id = viewModel.selectedCropCalendar.value?.id ?: 0
    val formattedDate = startDate?.asString("dd-MM-yyyy")
    val dateParts = formattedDate?.split("-")
    val day = dateParts?.getOrNull(0) ?: "dd"
    val month = dateParts?.getOrNull(1) ?: "MM"
    val year = dateParts?.getOrNull(2) ?: "yyyy"

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            AllCropModalSheetContent(
                viewModel = viewModel,
                updateCropCalendar = updateCropCalendar,
                onCloseClicked = {
                    scope.launch { sheetState.hide() }
                },
                cropId = cropId,
                stageId = stageId,
                id = id,
                dd = day,
                mm = month,
                yyyy = year)
        },
        sheetShape = sheetShape,
        scrimColor = Color.Cameron.copy(alpha = .2f),
        sheetBackgroundColor = Color.White
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            stickyHeader {
                CommonTopBar(
                    title = stringResource(id = R.string.crop_calendar),
                    activity = context as AppCompatActivity,
                    backgroundColor = Color.Cameron,
                    tintColor = Color.White,
                    addClick = { goToAddCropCalendarFragment() }
                )
            }
            if (viewModel.cropStages.value.isNotEmpty() && !viewModel.userCrops.isEmpty()) {
                item {
                    CropCalendarsRow(
                        viewModel = viewModel,
                        getCropCalendarsForCrop = getCropCalendarsForCrop,
                        initData = initData,
                        goToAddCropCalendarFragment = goToAddCropCalendarFragment
                    )
                    HorizontalSlideAnimation {
                        Box(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_crop_calendar_new),
                                    contentDescription = "Image",
                                    modifier = Modifier
                                        .size(23.dp)
                                        .padding(top = 3.dp)
                                )
                                Text(
                                    text = "Sowing Date",
                                    modifier = Modifier.padding(start = 5.dp, top = 3.dp)
                                )
                            }
                        }
                        Date(
                            formattedDate ?: "dd-mm-yyyy",
                            onEditClicked = {
                                scope.launch { sheetState.show() }
                            }
                        )
                        /*viewModel.selectedStage.value?.let {
                CroppingStage(
                    stage = it,
                    getStageName = { viewModel.stagesMap[it.stageName]?.name ?: "-" },
                    currentTaskPosition = currentTaskPosition,
                    onWeekClicked = { i ->
                        viewModel.selectedStage.postValue(it)
                        viewModel.selectedWeekIndex.postValue(i)
                        goToCroppingStageDetailsFragment()
                    }
                )
                DottedDivider(color = Color.Citron, padding = spacing.medium)
            }*/
                    }
                }

                itemsIndexed(viewModel.cropStages.value) { index, stage ->
                    HorizontalSlideAnimation {
                        CroppingStage(
                            stage = stage,
                            getStageName = { viewModel.stagesMap[stage.stageName]?.name ?: "-" },
                            currentTaskPosition = currentTaskPosition,
                            onWeekClicked = { i ->
                                CropCalendarFragment.GlobalVariables.cropStageId =
                                    stage.weeks.get(i).weekInfo.toString()
                                viewModel.selectedStage.postValue(stage)
                                viewModel.selectedStageIndex = index
                                viewModel.selectedWeekIndex.postValue(i)
                                goToCroppingStageDetailsFragment()
                            }
                        )
                        DottedDivider(color = Color.Citron, padding = spacing.medium)
                    }
                }
            }
        }
    }


    /*viewModel.cropStages.value.forEach { stage ->

        StageBanner(
            bannerId = stage.stageStatus,
            stageName = stage.stageName,
            stageWeek = stage.stageWeek,
            startEndDate = stage.startEndDate,
            isCurrentTask = stage.isCurrentTask
        )


        stage.weeks.forEachIndexed { index, week ->
            Week(
                modifier = Modifier.onGloballyPositioned {
                    if (week.isCurrentTask && !isScrolled) {
                        scope.launch {
                            state.animateScrollTo(
                                it.positionInParent().y.toInt(),
                                animationSpec = SpringSpec(stiffness = Spring.StiffnessLow)
                            )
                        }
                        isScrolled = true
                    }
                },
                week = week,
                backgroundColor = colors[index % (colors.size - 1)]
            ) {
                viewModel.selectedStage.postValue(stage)
                viewModel.selectedStageIndex = index
                viewModel.selectedWeekIndex.postValue(index)
                goToCroppingStageDetailsFragment()
            }
        }
    }*/


    if (viewModel.cropStages.value.isEmpty() || viewModel.userCrops.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyList(text = str(id = R.string.empty_crop_calendar_msg))
        }
    }
}