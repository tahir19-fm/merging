package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farmer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OrangePeel
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster


@Composable
fun AddCropCircleCrop(
    crop: CropMaster,
    isSelected: Boolean,
    selectedColor: Color = Color.OrangePeel,
    unselectedColor: Color = Color.White,
    selectedContentColor: Color = Color.White,
    unselectedContentColor: Color = Color.Cameron,
    showClose: Boolean = true,
    onCloseClicked: ((CropMaster) -> Unit)? = null,
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(spacing.extraSmall)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onClick
            )
    ) {
        val url = URLConstants.S3_IMAGE_BASE_URL + crop.photos
                            ?.elementAtOrNull(0)
                            ?.fileName.toString()

        RemoteImage(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(spacing.extraSmall)
                .fillMaxSize()
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (isSelected) selectedColor else unselectedColor,
                    shape = CircleShape
                )
                .padding(bottom = spacing.small),
            imageLink = url,
            contentScale = ContentScale.Crop,
            error = R.mipmap.img_default_pop
        )

        Text(
            text = crop.cropName ?: "N/A",
            color = if (isSelected) selectedContentColor else unselectedContentColor,
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .border(
                    width = .5.dp,
                    color = if (isSelected) selectedColor else unselectedColor,
                    shape = CircleShape
                )
                .background(
                    color = if (isSelected) selectedColor else unselectedColor,
                    shape = CircleShape
                )
                .padding(horizontal = spacing.small, vertical = spacing.extraSmall)
        )

        if (showClose) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color.White, shape = CircleShape)
                    .border(width = .3.dp, color = Color.Cameron, shape = CircleShape)
                    .padding(spacing.extraSmall)
                    .size(12.dp)
                    .clip(CircleShape)
                    .clickable { onCloseClicked?.invoke(crop) }
            )
        }
    }
}