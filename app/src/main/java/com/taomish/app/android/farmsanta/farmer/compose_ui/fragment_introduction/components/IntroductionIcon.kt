package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_introduction.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R


@Composable
fun IntroductionIcon(modifier: Modifier = Modifier, @DrawableRes iconId: Int) {
    Box(
        modifier = modifier.size(64.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_ellipse),
            contentDescription = "",
            modifier = Modifier
                .matchParentSize()
                .padding(8.dp),
            alignment = Alignment.Center
        )
        Image(
            painter = painterResource(id = R.drawable.bg_ellipse),
            contentDescription = "",
            modifier = Modifier
                .matchParentSize()
                .padding(14.dp)
                .alpha(0.8f),
            alignment = Alignment.Center
        )

        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "Introduction Icon",
            modifier = Modifier
                .align(Alignment.Center)
                .matchParentSize()
                .padding(8.dp),
            tint = Color.White
        )
    }
}