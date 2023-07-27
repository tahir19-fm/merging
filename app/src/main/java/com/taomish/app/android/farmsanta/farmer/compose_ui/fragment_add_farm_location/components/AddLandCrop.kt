package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farm_location.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster


@Composable
fun AddLandCrop(
    crop: CropMaster,
    onClick: (CropMaster) -> Unit
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(spacing.extraSmall)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = { crop.let(onClick) }
            )
    ) {

        val image = URLConstants.S3_IMAGE_BASE_URL + crop.photos
            ?.elementAtOrNull(0)
            ?.fileName.toString()

        RemoteImage(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(spacing.extraSmall)
                .fillMaxSize()
                .clip(CircleShape)
                .padding(bottom = spacing.small),
            imageLink = image,
            contentScale = ContentScale.Crop,
            error = R.mipmap.img_default_pop
        )

        Text(
            text = crop.cropName ?: "N/A",
            color = Color.Cameron,
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .border(
                    width = .5.dp,
                    color = Color.Cameron,
                    shape = CircleShape
                )
                .background(color = Color.White, shape = CircleShape)
                .padding(spacing.extraSmall)
        )
    }
}