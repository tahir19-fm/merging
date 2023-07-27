package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory


@Composable
fun LatestCropAdvisoriesSection(
    modifier: Modifier = Modifier,
    searchText: String = "",
    getAdvisories: () -> List<CropAdvisory>?,
    onNavigationItemClicked: (Screen) -> Unit,
    openViewAdvisoryFragment: (CropAdvisory) -> Unit
) {
    val spacing = LocalSpacing.current
    val advisories = if (searchText.isEmpty())
        getAdvisories()
    else
        getAdvisories()?.filter {
            it.advisoryTagName?.lowercase()?.contains(searchText.lowercase()) == true ||
                    it.cropName?.lowercase()?.contains(searchText.lowercase()) == true
        }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = str(id = R.string.latest_crop_advisories),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = str(id = R.string.view_more),
                color = Color.LightGray,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.clickable(onClick = { onNavigationItemClicked(Screen.CropAdvisory) })
            )
        }

        if (advisories != null && advisories.isEmpty()) {
            Text(
                text = str(id = R.string.no_advisories_msg),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )
        }

        if (advisories != null) {
            LazyRow {
                items(advisories) { advisory ->
                    LatestCropAdvisoryItem(
                        advisory = advisory,
                        onAdvisoryClicked = { openViewAdvisoryFragment(advisory) }
                    )
                }
            }
        } else {
            LazyRow {
                items(5) {
                    Spacer(
                        modifier = Modifier
                            .padding(spacing.small)
                            .width(160.dp)
                            .height(200.dp)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}