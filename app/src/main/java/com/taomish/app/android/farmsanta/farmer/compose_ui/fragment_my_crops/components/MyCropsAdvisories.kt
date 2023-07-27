package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_crops.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.shimmerEffect
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components.LatestCropAdvisoryItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel


@Composable
fun MyCropAdvisories(
    homeViewModel: HomeViewModel,
    selectedCrop: MutableState<CropMaster?>,
    onSectionViewMoreClicked: (Screen) -> Unit,
    goToViewAdvisoryFragment: (CropAdvisory) -> Unit
) {
    val spacing = LocalSpacing.current
    val advisories = homeViewModel.cropAdvisories.value?.filter { it.crop == selectedCrop.value?.uuid }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "${str(id = R.string.latest)} ${selectedCrop.value?.cropName ?: ""} ${str(id = R.string.advisories)}",
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = str(id = R.string.view_more),
                color = Color.LightGray,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.clickable(onClick = { onSectionViewMoreClicked(Screen.CropAdvisory) })
            )
        }

        if (advisories.isNullOrEmpty().not()) {
            LazyRow {
                items(advisories.orEmpty()) { advisory ->
                    LatestCropAdvisoryItem(
                        advisory = advisory,
                        onAdvisoryClicked = { goToViewAdvisoryFragment(advisory) }
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = str(id = R.string.no_advisory_for_crop),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.medium)
                )
            }
        }
    }
}