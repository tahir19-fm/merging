package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_posts.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message


@Composable
fun SavedPostsGridView(myPosts: List<Message>) {
    val spacing = LocalSpacing.current
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(120.dp)
    ) {
        items(myPosts) { message ->
            val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                    message.images
                        ?.elementAtOrNull(0)
                        ?.fileName.toString()

            RemoteImage(
                modifier = Modifier
                    .padding(spacing.extraSmall),
                imageLink = imageLink,
                error = R.mipmap.img_default_pop,
                contentScale = ContentScale.Fit
            )
        }
    }
}