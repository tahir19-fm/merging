package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun ExpandableRow(
    expanded: MutableState<Boolean>,
    title: String?,
    description: String?
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.extraSmall)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = spacing.small, end = spacing.small, top = spacing.small)
                .background(color = Color.Cameron, shape = RectangleShape)
                .padding(spacing.small)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource()
                ) { expanded.value = !expanded.value },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title ?: "-", color = Color.White, style = MaterialTheme.typography.body2)
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        AnimatedVisibility(
            visible = expanded.value,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Text(
                text = (description ?: "").ifEmpty { str(id = R.string.description_not_available) },
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.small)
                    .border(width = .8.dp, color = Color.Cameron, shape = RectangleShape)
                    .padding(spacing.small),
                textAlign = TextAlign.Justify
            )
        }
    }
}