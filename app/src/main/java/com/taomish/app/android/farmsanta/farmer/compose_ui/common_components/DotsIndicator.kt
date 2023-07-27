package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = Color.Cameron,
    unSelectedColor: Color = Color.White.copy(alpha = .8f),
) {
    val spacing = LocalSpacing.current

    val activeColor: Color by animateColorAsState(
        targetValue = selectedColor,
        animationSpec = tween(
            durationMillis = 500,
        )
    )

    val inactiveColor: Color by animateColorAsState(
        targetValue = unSelectedColor,
        animationSpec = tween(
            durationMillis = 300,
        )
    )

    val activeSize by animateDpAsState(targetValue = 12.dp, animationSpec = tween(
        durationMillis = 500,
    ))
    val inactiveSize by animateDpAsState(targetValue = 10.dp, animationSpec = tween(
        durationMillis = 500,
    ))

    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        items(totalDots) { index ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .size(if (isSelected) activeSize else inactiveSize)
                    .clip(CircleShape)
                    .background(if (isSelected) activeColor else inactiveColor)
            )
            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = spacing.extraSmall))
            }
        }
    }
}