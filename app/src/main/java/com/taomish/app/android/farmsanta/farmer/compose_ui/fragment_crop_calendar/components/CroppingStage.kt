package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.CropStageCalendar
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import kotlin.math.abs


@Composable
fun CroppingStage(
    stage: CropStageCalendar,
    getStageName: () -> String,
    currentTaskPosition: MutableState<Float>,
    onWeekClicked: (Int) -> Unit,
) {
    val colors = listOf(
        Color.Apple,
        Color.Limeade,
        Color.Citron,
        Color.Bahia,
        Color.ButterCup
    )
    Column(modifier = Modifier.fillMaxWidth()) {
        StageBanner(
            bannerId = stage.stageStatus,
            stageName = getStageName(),
            stageWeek = stage.stageWeek,
            startEndDate = stage.startEndDate,
            isCurrentTask = stage.isCurrentTask
        )

        stage.weeks.forEachIndexed { index, week ->
            Week(
                modifier = Modifier.onGloballyPositioned {
                    if (week.isCurrentTask) {
                        currentTaskPosition.postValue(abs(it.positionInRoot().y))
                    }
                },
                week = week,
                backgroundColor = colors[index % (colors.size - 1)]
            ) { onWeekClicked(index) }
        }
    }
}