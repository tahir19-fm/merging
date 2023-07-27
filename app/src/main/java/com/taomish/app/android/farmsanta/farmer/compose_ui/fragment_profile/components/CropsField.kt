package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Chip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ChipIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FlowRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Crop


@Composable
fun CropsField(crops: List<Crop>) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    var size by remember { mutableStateOf(Size.Zero) }
    val height: @Composable () -> Dp = { with(LocalDensity.current) { size.height.toDp() } }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
            .background(color = Color.Cameron.copy(alpha = 0.1f), shape = shape)
            .onGloballyPositioned { size = it.size.toSize() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_profile_location),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = spacing.large, horizontal = spacing.small)
                .size(32.dp),
            tint = Color.Unspecified
        )

        Divider(
            color = Color.Gray,
            modifier = Modifier
                .height(height() - spacing.small)
                .width(1.dp)
        )

        if (crops.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.padding(
                    vertical = spacing.medium,
                    horizontal = spacing.small
                )
            ) {
                for (crop in crops) {
                    Chip(
                        text = crop.cropName,
                        backgroundColor = Color.White.copy(alpha = .7f),
                        trailingIcon = {
                            ChipIcon(
                                iconId = R.drawable.ic_crop,
                                backgroundColor = Color.White,
                                backgroundShape = CircleShape,
                                iconColor = Color.Unspecified,
                                size = 32.dp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color.Cameron
                            )
                        }
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height()),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.has_no_crop),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2,
                    color = Color.Cameron
                )
            }
        }
    }
}