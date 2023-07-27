package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun MapButton(
    modifier: Modifier = Modifier,
    @StringRes titleId: Int,
    @DrawableRes iconId: Int,
    tint: Color,
    onClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier.padding(spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = str(id = titleId),
            style = MaterialTheme.typography.body2.copy(
                color = tint,
                fontWeight = FontWeight.Bold
            )
        )

        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clip(CircleShape)
                .background(color = Color.White, shape = CircleShape)
                .padding(spacing.extraSmall)
                .background(color = tint, shape = CircleShape)
                .padding(spacing.extraSmall)
                .clickable { onClick() }
        )

    }
}