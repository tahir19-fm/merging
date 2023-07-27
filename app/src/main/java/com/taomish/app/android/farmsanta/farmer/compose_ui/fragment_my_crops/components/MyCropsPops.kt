package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_crops.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components.HomePopsItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel


@Composable
fun MyCropsPops(
    homeViewModel: HomeViewModel,
    selectedCrop: MutableState<CropMaster?>,
    onSectionViewMoreClicked: (Screen) -> Unit,
    goToViewPop: (PopDto) -> Unit
) {
    val spacing = LocalSpacing.current
    val pops by remember {
        mutableStateOf(
            homeViewModel.userPops.value?.filter { it.crop == selectedCrop.value?.uuid }.orEmpty()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = str(id = R.string.pop),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = str(id = R.string.view_more),
                color = Color.LightGray,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.clickable(onClick = { onSectionViewMoreClicked(Screen.Pops) })
            )
        }

        if (homeViewModel.cropPops.value.isNotEmpty()) {
            LazyRow {
                itemsIndexed(homeViewModel.cropPops.value) { _, pop ->
                    HomePopsItem(popDto = pop, onPopItemClicked = { goToViewPop(pop) })
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = str(id = R.string.no_pop_for_crop),
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

