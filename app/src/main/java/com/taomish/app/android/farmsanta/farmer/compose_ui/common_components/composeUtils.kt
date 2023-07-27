package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource


@Composable
fun str(@StringRes id: Int): String {
    return stringResource(id = id)
}



@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.onTap(action: () -> Unit) : Modifier = composed {
    pointerInput(Unit) {
        detectTapGestures(onTap = { action() })
    }
}