package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_nutri_source_details.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CircleIconWithText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.AzureRadiance
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Valencia


@Composable
fun NutriSourceDetailsBottomSheet(
    onSaveClicked: () -> Unit,
    onDownloadClicked: () -> Unit,
    onShareClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .padding(spacing.medium)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    onClick = onSaveClicked
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Bookmark,
                contentDescription = null,
                tint = Color.Valencia,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .border(
                        width = 0.3.dp,
                        color = Color.Valencia,
                        shape = CircleShape
                    )
                    .padding(spacing.small)
            )

            Text(
                text = str(id = R.string.save),
                color = Color.Valencia,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(vertical = spacing.small)
            )
        }

        CircleIconWithText(
            iconId = R.drawable.ic_download_green,
            text = str(id = R.string.download),
            tint = Color.Cameron,
            iconSize = 56.dp,
            onClick = onDownloadClicked
        )

        CircleIconWithText(
            iconId = R.drawable.ic_share_paper_plane,
            text = str(id = R.string.share),
            tint = Color.AzureRadiance,
            iconSize = 56.dp,
            onClick = onShareClicked
        )
    }
}