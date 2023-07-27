package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CropChip(
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.caption,
    @DrawableRes leadingIconId: Int? = null,
    leadingIconSize: Dp = 16.dp,
    leadingIconColor: Color = Color.White,
    @DrawableRes trailingIconId: Int? = null,
    trailingIconSize: Dp = 16.dp,
    trailingIconColor: Color = Color.White,
    onClickTrailingIcon: (() -> Unit)? = null,
    backgroundColor: Color = Color.Cameron,
    contentColor: Color = Color.White,
    onClick: () -> Unit = { }
) {
    val spacing = LocalSpacing.current
    val chipColors = ChipDefaults.chipColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        leadingIconContentColor = leadingIconColor
    )

    Chip(
        modifier = Modifier.padding(spacing.extraSmall),
        onClick = onClick,
        colors = chipColors,
        leadingIcon = if (leadingIconId != null) {
            {
                Icon(
                    painter = painterResource(id = leadingIconId),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = spacing.extraSmall)
                        .size(leadingIconSize)
                )
            }
        } else null,
        border = BorderStroke(width = .3.dp, color = Color.Cameron)
    ) {
        Text(text = title, style = titleStyle)
        trailingIconId?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = null,
                tint = trailingIconColor,
                modifier = Modifier
                    .size(trailingIconSize)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) { onClickTrailingIcon?.invoke() }
            )
        }
    }
}