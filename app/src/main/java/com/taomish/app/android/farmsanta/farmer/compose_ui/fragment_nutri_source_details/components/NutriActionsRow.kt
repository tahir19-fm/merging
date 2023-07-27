package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_nutri_source_details.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NutriActionsRow(name: String) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            color = Color.Cameron,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            Chip(
                onClick = {},
                shape = CircleShape,
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.Cameron,
                    contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Filled.Downloading, contentDescription = null)
                Text(
                    text = str(id = R.string.download_image),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = spacing.small)
                )
            }

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_share_paper_plane),
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier
                    .border(
                        width = .3.dp,
                        color = Color.Cameron,
                        shape = CircleShape
                    )
                    .padding(spacing.extraSmall)
            )
        }
    }
}