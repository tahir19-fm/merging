package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun FertilizerTableRow(
    fertilizer: String,
    quantity: String,
    unit: String,
    textStyle: TextStyle = MaterialTheme.typography.overline
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = fertilizer,
            style = textStyle,
            color = Color.Limeade,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(.6f)
                .padding(spacing.extraSmall)
        )

        Text(
            text = quantity,
            style = textStyle,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(.6f)
        )

        Text(
            text = unit,
            style = textStyle,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = spacing.extraSmall)
        )
    }
}