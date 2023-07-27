package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SVG
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FertilizerCalculatorBanner(onNavigationItemClicked: (Screen) -> Unit) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    val brush = Brush.horizontalGradient(listOf(Color.Bahia, Color.Limeade, Color.Apple))
    Row(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .background(brush = brush, shape = shape),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        SVG(
            modifier = Modifier.wrapContentSize(),
            fileName = R.raw.home_calculate_fertilizer_icon
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {

            Text(
                text = str(id = R.string.fertilizer_screen_title),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.overline
            )

            Chip(
                onClick = { onNavigationItemClicked(Screen.FertilizerCalculator) },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Limeade
                )
            ) {
                Text(
                    text = str(id = R.string.calculate_fertilizer),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}