package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Chip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OrangePeel


@Composable
fun Agronomist() {
    val spacing = LocalSpacing.current
    /*Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_ask_query),
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = "Ask Query",
                color = Color.Cameron,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = spacing.small)
            )
        }*/

        Chip(
            modifier = Modifier
                .padding(vertical = spacing.small),
            text = str(id = R.string.agronomist),
            textStyle = MaterialTheme.typography.caption,
            textPadding = spacing.small,
            backgroundColor = Color.OrangePeel
        )
//    }
}