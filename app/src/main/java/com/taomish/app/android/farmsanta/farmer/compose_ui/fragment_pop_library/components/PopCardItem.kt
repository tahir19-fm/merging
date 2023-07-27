package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_library.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Chip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ChipIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.MoreVertIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Valencia
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PopCardItem(
    pop: PopDto,
    getCropName: () -> String,
    onMoreOptionClicked: (PopDto) -> Unit,
    onBookmarkClicked: (String) -> Unit,
    onShareClicked: () -> Unit,
    onPopItemClicked: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onPopItemClicked
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = getCropName(),
                modifier = Modifier.padding(
                    vertical = spacing.small,
                    horizontal = spacing.extraSmall
                ),
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            MoreVertIcon { onMoreOptionClicked(pop) }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {

            val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                    pop.photos
                        ?.elementAtOrNull(0)
                        ?.fileName.toString()

            RemoteImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = spacing.small)
                    .clip(RectangleShape),
                imageLink = imageLink,
                contentScale = ContentScale.FillBounds,
                error = R.mipmap.img_default_pop
            )

            Chip(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = spacing.medium),
                onClick = { onPopItemClicked() },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_forword_arrow_round),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(vertical = spacing.small, horizontal = spacing.small)
                        .size(32.dp)
                )
            }

            Chip(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = spacing.small, bottom = spacing.medium),
                text = pop.updatedTimestamp ?: "",
                textColor = Color.White,
                textStyle = MaterialTheme.typography.caption,
                backgroundColor = Color.Black.copy(alpha = .5f)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = spacing.small)
            ) {
                ChipIcon(
                    iconId = if (pop.bookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark,
                    iconColor = if (pop.bookmarked) Color.Valencia else Color.Black,
                    size = 24.dp,
                    backgroundShape = CircleShape,
                    backgroundColor = Color.White.copy(alpha = .7f),
                    onClick = { onBookmarkClicked(pop.uuid) }
                )

                ChipIcon(
                    iconId = R.drawable.ic_share_paper_plane,
                    iconColor = Color.Black,
                    size = 24.dp,
                    backgroundShape = CircleShape,
                    backgroundColor = Color.White.copy(alpha = .7f),
                    onClick = { onShareClicked() }
                )
            }
        }
    }
}