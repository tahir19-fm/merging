package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.ProfileWithBadge
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun HomeFarmTalkSection(
    getProfiles: () -> Map<String, String>,
    onClickAdd: () -> Unit,
    onNavigationItemClicked: (Screen) -> Unit
) {
    val spacing = LocalSpacing.current
    val profiles = getProfiles()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = str(id = R.string.farm_talk),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onNavigationItemClicked(Screen.FarmTalks) }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .padding(end = spacing.extraSmall)
                    .clickable(onClick = onClickAdd),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_farm_talk_new),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(end = spacing.extraSmall)
                            .size(48.dp)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_green_new),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.BottomEnd)
                    )
                }

                Text(
                    text = str(id = R.string.create_post),
                    color = Color.Cameron,
                    style = MaterialTheme.typography.caption
                )
            }

            if (profiles.isNotEmpty()) {
                LazyRow {
                    items(profiles.entries.toList()) { message ->
                        ProfileWithBadge(
                            userName = message.key,
                            imageLink = message.value,
                            onGoToOtherFarmerProfileClicked = {},
                            showBadge = false
                        )
                    }
                }
            } else {
                LazyRow {
                    items(5) {
                        CircleProfileShimmerItem()
                    }
                }
            }
        }
    }
}