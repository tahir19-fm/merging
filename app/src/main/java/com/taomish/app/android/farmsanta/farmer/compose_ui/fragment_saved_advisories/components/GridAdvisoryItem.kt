package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_advisories.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Chip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ProfileImageWithName
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory


@Composable
fun GridAdvisoryItem(
    advisory: CropAdvisory,
    cropName: String,
    advisoryTag: String,
    onAdvisoryClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .height(224.dp)
            .padding(spacing.extraSmall)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onAdvisoryClicked
            )
    ) {
        val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                advisory.photos
                    ?.elementAtOrNull(0)
                    ?.fileName.toString()

        RemoteImage(
            modifier = Modifier.fillMaxSize(),
            imageLink = imageLink,
            contentScale = ContentScale.FillBounds,
            error = R.mipmap.img_default_pop
        )

        //TODO: CropAdvisory Object Should Have Profile Image Field from API Response.
        ProfileImageWithName(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = spacing.extraSmall, top = spacing.extraSmall),
            name = "${advisory.firstName} ${advisory.lastName}",
            profileImageLink = URLConstants.S3_IMAGE_BASE_URL /* + advisory.profileImage */,
            textStyle = MaterialTheme.typography.caption,
            textColor = Color.White,
            imageSize = 24.dp
        )

        Chip(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = spacing.extraSmall, end = spacing.extraSmall)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    onClick = onAdvisoryClicked
                ),
            text = "${str(id = R.string.plant)}: $cropName",
            textStyle = MaterialTheme.typography.overline,
            textPadding = spacing.tiny,
            backgroundColor = Color.White.copy(alpha = .4f)
        )

        Text(
            text = "${R.string.advisory} : $advisoryTag",
            style = MaterialTheme.typography.caption,
            color = Color.White,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(color = Color.Cameron, shape = RectangleShape)
                .padding(spacing.small)
        )
    }
}