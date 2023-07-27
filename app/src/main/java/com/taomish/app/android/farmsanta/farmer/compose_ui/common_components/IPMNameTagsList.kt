package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun <T : Any> IPMNameTagsList(
    list: List<T?>,
    getName: (T) -> String?,
    selected: T?,
    onSelect: (T) -> Unit
) {
    val spacing = LocalSpacing.current
    LazyRow(modifier = Modifier.padding(spacing.small)) {
        items(list) { item ->
            item?.let {
                SelectableChip(
                    text = getName(item) ?: "",
                    isSelected = selected === item,
                    selectedBackgroundColor = Color.Cameron,
                    onClick = { onSelect(item) }
                )
            }
        }
    }
}