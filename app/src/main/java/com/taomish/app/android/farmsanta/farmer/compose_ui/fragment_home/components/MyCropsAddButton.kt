package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import dashedBorder


@Composable
fun MyCropsAddButton(onClick: () -> Unit) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .padding(spacing.extraSmall)
            .size(80.dp)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onClick
            )
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(64.dp)
                .dashedBorder(
                    .8.dp,
                    color = Color.Cameron,
                    shape = CircleShape,
                    on = 6.dp,
                    off = 4.dp
                )
                .background(color = Color.White, shape = CircleShape)
                .padding(spacing.medium)
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(48.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_rounded_corner),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = Color.Limeade),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = str(id = R.string.add),
            color = Color.White,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = spacing.medium)
                .border(width = .5.dp, color = Color.Cameron, shape = CircleShape)
                .background(color = Color.Cameron, shape = CircleShape)
                .padding(horizontal = spacing.small, vertical = spacing.extraSmall)
        )
    }
}