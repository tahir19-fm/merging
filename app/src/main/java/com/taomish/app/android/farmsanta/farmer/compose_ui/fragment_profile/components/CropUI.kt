package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CloseIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Crop


@Composable
fun CropUI(
    crop: Crop,
    name: String,
    showClose: Boolean = true,
    onSelect: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .padding(spacing.extraSmall)
            .background(color = Color.Cameron.copy(alpha = 0.2f), shape = CircleShape)
            .padding(spacing.small)
            .clip(CircleShape)
            .clickable { onSelect?.invoke() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_crop),
            contentDescription = null,
            modifier = Modifier
                .padding(end = spacing.small)
                .size(16.dp),
            tint = Color.Unspecified
        )

        Text(
            text = name /* crop.cropName */,
            color = Color.Cameron,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.caption
        )

        if (showClose) {
            CloseIcon { onDelete?.invoke() }
        }
    }
}