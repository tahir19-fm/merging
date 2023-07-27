package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Apple
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.Task
import com.taomish.app.android.farmsanta.farmer.utils.notNull
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun StageBanner(viewModel: CropCalendarViewModel, taskList: List<Task>?, onBack: () -> Unit) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = spacing.small)
            .wrapContentSize()
            .background(color = Color.White, shape = CircleShape),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(
            imageVector = Icons.Filled.ChevronLeft,
            contentDescription = null,
            tint = Color.Apple,
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    if ((viewModel.selectedOperationIndex.value - 1) >= 0) {
//                        val stage =
//                            viewModel.cropStages.value[--viewModel.selectedWeekIndex.value]
                        viewModel.selectedOperationIndex.postValue(viewModel.selectedOperationIndex.value - 1)
//                        viewModel.selectedStage.postValue(stage)
                    }
                }
        )
        Log.d("FARMERSTAGE", "StageBanner: selected operation index ${taskList?.size} ${viewModel.selectedOperationIndex.value}")
        if(viewModel.selectedOperationIndex.value!=-1) {
            taskList?.get(viewModel.selectedOperationIndex.value)?.oprationType?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body2,
                    color = Color.Apple
                )
            }
        }

        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = Color.Apple,
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    if ((viewModel.selectedOperationIndex.value + 1) < taskList?.size!!) {
//                        val stage =
//                            viewModel.cropStages.value[++viewModel.selectedWeekIndex.value]
                        viewModel.selectedOperationIndex.postValue(viewModel.selectedOperationIndex.value + 1)
//                        viewModel.selectedStage.postValue(stage)
                    }
                }
        )
    }
}