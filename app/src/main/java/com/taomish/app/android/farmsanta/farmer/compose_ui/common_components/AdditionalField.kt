package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower


@Composable
fun AdditionalField(description: String?, backgroundColor: Color = Color.RiceFlower) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = spacing.small, end = spacing.extraSmall)
            .background(color = backgroundColor, shape = shape),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = description?.ifEmpty { "-" } ?: "-",
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth()
        )
    }
}