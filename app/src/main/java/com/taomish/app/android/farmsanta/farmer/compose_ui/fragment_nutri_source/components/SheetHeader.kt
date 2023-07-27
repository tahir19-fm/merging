package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_nutri_source.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun SheetHeader(onCloseClicked: () -> Unit) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = str(id = R.string.filters),
            color = Color.Cameron,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2
        )

        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = null,
            tint = Color.Cameron,
            modifier = Modifier
                .size(24.dp)
                .border(width = .5.dp, color = Color.Cameron, shape = CircleShape)
                .padding(spacing.extraSmall)
                .clip(CircleShape)
                .clickable(onClick = onCloseClicked)
        )
    }
}