package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FlowRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun <T : Any> FilterOptions(
    modifier: Modifier = Modifier,
    title: String,
    options: List<T>,
    getText: (T) -> String?,
    selectedOptions: List<T>,
    onSelect: (T) -> Unit,
    onDelete: (T) -> Unit,
    itemBackgroundColor: Color = Color.White,
    itemContentColor: Color = Color.Black
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .padding(spacing.small)
            .fillMaxWidth()
    ) {

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier
                .padding(spacing.small)
        )

        LazyRow {
            items(selectedOptions) { item ->
                FilterChip(item = item, getText = { getText(item) }, onDelete = onDelete)
            }
        }

        Divider(thickness = .3.dp, color = Color.LightGray)

        FlowRow(verticalGap = spacing.tiny, horizontalGap = spacing.tiny) {
            options.forEach { item ->
                OptionChip(
                    item = item,
                    getName = { getText(item) },
                    onSelect = onSelect,
                    backgroundColor = itemBackgroundColor,
                    contentColor = itemContentColor
                )
            }
        }
    }
}