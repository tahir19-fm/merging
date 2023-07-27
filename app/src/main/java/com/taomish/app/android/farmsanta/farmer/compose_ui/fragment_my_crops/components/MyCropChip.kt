package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_crops.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OrangePeel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyCropChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit = {},
    showClose: Boolean = true,
    onCloseClicked: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val colors = ChipDefaults.chipColors(
        backgroundColor = if (isSelected) Color.Cameron else Color.White,
        contentColor = if (isSelected) Color.White else Color.Cameron
    )

    Box(modifier = Modifier.padding(spacing.extraSmall)) {
        Chip(
            onClick = onClick,
            colors = colors,
            border = BorderStroke(width = .4.dp, color = Color.Cameron)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_mango),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Text(
                text = name,
                color = Color.OrangePeel,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = spacing.extraSmall)
            )
        }

        if (showClose) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(16.dp)
                    .background(color = Color.OrangePeel, shape = CircleShape)
                    .padding(spacing.extraSmall)
                    .clip(CircleShape)
                    .clickable(onClick = onCloseClicked)
            )
        }
    }
}