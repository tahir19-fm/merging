package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun SearchLeadingIcon() {
    Icon(
        imageVector = Icons.Filled.Search,
        contentDescription = null,
        tint = Color.LightGray
    )
}