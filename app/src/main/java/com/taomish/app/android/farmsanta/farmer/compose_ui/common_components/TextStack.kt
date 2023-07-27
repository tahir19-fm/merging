package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


@Composable
fun TextStack(
    heading: String,
    value: Int
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$value",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6,
            color = Color.Gray
        )
        Text(text = heading, style = MaterialTheme.typography.caption, color = Color.Gray)
    }
}