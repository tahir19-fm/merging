package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun IPMSection(
    modifier: Modifier = Modifier,
    title: AnnotatedString,
    imageLink: String,
    onClick: () -> Unit,
    @DrawableRes error: Int = R.mipmap.fall_armyworm_spodoptera_frugiperda,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .height(200.dp)
            .background(color = Color.Cameron, shape = RectangleShape)
            .clip(RectangleShape)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RemoteImage(
            modifier = Modifier
                .fillMaxSize(.7f)
                .aspectRatio(1F)
                .clip(CircleShape),
            imageLink = imageLink,
            contentScale = ContentScale.FillBounds,
            error = error
        )

        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(spacing.extraSmall)
        )
    }
}