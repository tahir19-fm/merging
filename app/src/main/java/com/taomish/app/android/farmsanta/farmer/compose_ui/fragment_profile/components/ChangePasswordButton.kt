package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun ChangePasswordButton(onClick: () -> Unit) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    var size by remember { mutableStateOf(Size.Zero) }
    val height: @Composable () -> Dp = { with(LocalDensity.current) { size.height.toDp() } }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacing.small)
            .background(color = Color.Cameron.copy(alpha = 0.1f), shape = shape)
            .border(width = 1.dp, color = Color.Cameron, shape = shape)
            .onGloballyPositioned { size = it.size.toSize() }
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier.padding(start = spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_profile_news),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(
                        start = spacing.small,
                        end = spacing.small.plus(spacing.extraSmall),
                        top = spacing.small.plus(spacing.extraSmall),
                        bottom = spacing.small.plus(spacing.extraSmall)
                    )
                    .size(32.dp)
            )

            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .height(height() - spacing.small)
                    .width(1.dp)
            )


            Text(
                text = str(id = R.string.change_password),
                color = Color.Cameron,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = spacing.small)
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_forward),
            contentDescription = null,
            tint = Color.Cameron,
            modifier = Modifier
                .size(24.dp)
                .padding(end = spacing.small)
        )
    }
}