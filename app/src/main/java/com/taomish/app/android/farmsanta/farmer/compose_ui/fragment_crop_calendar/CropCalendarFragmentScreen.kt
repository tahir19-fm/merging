package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*
import com.taomish.app.android.farmsanta.farmer.utils.asString
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.toLocalDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CropCalendarFragmentScreen(
    viewModel: CropCalendarViewModel,
    goToAddCropCalendarFragment: () -> Unit,
    getCropCalendarsForCrop: () -> Unit,
    goToCroppingStageDetailsFragment: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val currentTaskPosition = remember { mutableStateOf(0F) }

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

        item {
            CropCalendarsRow(
                viewModel = viewModel,
                getCropCalendarsForCrop = getCropCalendarsForCrop
            )

            Date(
                viewModel.selectedCropCalendar.value?.startDate?.toLocalDateTime()
                    ?.asString("dd-MM-yyyy") ?: "dd-mm-yyyy"
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

        itemsIndexed(viewModel.cropStages.value) { index, stage ->
            CroppingStage(
                stage = stage,
                getStageName = { viewModel.stagesMap[stage.stageName]?.name ?: "-" },
                currentTaskPosition = currentTaskPosition,
                onWeekClicked = { i ->
                    viewModel.selectedStage.postValue(stage)
                    viewModel.selectedStageIndex = index
                    viewModel.selectedWeekIndex.postValue(i)
                    goToCroppingStageDetailsFragment()
                }
            )
            DottedDivider(color = Color.Citron, padding = spacing.medium)
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



    if (viewModel.cropStages.value.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyList(text = str(id = R.string.empty_crop_calendar_msg))
        }
    }
}