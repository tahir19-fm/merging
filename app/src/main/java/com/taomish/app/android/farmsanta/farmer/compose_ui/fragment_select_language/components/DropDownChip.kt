package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_select_language.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownChip(text: String, onDelete: () -> Unit) {
    FilterChip(
        selected = true,
        onClick = { },
        colors = ChipDefaults.filterChipColors(
            selectedBackgroundColor = Color.RiceFlower,
            selectedContentColor = Color.Cameron,
            selectedLeadingIconColor = Color.Red
        ),
        leadingIcon = {
            Icon(imageVector = Icons.Filled.PinDrop, contentDescription = "Pin")
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Pin",
                modifier = Modifier.clickable(
                    indication = rememberRipple(
                        bounded = false,
                        color = Color.Cameron.copy(alpha = .2f)
                    ),
                    interactionSource = MutableInteractionSource(),
                    onClick = onDelete
                )
            )
        }
    ) {
        Text(text = text, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.caption)
    }
}