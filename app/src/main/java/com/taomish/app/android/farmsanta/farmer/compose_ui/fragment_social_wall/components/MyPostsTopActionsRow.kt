package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.AzureRadiance
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun MyPostsTopActionsRow(
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onMoreClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier.padding(top = spacing.small, start = spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionIcon(
            text = str(id = R.string.edit),
            imageVector = Icons.Filled.Edit,
            color = Color.AzureRadiance,
            onClick = onEditClicked
        )

        ActionIcon(
            text = str(id = R.string.delete),
            imageVector = Icons.Filled.Delete,
            color = Color.Red,
            onClick = onDeleteClicked
        )

        ActionIcon(
            text = str(id = R.string.more),
            imageVector = Icons.Filled.MoreVert,
            color = Color.Cameron,
            onClick = onMoreClicked
        )
    }
}

@Composable
fun ActionIcon(
    text: String,
    imageVector: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.padding(spacing.extraSmall),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier
                .background(color = color, shape = CircleShape)
                .border(width = 2.dp, color = Color.White, shape = CircleShape)
                .padding(spacing.extraSmall)
                .clip(CircleShape)
                .clickable {
                    onClick.invoke()
                },
            tint = Color.White
        )

        Text(
            text = text,
            style = MaterialTheme.typography.caption,
            color = Color.White
        )
    }
}