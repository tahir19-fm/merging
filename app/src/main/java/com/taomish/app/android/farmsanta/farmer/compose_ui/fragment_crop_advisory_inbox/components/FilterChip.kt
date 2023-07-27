package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.notNull


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : Any> FilterChip(
    item: T,
    getText: () -> String?,
    onDelete: ((T) -> Unit)? = null,
    onSelect: ((T) -> Unit)? = null,
    textStyle: TextStyle = MaterialTheme.typography.overline,
    backgroundColor: Color = Color.Cameron,
    contentColor: Color = Color.White,
    showCloseIcon: Boolean = true
) {
    val spacing = LocalSpacing.current
    Chip(
        modifier = Modifier.padding(horizontal = spacing.extraSmall),
        onClick = { onSelect?.invoke(item) },
        shape = CircleShape,
        colors = ChipDefaults.chipColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Text(text = getText().notNull(), style = textStyle)
        if (showCloseIcon) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = spacing.small)
                    .size(16.dp)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) { onDelete?.invoke(item) }
            )
        }
    }
}