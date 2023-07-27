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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SelectableChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FertilizerCalculatorViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components.FertilizerTableRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun FertilizerTable(viewModel: FertilizerCalculatorViewModel) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val finalReport = viewModel.fertilizerGeneratedReport?.getFinalReport()

    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .background(color = Color.White, shape = shape)
    ) {

        SelectableChip(
            modifier = Modifier.padding(spacing.extraSmall),
            text = "${str(id = R.string.crop)}: ${viewModel.selectedCropName}",
            isSelected = false,
            unselectedBackgroundColor = Color.Limeade,
            unselectedContentColor = Color.White
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${str(id = R.string.crop_age)}: ${viewModel.fertilizerGeneratedReport?.ageOfPlant ?: "-"}",
                style = MaterialTheme.typography.overline,
                color = Color.Limeade,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .padding(spacing.extraSmall)
            )

            Text(
                text = "${str(id = R.string.crop_area)}: ${viewModel.fertilizerGeneratedReport?.area} Hectare",
                style = MaterialTheme.typography.overline,
                color = Color.Limeade,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.extraSmall)
            )
        }

        Divider(color = Color.Limeade, thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = str(id = R.string.fertilizer),
                style = MaterialTheme.typography.caption,
                color = Color.Limeade,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .padding(spacing.extraSmall)
            )

            Text(
                text = str(id = R.string.quantity),
                style = MaterialTheme.typography.caption,
                color = Color.Limeade,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(.5f)
            )

            Text(
                text = str(id = R.string.unit),
                style = MaterialTheme.typography.caption,
                color = Color.Limeade,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = spacing.extraSmall)
            )
        }

        Divider(color = Color.Limeade, thickness = 1.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small)
        ) {
            FertilizerTableRow(
                fertilizer = finalReport?.fym?.fertilizerName ?: "",
                quantity = "${finalReport?.fym?.quantity}",
                unit = finalReport?.fym?.unit ?: ""
            )
            FertilizerTableRow(
                fertilizer = finalReport?.vermicompost?.fertilizerName ?: "",
                quantity = "${finalReport?.vermicompost?.quantity}",
                unit = finalReport?.vermicompost?.unit ?: ""
            )
            FertilizerTableRow(
                fertilizer = finalReport?.npk?.fertilizerName ?: "",
                quantity = "${finalReport?.npk?.quantity}",
                unit = finalReport?.npk?.unit ?: ""
            )
            FertilizerTableRow(
                fertilizer = finalReport?.nitrogenous?.fertilizerName ?: "",
                quantity = "${finalReport?.nitrogenous?.quantity}",
                unit = finalReport?.nitrogenous?.unit ?: ""
            )
            FertilizerTableRow(
                fertilizer = finalReport?.potassium?.fertilizerName ?: "",
                quantity = "${finalReport?.potassium?.quantity}",
                unit = finalReport?.potassium?.unit ?: ""
            )
            FertilizerTableRow(
                fertilizer = finalReport?.zinc?.fertilizerName ?: "",
                quantity = finalReport?.zinc?.quantity?.toString() ?: "",
                unit = finalReport?.zinc?.unit ?: ""
            )
            FertilizerTableRow(
                fertilizer = finalReport?.phosphorus?.fertilizerName ?: "",
                quantity = finalReport?.phosphorus?.quantity?.toString() ?: "",
                unit = finalReport?.phosphorus?.unit ?: ""
            )
            FertilizerTableRow(
                fertilizer = finalReport?.boron?.fertilizerName ?: "",
                quantity = finalReport?.boron?.quantity?.toString() ?: "",
                unit = finalReport?.boron?.unit ?: ""
            )
        }
    }
}