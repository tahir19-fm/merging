package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

@Composable
fun HorizontalSlideAnimation( content: @Composable () -> Unit ){
    val visibility = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(visibility){
        delay(500)
        visibility.value=true
    }
    val slowEasing = CubicBezierEasing(0.2f, 0.0f, 0.2f, 1.0f)
    AnimatedVisibility(
        visible = visibility.value,
        enter = slideInHorizontally(
            initialOffsetX = { 300 },
            animationSpec = tween(durationMillis = 1500, easing = slowEasing)
        )
    ){
        content()
    }
}