package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


@Composable
fun CultivarTag(text: String) {
    Text(
        text = text,
        color = Color.Gray,
        style = MaterialTheme.typography.body1,
        fontWeight = FontWeight.Bold
    )
}