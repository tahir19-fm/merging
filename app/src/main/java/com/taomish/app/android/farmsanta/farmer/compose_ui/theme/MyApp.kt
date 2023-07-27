package com.taomish.app.android.farmsanta.farmer.compose_ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.onTap


@Composable
fun MyApp(content: @Composable () -> Unit) {
    val focusManager = LocalFocusManager.current
    NewUiDesignTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .onTap { focusManager.clearFocus() },
            color = Color.Transparent,
            content = content
        )
    }
}