package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farmer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditLand(farmName: String, farmAddress: String, backgroundColor: Color, onEdit: () -> Unit) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    val imageShape = LocalShapes.current.topRoundedSmallShape
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .border(width = .3.dp, color = Color.LightGray, shape = shape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = backgroundColor.copy(alpha = .2f), shape = imageShape)
            )

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.ic_sign_up_location_field_foreground),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(56.dp)
                )

                Chip(
                    modifier = Modifier.padding(top = spacing.extraSmall),
                    onClick = onEdit,
                    colors = ChipDefaults.chipColors(
                        backgroundColor = Color.Cameron,
                        contentColor = Color.White
                    ),
                    shape = CircleShape
                ) {
                    Text(
                        text = str(id = R.string.edit_farm),
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }

        Text(
            text = farmName,
            color = backgroundColor,
            style = MaterialTheme.typography.body2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = spacing.small)
        )

        Text(
            text = farmAddress,
            color = Color.Gray,
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = spacing.small)
        )
    }
}