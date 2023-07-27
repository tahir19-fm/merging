package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster


@Composable
fun AddCropCalendarBottomSheet(
    viewModel: CropCalendarViewModel,
    onSelect: (CropMaster) -> Unit,
    onDone: () -> Unit
) {
    val spacing = LocalSpacing.current
    LazyColumn(
        modifier = Modifier
            .background(color = Color.RiceFlower)
            .padding(top = spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.small, horizontal = spacing.medium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = str(id = R.string.select_crop),
                    fontWeight = FontWeight.Bold,
                    color = Color.Cameron
                )

                Text(
                    text = str(id = R.string.done),
                    fontWeight = FontWeight.Bold,
                    color = Color.Cameron,
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource(),
                        onClick = onDone
                    )
                )
            }

            Text(
                text = str(id = R.string.your_crops),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small)
            ) {
                items(viewModel.userCrops) { crop ->
                    CropCalendarChip(crop = crop, isSelected = true, onSelect = onSelect)
                }
            }


            Text(
                text = str(id = R.string.other_crops),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small)
            )
        }

        items(viewModel.allCropDivisions) { pair ->
            AddCropCalendarCropOptions(
                title = pair.second,
                options = viewModel.allCropsByDivisions.value[pair.first].orEmpty(),
                onSelect = onSelect
            )
        }

        item {
            RoundedShapeButton(
                modifier = Modifier
                    .padding(top = spacing.medium)
                    .fillMaxWidth(.5f),
                text = str(id = R.string.done),
                textPadding = spacing.extraSmall,
                textStyle = MaterialTheme.typography.caption,
                onClick = { }
            )
        }
    }
}