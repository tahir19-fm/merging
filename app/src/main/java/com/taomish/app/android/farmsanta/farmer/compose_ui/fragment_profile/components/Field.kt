package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun Field(
    metaData: String,
    value: String,
    @DrawableRes iconId: Int
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    var size by remember { mutableStateOf(Size.Zero) }
    val height: @Composable () -> Dp = { with(LocalDensity.current) { size.height.toDp() } }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
            .background(color = Color.Cameron.copy(alpha = 0.1f), shape = shape)
            .onGloballyPositioned { size = it.size.toSize() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(horizontal = spacing.small)
                .size(32.dp)
        )

        Divider(
            color = Color.Gray,
            modifier = Modifier
                .height(height() - spacing.small)
                .width(1.dp)
        )

        Column(
            modifier = Modifier
                .padding(spacing.small)
        ) {
            Text(
                text = metaData,
                color = Color.Gray,
                style = MaterialTheme.typography.caption
            )

            Text(
                text = value,
                color = Color.Cameron,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2
            )
        }
    }
}