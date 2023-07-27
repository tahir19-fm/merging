package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_library.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CircleIconWithText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.AzureRadiance
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OutrageousOrange
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto


@Composable
fun AllPopModalSheetContent(
    pop: PopDto?,
    onViewPopClicked: () -> Unit,
    onSaveClicked: (PopDto) -> Unit,
    onShareClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    if (pop == null) {
        Box(modifier = Modifier.size(1.dp))
        return
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIconWithText(
                iconId = R.drawable.ic_view_pop,
                text = str(id = R.string.view_pop),
                tint = Color.Cameron,
                onClick = onViewPopClicked
            )

            CircleIconWithText(
                iconId = R.drawable.ic_bookmark,
                text = str(id = R.string.save),
                tint = Color.OutrageousOrange,
                onClick = {
                    if (pop.bookmarked.not()) {
                        onSaveClicked(pop)
                    }
                }
            )

            CircleIconWithText(
                iconId = R.drawable.ic_share_paper_plane,
                text = str(id = R.string.share),
                tint = Color.AzureRadiance,
                onClick = onShareClicked
            )
        }

        /*Column(
            modifier = Modifier
                .padding(spacing.medium)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    onClick = onProfileClicked
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RemoteImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(width = 0.3.dp, color = Color.Cameron, shape = CircleShape),
                imageLink = URLConstants.S3_IMAGE_BASE_URL + pop.profileImage,
                contentScale = ContentScale.Crop
            )

            Text(
                text = "View Profile",
                color = Color.Cameron,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = spacing.small)
            )
        }*/
    }

}