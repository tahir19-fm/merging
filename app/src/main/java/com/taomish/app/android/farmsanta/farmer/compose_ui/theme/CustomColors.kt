package com.taomish.app.android.farmsanta.farmer.compose_ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

class CustomColors (
    val nyanza: Color = Color(0xFFDDFFCC)
)

val LocalCustomColors = compositionLocalOf { CustomColors() }

