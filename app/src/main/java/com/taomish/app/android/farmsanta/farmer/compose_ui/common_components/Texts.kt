package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron


@Composable
fun MetaText(text: String) {
    Text(
        text = text,
        color = Color.Cameron,
        style = MaterialTheme.typography.body2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ValueText(text: String) {
    Text(
        text = text,
        color = Color.Gray,
        style = MaterialTheme.typography.body2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun HeadingText(modifier: Modifier = Modifier, text: String, color: Color) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        color = color,
        style = MaterialTheme.typography.h6,
        modifier = modifier
    )
}