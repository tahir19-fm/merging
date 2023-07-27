package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Apple
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.CropCalendarsConstants.OPERATION_FERTILIZER_CALCULATOR
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.StageWeek
import com.taomish.app.android.farmsanta.farmer.utils.notNull
import kotlin.math.abs


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeekDetailView(
    viewModel: CropCalendarViewModel,
    week: StageWeek,
    startEndDate: String?,
    SelectedDescription: String?,
    getStageName: () -> String,
    goToFertilizerCalculator: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    var showFertilizerCalculator by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth()
                .height(300.dp)
        ) {
            val imageUrl =
                URLConstants.S3_IMAGE_BASE_URL + week.tasklist
                    ?.getOrNull(0)?.taskImages
                    ?.elementAtOrNull(viewModel.selectedOperationIndex.value)
            Log.d("cropcalenderdetail", "WeekDetailView: ${week.tasklist?.get(0)?.taskImages?.size}")
            Log.d("cropcalenderdetail", "image url: $imageUrl")
//           val url= week.tasklist?.get(0)?.taskImages?.get(viewModel.selectedOperationIndex.value)
//                ?: ""
            RemoteImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape),
                imageLink = imageUrl,
                contentScale = ContentScale.FillBounds,
                error = R.drawable.tractor
            )
        }

        Spacer(modifier = Modifier.height(spacing.medium))

        Chip(
            onClick = { },
            colors = ChipDefaults.chipColors(
                backgroundColor = Color.Apple,
                contentColor = Color.White
            ),
            shape = CircleShape
        ) {
            Text(text = getStageName(), style = MaterialTheme.typography.caption)
        }

        if (showFertilizerCalculator) {
            Chip(
                onClick = goToFertilizerCalculator,
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.Apple,
                    contentColor = Color.White
                ),
                shape = CircleShape
            ) {
                Text(
                    text = str(id = R.string.calculate_fertilizer),
                    style = MaterialTheme.typography.caption
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.medium))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            val weekNo = week.weekInfo?.let {
                if (it == 0) str(id = R.string.current_week)
                else if (it < 0) "${abs(it)} ${str(id = R.string.week)} ${str(id = R.string.before)}"
                else "${str(id = R.string.week)} $it"
            } ?: ""

            Text(
                text = weekNo,
                color = Color.Cameron,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(spacing.small)
            )

//            Text(
//                text = "(${startEndDate.notNull()})",
//                color = Color.Gray,
//                style = MaterialTheme.typography.body2,
//                modifier = Modifier.padding(spacing.small)
//            )
        }

        Text(
            text = week.tasklist?.get(viewModel.selectedOperationIndex.value)?.oprationName ?: "",
            color = Color.Cameron,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(spacing.small)
        )

        Text(
            text = week.tasklist?.get(viewModel.selectedOperationIndex.value)?.oprationDescription ?: "",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = spacing.small),
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(spacing.small))

    }
}