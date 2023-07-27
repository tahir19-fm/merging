package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.shimmerEffect
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto


@Composable
fun PopsSection(
    searchText: String,
    getPops: () -> List<PopDto>?,
    onNavigationItemClicked: (Screen) -> Unit,
    goToViewPop: (PopDto) -> Unit
) {
    val spacing = LocalSpacing.current

    val pops = if (searchText.isEmpty()) getPops() else getPops()?.filter {
        it.getCropNameById().lowercase().contains(searchText.lowercase())
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
                modifier = Modifier.clickable(onClick = { onNavigationItemClicked(Screen.Pops) })
            )
        }


        if (pops != null && pops.isEmpty()) {
            Text(
                text = str(id = R.string.no_pop_msg),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )
        }

        if (pops != null) {
            LazyRow {
                itemsIndexed(pops) { _, pop ->
                    HomePopsItem(popDto = pop, onPopItemClicked = { goToViewPop(pop) })
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