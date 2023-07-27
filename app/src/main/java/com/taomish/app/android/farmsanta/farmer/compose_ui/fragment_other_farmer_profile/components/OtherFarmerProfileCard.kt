package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_other_farmer_profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
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
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants


@Composable
fun OtherFarmerProfileCard(
    userName: String,
    imageLink: String,
    myPostsCount: Int
) {
    val spacing = LocalSpacing.current
    val backgroundShape = LocalShapes.current.smallShape
    var size by remember { mutableStateOf(Size.Zero) }
    val height: @Composable () -> Dp = { with(LocalDensity.current) { size.height.toDp() } }
    val windowHeight = LocalConfiguration.current.screenHeightDp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(254.dp)
            .padding(vertical = spacing.small)
            .onGloballyPositioned { size = it.size.toSize() }
    ) {

        Card(
            modifier = Modifier
                .padding(
                    top = spacing.extraLarge + spacing.medium,
                    start = spacing.medium,
                    end = spacing.medium
                )
                .background(color = Color.White, shape = backgroundShape)
                .fillMaxWidth()
                .height(height() + spacing.medium)
                .align(Alignment.Center),
            elevation = 2.dp,
            backgroundColor = Color.White
        ) { }

        Column(
            modifier = Modifier
                .padding(top = spacing.medium)
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

            Text(
                text = str(id = R.string.following),
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier
                    .padding(top = spacing.small)
                    .background(color = Color.Cameron, shape = CircleShape)
                    .padding(spacing.small)
                    .clip(CircleShape)
                    .clickable(onClick = {})
            )


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