package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_crops.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.line.model.LineData
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.components.AnalysisChart
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Bahia
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun MyCropMarketAnalysis(
    chartData: List<LineData>,
    onSectionViewMoreClicked: (Screen) -> Unit
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val periods = listOf("1D", "1W", "1M", "1Y", "5Y")
    var selected by remember { mutableStateOf(periods[0]) }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Mango ${str(id = R.string.market_analysis)}",
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(spacing.small)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.small, vertical = spacing.extraSmall)
        ) {
            items(periods) { period ->
                Text(
                    text = period,
                    color = if (selected == period) Color.Cameron else Color.LightGray,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .padding(spacing.extraSmall)
                        .clickable(
                            indication = null,
                            interactionSource = MutableInteractionSource(),
                            onClick = { selected = period }
                        )
                )
            }
        }


        AnalysisChart(
            price = null,
            getPointsValues = { chartData.map { it.yValue } },
            getXValues = { listOf("6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 AM", "1 PM") },
            getYValues = { listOf(500F, 1000F, 1500F, 2000F, 2500F) }
        )

        Row(
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth()
                .border(width = .6.dp, color = Color.Cameron, shape = shape)
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mango Current Rate : 200/Kg",
                color = Color.Cameron,
                style = MaterialTheme.typography.caption
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${str(id = R.string.up_arrow)} 6% ${str(id = R.string.increase)}",
                    color = Color.Bahia,
                    style = MaterialTheme.typography.overline
                )

                Spacer(modifier = Modifier.height(spacing.small))

                Text(
                    text = str(id = R.string.from_previous_day),
                    color = Color.Cameron,
                    style = MaterialTheme.typography.overline
                )
            }
        }

        RoundedShapeButton(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .padding(vertical = spacing.small),
            text = str(id = R.string.view_more),
            textPadding = spacing.extraSmall,
            onClick = { onSectionViewMoreClicked(Screen.MarketAnalysis) }
        )
    }
}