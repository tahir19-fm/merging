package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.enums.VIEW
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SocialWallViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_posts.components.SavedPostsGridView
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_posts.components.SavedPostsListView
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message

//TODO: API Integration is remained for Whole Screen.

@Composable
fun SavedPostsFragmentScreen(viewModel: SocialWallViewModel, myPosts: List<Message>) {
    val spacing = LocalSpacing.current
    var view by remember { mutableStateOf(VIEW.LIST) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.extraLarge),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_list_view),
                contentDescription = null,
                tint = if (view == VIEW.LIST) Color.Cameron else Color.LightGray,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable { view = VIEW.LIST }
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_tile_view),
                contentDescription = null,
                tint = if (view == VIEW.GRID) Color.Cameron else Color.LightGray,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable { view = VIEW.GRID }
            )
        }

        if (view == VIEW.LIST) {
            SavedPostsListView(myPosts = myPosts)
        } else {
            SavedPostsGridView(myPosts = myPosts)
        }
    }
}