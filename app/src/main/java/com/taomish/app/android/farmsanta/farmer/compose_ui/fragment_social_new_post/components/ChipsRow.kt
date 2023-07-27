package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_new_post.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Chip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CloseIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower


@Composable
fun ChipsRow(list: List<String>, onClickClose: (String) -> Unit) {
    val spacing = LocalSpacing.current
    LazyRow(modifier = Modifier.padding(vertical = spacing.small)) {
        items(list) { item ->
            Chip(
                modifier = Modifier
                    .padding(end = spacing.small)
                    .border(
                        width = 1.dp,
                        color = Color.Cameron,
                        shape = CircleShape
                    ),
                text = item,
                textColor = Color.Cameron,
                textStyle = MaterialTheme.typography.caption,
                backgroundColor = Color.RiceFlower,
                trailingIcon = {
                    CloseIcon(
                        iconSize = 16.dp,
                        iconColor = Color.Cameron,
                        onClick = { onClickClose(item) }
                    )
                }
            )
        }
    }
}