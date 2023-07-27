package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron


@Composable
fun MetaAndValueTextRow(
    modifier: Modifier = Modifier,
    metaText: String,
    valueText: String,
    metaTextColor: Color = Color.Gray,
    valueTextColor: Color = Color.Cameron,
    textStyle: TextStyle = MaterialTheme.typography.caption
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$metaText: ",
            color = metaTextColor,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = valueText,
            color = valueTextColor,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}