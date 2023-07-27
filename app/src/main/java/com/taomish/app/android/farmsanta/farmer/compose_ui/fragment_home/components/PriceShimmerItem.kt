package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.shimmerEffect
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun PriceShimmerItem() {
    val spacing = LocalSpacing.current
    Spacer(modifier = Modifier
        .padding(spacing.small)
        .width(192.dp)
        .height(32.dp)
        .clip(CircleShape)
        .shimmerEffect()
    )
}