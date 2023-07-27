package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.withRunningRecomposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.shimmerEffect
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message


@Composable
fun TrendingFarmerTalks(
    searchText: String = "",
    getMessages: () -> ArrayList<Message?>?,
    onViewTalkClicked: (Int) -> Unit,
    onNavigationItemClicked: (Screen) -> Unit
) {
    val spacing = LocalSpacing.current
    val messages = if (searchText.isEmpty()) getMessages() else getMessages()?.filter {
        it?.title?.lowercase()?.contains(searchText.lowercase()) == true
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = str(id = R.string.trending_farm_talk),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = str(id = R.string.view_more),
                color = Color.LightGray,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.clickable(onClick = { onNavigationItemClicked(Screen.FarmTalks) })
            )
        }

        if (messages != null) {
            LazyRow {
                itemsIndexed(messages) { index, message ->
                    if (message != null) {
                        TrendingTalkItem(
                            message = message,
                            onViewTalkClicked = { onViewTalkClicked(index) }
                        )
                    }
                }
            }
        } else {
            LazyRow {
                items(5) {
                    Spacer(
                        modifier = Modifier
                            .width(100.dp)
                            .height(200.dp)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}