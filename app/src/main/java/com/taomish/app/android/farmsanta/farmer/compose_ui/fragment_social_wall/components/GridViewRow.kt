package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun GridViewRow(messages: List<Message?>, onMessageClick: (Message) -> Unit) {
    val spacing = LocalSpacing.current
    val modifiers = listOf(
        Modifier.fillMaxWidth(.3333333f),
        Modifier.fillMaxWidth(.5f),
        Modifier.fillMaxWidth()
    )
    
    /*LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(spacing.small),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        items(messages) {
            if (it != null) {
                val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                        it.images
                            ?.elementAtOrNull(0)?.fileName.toString()

                Box(modifier = Modifier.fillMaxWidth()
                    .height(100.dp)
                    .width(180.dp)
                ) {
                    RemoteImage(
                        modifier = Modifier
                            .padding(spacing.extraSmall)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        imageLink = imageLink,
                        error = R.mipmap.img_default_pop,
                        contentScale = ContentScale.FillBounds
                    )
                }


            }

        }
    }*/

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        messages.forEachIndexed { index, message ->

            if (message != null) {
                val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                        message.images
                            ?.elementAtOrNull(0)
                            ?.fileName.toString()

                RemoteImage(
                    modifier = modifiers[index]
                        .height(180.dp)
                        .padding(spacing.extraSmall)
                        .clickable { onMessageClick(message) },
                    imageLink = imageLink,
                    error = R.mipmap.img_default_pop,
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}