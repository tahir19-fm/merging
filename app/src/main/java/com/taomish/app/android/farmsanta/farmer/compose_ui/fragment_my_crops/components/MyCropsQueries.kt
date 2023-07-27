package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_crops.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.shimmerEffect
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components.NearbyRelatedQueryItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel


@Composable
fun MyCropsQueries(
    homeViewModel: HomeViewModel,
    selectedCrop: MutableState<CropMaster?>,
    onSectionViewMoreClicked: (Screen) -> Unit,
    goToQueryDetailsFragment: (FarmScouting, Int) -> Unit
) {
    val spacing = LocalSpacing.current
    val queries = homeViewModel.getFarmsScouting()?.filter {
        it?.crop == selectedCrop.value?.uuid
    }.orEmpty()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = str(id = R.string.nearby_related_crop_queries),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onSectionViewMoreClicked(Screen.AskQueries) }
            )
        }

        if (queries.isNotEmpty()) {
            LazyRow {
                itemsIndexed(queries) { index, farmScouting ->
                    if (farmScouting != null) {
                        NearbyRelatedQueryItem(
                            homeViewModel = homeViewModel,
                            farmScouting = farmScouting,
                            goToQueryDetailsFragment = { goToQueryDetailsFragment(farmScouting, index) }
                        )
                    }
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

