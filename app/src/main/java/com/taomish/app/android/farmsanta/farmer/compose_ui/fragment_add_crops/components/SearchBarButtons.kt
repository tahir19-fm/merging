package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun SearchBarButtons(
    onSearch: () -> Unit,
    showClose: () -> Boolean,
    onClose: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .clickable(onClick = onSearch)
        )

        if (showClose()) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier
                    .padding(spacing.small)
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onClose)
            )
        }
    }
}