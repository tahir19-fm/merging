package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view.components

import androidx.annotation.Keep
import androidx.compose.runtime.Stable

@Keep
@Stable
data class ImmutableList<out T>(val list: List<T>) : List<T> by list