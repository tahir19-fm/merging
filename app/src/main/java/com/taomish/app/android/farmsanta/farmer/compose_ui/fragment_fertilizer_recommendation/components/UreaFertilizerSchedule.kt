package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_recommendation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FertilizerCalculatorViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun UreaFertilizerSchedule(
    viewModel: FertilizerCalculatorViewModel
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .background(color = Color.White, shape = shape)
    ) {

        Text(
            text = str(id = R.string.urea_fertilizer_schedule),
            color = Color.Limeade,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(vertical = spacing.medium, horizontal = spacing.small)
        )

        Divider(color = Color.Limeade, thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = str(id = R.string.fertilizer_schedule),
                style = MaterialTheme.typography.caption,
                color = Color.Limeade,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .padding(spacing.extraSmall)
            )

            Text(
                text = str(id = R.string.quantity),
                style = MaterialTheme.typography.caption,
                color = Color.Limeade,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Divider(color = Color.Limeade, thickness = 1.dp)
        if (viewModel.showFertilizerSchedule()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small)
            ) {
                FertilizerScheduleRow(
                    fertilizerSchedule = str(id = R.string.before_transplanting),
                    quantity = viewModel.fertilizerGeneratedReport?.getFinalReport()?.lastploughing
                        ?: ""
                )
                FertilizerScheduleRow(
                    fertilizerSchedule = str(id = R.string.at_time_of_sowing),
                    quantity = viewModel.fertilizerGeneratedReport?.getFinalReport()?.timeOfsowing
                        ?: ""
                )
                FertilizerScheduleRow(
                    fertilizerSchedule = str(id = R.string.twenty_five_to_thirty_days_after_sowing),
                    quantity = viewModel.fertilizerGeneratedReport?.getFinalReport()?.daysAfter25
                        ?: ""
                )
                FertilizerScheduleRow(
                    fertilizerSchedule = str(id = R.string.forty_to_fifty_days_after_sowing),
                    quantity = viewModel.fertilizerGeneratedReport?.getFinalReport()?.daysAfter40
                        ?: ""
                )
            }
        }
    }
}