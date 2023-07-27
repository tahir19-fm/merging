package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel


@Composable
fun NearbyRelatedFarmQueriesSection(
    homeViewModel: HomeViewModel,
    getScouting: () -> List<FarmScouting?>?,
    onNavigationItemClicked: (Screen) -> Unit,
    goToQueryDetailsFragment: (FarmScouting, Int) -> Unit
) {
    val spacing = LocalSpacing.current
    val searchText by homeViewModel.searchText.observeAsState("")
    val scouting = if (searchText.isEmpty())
        getScouting()
    else
        getScouting()?.filter {
            it?.getCropName()?.lowercase()?.contentEquals(searchText.lowercase()) == true
        }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = str(id = R.string.near_by_related_farmer_queries),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onNavigationItemClicked(Screen.AskQueries) }
            )
        }

        if (scouting != null && scouting.isEmpty()) {
            Text(
                text = str(id = R.string.no_farmer_queries),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )
        }

        if (scouting != null) {
            LazyRow {
                itemsIndexed(scouting) { index, farmScouting ->
                    if (farmScouting != null) {
                        NearbyRelatedQueryItem(
                            homeViewModel = homeViewModel,
                            farmScouting = farmScouting,
                            goToQueryDetailsFragment = {
                                goToQueryDetailsFragment(
                                    farmScouting,
                                    index
                                )
                            }
                        )
                    }
                }
            }
        } else {
            LazyRow {
                items(5) {
                    Spacer(
                        modifier = Modifier
                            .padding(spacing.small)
                            .width(180.dp)
                            .height(200.dp)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}