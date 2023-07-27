package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.NextChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.PreviousChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components.Ongoing
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail.components.CroppingPeriod
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail.components.StageBanner
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail.components.WeekDetailView
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail.components.WeekOperations
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Apple
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.fragments.CropCalendarFragment
import com.taomish.app.android.farmsanta.farmer.utils.isNotEmptyOrNull
import com.taomish.app.android.farmsanta.farmer.utils.lastPage
import com.taomish.app.android.farmsanta.farmer.utils.notNull
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarStageDetailsFragmentScreen(
    viewModel: CropCalendarViewModel,
    onBack: () -> Unit,
    goToFertilizerCalculator: () -> Unit
) {
    val spacing = LocalSpacing.current
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val weekno : Int = CropCalendarFragment.GlobalVariables.cropStageId.toInt()
    val selectedStage by viewModel.selectedStage
    var currenttasklistinfo = selectedStage?.weeks?.get(0)?.tasklist
    var selectedWeek by remember { mutableStateOf(selectedStage?.weeks?.getOrNull(0)) }
    val selectedDescription by remember { mutableStateOf(selectedStage?.weeks?.getOrNull(0)?.tasklist?.getOrNull(0)?.oprationDescription) }

    for (i in 0 until selectedStage?.weeks?.size!!) {
        if (selectedStage?.weeks?.get(i)?.weekInfo == weekno){
            selectedWeek = selectedStage?.weeks?.getOrNull(i)
            currenttasklistinfo = selectedStage?.weeks?.get(i)?.tasklist
            currenttasklistinfo.isNotEmptyOrNull { viewModel.selectedOperationIndex.postValue(0) }
            if(currenttasklistinfo?.isEmpty() == true){
                viewModel.selectedOperationIndex.postValue(-1)
            }
        }
    }
//    val onPageChange: (Int) -> Unit = { page ->
//        if (page in 0..pagerState.lastPage) {
//            selectedTasklist = currenttasklistinfo
//            selectedDescription = currenttasklistinfo?.get(page)?.oprationDescription
//            scope.launch { pagerState.animateScrollToPage(page) }
//        }
//    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(165.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .fillMaxWidth()
                                .height(156.dp)
                                .padding(bottom = spacing.large)
                                .background(Color.Apple),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CroppingPeriod(
                                weeksRange = selectedStage?.stageWeek,
                                dateRange = selectedStage?.startEndDate,
                                onBack = onBack
                            )
                            StageBanner(
                                viewModel = viewModel,
                                taskList = currenttasklistinfo,
                                onBack = onBack
                            )
                        }

//                        selectedWeek?.tasklist?.let {
//                            WeekOperations(tasks = it)
//                        }

                        if (selectedStage?.isCurrentTask == true) {
                            Ongoing()
                        }

                    }

                    HorizontalPager(
                        modifier = Modifier
                            .padding(bottom = 30.dp)
                            .fillMaxWidth(),
                        state = pagerState,
                        count = selectedStage?.weeks?.size ?: 0,
                        userScrollEnabled = false,
                        verticalAlignment = Alignment.Top
                    ) {
                        if (selectedWeek != null) {
                            WeekDetailView(
                                week = selectedWeek!!,
                                viewModel = viewModel,
                                SelectedDescription = selectedDescription,
                                getStageName = { viewModel.stagesMap[selectedWeek!!.stageName]?.name.notNull() },
                                startEndDate = selectedStage?.startEndDate,
                                goToFertilizerCalculator = goToFertilizerCalculator
                            )
                        } else {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = str(id = R.string.nothing_to_show),
                                    color = Color.Apple,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
//                Row(
//                    modifier = Modifier
//                        .align(Alignment.BottomCenter)
//                        .padding(spacing.small)
//                        .fillMaxWidth()
//                        .background(Color.Transparent),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    PreviousChip(
//                        backgroundColor = Color.Apple,
//                        enabled = pagerState.currentPage != 0
//                    ) {
//                        onPageChange(pagerState.currentPage - 1)
//                    }
//                    NextChip(
//                        backgroundColor = Color.Apple,
//                        enabled = pagerState.currentPage != pagerState.lastPage
//                    ) {
//                        onPageChange(pagerState.currentPage + 1)
//                    }
//                }
            }
        }
    }
}