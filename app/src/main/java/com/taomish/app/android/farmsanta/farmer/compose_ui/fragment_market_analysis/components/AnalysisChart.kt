package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CurveLineGraph
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import com.taomish.app.android.farmsanta.farmer.models.api.home.Price


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnalysisChart(
    price: Price?,
    getPointsValues: () -> List<Float>,
    getXValues: () -> List<String>,
    getYValues: () -> List<Float>
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.RiceFlower.copy(alpha = .6f)),
        horizontalAlignment = Alignment.End
    ) {

        Chip(
            modifier = Modifier.padding(spacing.small),
            onClick = { },
            colors = ChipDefaults.chipColors(
                backgroundColor = Color.Cameron,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "${price?.currentPrice?.currency ?: "INR"} / " +
                        ((price?.packaging?.unit?.toInt() ?: 0).toString() +
                                (price?.packaging?.uom ?: "kg")),
                style = MaterialTheme.typography.caption
            )
        }

        val points = getPointsValues()

        val modifier = if (points.size > 7)
            Modifier
                .horizontalScroll(rememberScrollState())
                .width(((328 / 7) * points.size).dp)
        else
            Modifier.fillMaxWidth()


        CurveLineGraph(
            modifier = modifier
                .padding(vertical = spacing.small, horizontal = spacing.medium)
                .height(300.dp),
            points = getPointsValues(),
            xValues = getXValues(),
            yValues = getYValues()
        )

    }
}