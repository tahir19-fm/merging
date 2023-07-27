package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_pop.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Valencia


@Composable
fun ActionsRow(
    getPopName: () -> String,
    isBookmarked: Boolean,
    onBookmark: () -> Unit,
    onShare: () -> Unit,
    onMoreOptionClicked: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = getPopName(), fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(
                painter = painterResource(
                    id = if (isBookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
                ),
                contentDescription = null,
                tint = if (isBookmarked) Color.Valencia else Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onBookmark)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_share_paper_plane),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = spacing.small)
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onShare)
            )

            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable { onMoreOptionClicked() }
            )
        }
    }
}