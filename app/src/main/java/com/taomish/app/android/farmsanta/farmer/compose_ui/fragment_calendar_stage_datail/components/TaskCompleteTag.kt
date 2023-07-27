package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun TaskCompleteTag(modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    val brush = Brush.verticalGradient(listOf(Color.Green, Color.Green.copy(alpha = .6f)))
    Row(
        modifier = modifier
            .background(brush = brush, shape = CircleShape)
            .padding(spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_like_thumb_up),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Text(
            text = str(id = R.string.task_complete),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(horizontal = spacing.extraSmall)
        )
    }
}