package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farmer.components.AddCropCircleCrop
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components.MyCropsAddButton
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster


@Composable
fun EditMyCrops(
    allSelectedCrops: SnapshotStateList<CropMaster>,
    onAddClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (allSelectedCrops.isEmpty()) {
            MyCropsAddButton(onAddClicked)
        }

        LazyRow {
            items(allSelectedCrops) { crop ->
                AddCropCircleCrop(
                    crop = crop,
                    isSelected = false,
                    unselectedColor = Color.White,
                    showClose = true,
                    onClick = { },
                    onCloseClicked = {
                        allSelectedCrops.remove(it)
                    }
                )
            }
        }
    }
}
