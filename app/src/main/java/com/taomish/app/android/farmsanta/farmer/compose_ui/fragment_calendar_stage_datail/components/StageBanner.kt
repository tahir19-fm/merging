package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail.components

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
import com.taomish.app.android.farmsanta.farmer.utils.notNull
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun StageBanner(viewModel: CropCalendarViewModel, onBack: () -> Unit) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = spacing.medium)
    ) {
        Box(
            modifier = Modifier
                .padding(start = spacing.small)
                .fillMaxWidth(),
        ) {
            Icon(
                imageVector = Icons.Filled.ChevronLeft,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.Center)
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
                            if ((viewModel.selectedStageIndex - 1) >= 0) {
                                val stage =
                                    viewModel.cropStages.value[--viewModel.selectedStageIndex]
                                viewModel.selectedStage.postValue(stage)
                            }
                        }
                )

                Text(
                    text = viewModel.stagesMap[viewModel.selectedStage.value?.stageName]?.name.notNull(),
                    style = MaterialTheme.typography.body2,
                    color = Color.Apple
                )

                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = Color.Apple,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            if ((viewModel.selectedStageIndex + 1) < viewModel.cropStages.value.size) {
                                val stage =
                                    viewModel.cropStages.value[++viewModel.selectedStageIndex]
                                viewModel.selectedStage.postValue(stage)
                            }
                        }
                )
            }
        }
    }
}