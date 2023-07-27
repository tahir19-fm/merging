package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.TextStack
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Thunderbird
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants


@Composable
fun MyPostsProfileCard(
    userName: String,
    imageLink: String,
    myPostsCount: Int,
    onClickMyActivities: () -> Unit,
    onCreatePostClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    val backgroundShape = LocalShapes.current.smallShape
    var size by remember { mutableStateOf(Size.Zero) }
    val height: @Composable () -> Dp = { with(LocalDensity.current) { size.height.toDp() } }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(254.dp)
            .padding(vertical = spacing.small)
            .onGloballyPositioned { size = it.size.toSize() }
    ) {

        RemoteImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.6f),
            imageLink = "",
            error = R.mipmap.img_default_pop,
            contentScale = ContentScale.FillBounds
        )

        Card(
            modifier = Modifier
                .padding(
                    top = spacing.extraLarge + spacing.large,
                    start = spacing.medium,
                    end = spacing.medium
                )
                .background(color = Color.White, shape = backgroundShape)
                .fillMaxWidth()
                .height(height() + spacing.large)
                .align(Alignment.Center),
            elevation = 4.dp,
            backgroundColor = Color.White
        ) { }

        Row(modifier = Modifier.align(Alignment.TopEnd)) {
            SearchBarIconButton(
                iconId = R.drawable.ic_chat_heart,
                iconTint = Color.Thunderbird,
                padding = spacing.extraSmall,
                backgroundPadding = spacing.extraSmall,
                onClick = { onClickMyActivities() }
            )
        }

        Column(
            modifier = Modifier
                .padding(top = spacing.large)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RemoteImage(
                modifier = Modifier
                    .size(80.dp)
                    .border(width = 3.dp, color = Color.White, shape = CircleShape)
                    .clip(CircleShape),
                imageLink = URLConstants.S3_IMAGE_BASE_URL + imageLink,
                contentScale = ContentScale.FillBounds
            )

            Text(
                text = userName,
                style = MaterialTheme.typography.body2,
                color = Color.Cameron,
                modifier = Modifier.padding(top = spacing.small)
            )

            Row(
                modifier = Modifier
                    .padding(top = spacing.small)
                    .border(width = 0.5.dp, color = Color.White, shape = CircleShape)
                    .background(color = Color.Cameron, shape = CircleShape)
                    .padding(spacing.small)
                    .clip(CircleShape)
                    .clickable(onClick = onCreatePostClicked),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = str(id = R.string.create_new_post),
                    style = MaterialTheme.typography.body2,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.small, horizontal = spacing.extraLarge),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextStack(heading = str(id = R.string.my_posts), value = myPostsCount)
                TextStack(heading = str(id = R.string.followers), value = 0)
                TextStack(heading = str(id = R.string.following), value = 0)
            }
        }
    }
}