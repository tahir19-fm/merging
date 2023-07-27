package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrendRow(
    modifier: Modifier = Modifier,
    trending: List<String>?,
    onTrendSelected: (String) -> Unit? = {  }
) {
    val spacing = LocalSpacing.current
    var selectedTrend by remember{ mutableStateOf("") }

    Row(
        modifier = modifier.padding(start = spacing.small, end = spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (trending != null) "${stringResource(id = R.string.trending)} :" else str(id = R.string.no_trends),
            color = Color.Cameron,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2
        )

        if (trending != null) {
            LazyRow(modifier = Modifier.padding(spacing.small)) {
                items(trending) { trend ->
                    val isSelected = selectedTrend == trend
                    Chip(
                        modifier = Modifier.padding(horizontal = spacing.extraSmall),
                        onClick = {
                            selectedTrend = if (selectedTrend == trend) "" else trend
                            onTrendSelected(selectedTrend)
                        },
                        shape = CircleShape,
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (!isSelected) Color.LightGray.copy(alpha = .2f) else Color.Cameron,
                            contentColor = if (!isSelected) Color.Gray else Color.White
                        )
                    ) {
                        Text(text = trend, style = MaterialTheme.typography.caption)
                    }
                }
            }
        }
    }
}