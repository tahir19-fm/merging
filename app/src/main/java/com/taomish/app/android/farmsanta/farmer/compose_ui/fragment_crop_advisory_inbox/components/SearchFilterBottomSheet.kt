package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropAdvisoryViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@Composable
fun SearchFilterBottomSheet(
    cropAdvisoryViewModel: CropAdvisoryViewModel,
    onClickApply: () -> Unit,
    onClickCancel: () -> Unit
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val msg = str(id = R.string.you_have_already)
    LazyColumn(
        modifier = Modifier
            .background(color = Color.RiceFlower)
            .padding(top = spacing.medium)
    ) {
        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.small, horizontal = spacing.medium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = str(id = R.string.filter),
                    fontWeight = FontWeight.Bold,
                    color = Color.Cameron
                )

                Text(
                    text = str(id = R.string.apply),
                    fontWeight = FontWeight.Bold,
                    color = Color.Cameron,
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource(),
                        onClick = onClickApply
                    )
                )
            }

            FilterOptions(
                title = str(id = R.string.select_crops),
                options = cropAdvisoryViewModel.cropsMap.value.values.toList(),
                getText = { it.cropName },
                selectedOptions = cropAdvisoryViewModel.filterCrops,
                onSelect = {
                    if (cropAdvisoryViewModel.filterCrops.find { crop -> it.uuid == crop.uuid } == null) {
                        cropAdvisoryViewModel.filterCrops.add(0, it)
                    } else {
                        context.showToast("$msg ${it.cropName}")
                    }
                },
                onDelete = { cropAdvisoryViewModel.filterCrops.remove(it) }
            )

            FilterOptions(
                title = str(id = R.string.select_advisories),
                options = cropAdvisoryViewModel.advisoryTags.value.values.toList(),
                getText = { it.name },
                selectedOptions = cropAdvisoryViewModel.filterAdvisoryTags,
                onSelect = {
                    if (cropAdvisoryViewModel.filterAdvisoryTags.find { crop -> it == crop } == null) {
                        cropAdvisoryViewModel.filterAdvisoryTags.add(0, it)
                    } else {
                        context.showToast("$msg ${it.name}")
                    }
                },
                onDelete = { cropAdvisoryViewModel.filterAdvisoryTags.remove(it) }
            )

            FilterOptions(
                title = str(id = R.string.select_growth_stages),
                options = cropAdvisoryViewModel.growthStages.value.values.toList(),
                getText = { it.name },
                selectedOptions = cropAdvisoryViewModel.filterGrowthStages,
                onSelect = {
                    if (cropAdvisoryViewModel.filterGrowthStages.find { crop -> it == crop } == null) {
                        cropAdvisoryViewModel.filterGrowthStages.add(0, it)
                    } else {
                        context.showToast("$msg ${it.name}")
                    }
                },
                onDelete = { cropAdvisoryViewModel.filterGrowthStages.remove(it) }
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.fillMaxWidth(.5f),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    onClick = onClickCancel
                ) {
                    Text(
                        text = str(id = R.string.cancel),
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = spacing.small)
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cameron),
                    onClick = onClickApply
                ) {
                    Text(
                        text = str(id = R.string.apply),
                        style = MaterialTheme.typography.body2,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = spacing.small)
                    )
                }
            }
        }
    }
}