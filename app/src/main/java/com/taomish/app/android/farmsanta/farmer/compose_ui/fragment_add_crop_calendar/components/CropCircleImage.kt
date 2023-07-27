package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster


@Composable
fun CropCircleImage(crop: CropMaster, isSelected: Boolean, onSelect: ((CropMaster) -> Unit)? = null) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = { onSelect?.invoke(crop) }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(64.dp)) {
            val imageUrl = URLConstants.S3_IMAGE_BASE_URL + crop.photos
                ?.elementAtOrNull(0)
                ?.fileName.toString()
            RemoteImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                imageLink = imageUrl,
                contentScale = ContentScale.Crop,
                error = R.mipmap.img_default_pop
            )

            if (isSelected) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_done_outline),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp)
                        .border(width = .7.dp, color = Color.White, shape = CircleShape)
                        .padding(spacing.small)
                )
            }
        }

        Card(
            elevation = 1.dp,
            backgroundColor = if (isSelected) Color.Cameron else Color.RiceFlower,
            shape = CircleShape,
            modifier = Modifier.padding(top = spacing.small)
        ) {
            Text(
                text = crop.cropName ?: "",
                color = if (isSelected) Color.White else Color.Cameron,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(horizontal = spacing.small)
            )
        }
    }
}