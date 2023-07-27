package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PreviousChip(
    backgroundColor: Color,
    disabledBackgroundColor: Color = backgroundColor.copy(alpha = .3f),
    enabled: Boolean,
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Chip(
        onClick = onClick,
        colors = ChipDefaults.chipColors(
            backgroundColor = backgroundColor,
            contentColor = Color.White,
            disabledContentColor = Color.Gray,
            disabledBackgroundColor = disabledBackgroundColor
        ),
        enabled = enabled
    ) {
        Icon(
            imageVector = Icons.Filled.ChevronLeft,
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = spacing.extraSmall, horizontal = spacing.tiny)
                .size(16.dp)
        )

        Text(
            text = str(id = R.string.previous),
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(vertical = spacing.extraSmall, horizontal = spacing.tiny)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NextChip(
    @StringRes textId: Int = R.string.next,
    backgroundColor: Color,
    disabledBackgroundColor: Color = backgroundColor.copy(alpha = .3f),
    enabled: Boolean,
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Chip(
        onClick = onClick,
        colors = ChipDefaults.chipColors(
            backgroundColor = backgroundColor,
            contentColor = Color.White,
            disabledContentColor = Color.Gray,
            disabledBackgroundColor = disabledBackgroundColor
        ),
        enabled = enabled
    ) {
        Text(
            text = str(id = textId),
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(vertical = spacing.extraSmall, horizontal = spacing.tiny)
        )

        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = spacing.extraSmall, horizontal = spacing.tiny)
                .size(16.dp)
        )
    }
}