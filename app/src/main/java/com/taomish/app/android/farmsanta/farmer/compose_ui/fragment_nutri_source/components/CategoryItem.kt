package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_nutri_source.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun CategoryItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    onCloseClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        Icon(
            imageVector = if (selected) Icons.Filled.Circle else Icons.Outlined.Circle,
            contentDescription = null,
            tint = if (selected) Color.Cameron else Color.Gray,
            modifier = Modifier.size(16.dp)
        )

        if (selected) {
            Row(
                modifier = Modifier
                    .background(color = Color.Cameron, shape = CircleShape)
                    .padding(spacing.extraSmall),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.extraSmall)
            ) {
                Text(text = text, color = Color.White, style = MaterialTheme.typography.body2)
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.Cameron,
                    modifier = Modifier
                        .size(16.dp)
                        .background(color = Color.White, shape = CircleShape)
                        .padding(spacing.tiny)
                        .clip(CircleShape)
                        .clickable(onClick = onCloseClicked)
                )
            }
        } else {
            Text(text = text, color = Color.Gray, style = MaterialTheme.typography.body2)
        }
    }
}