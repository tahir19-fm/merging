package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.components.AnalysisChart
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.components.MarketPrices
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.components.PeriodsRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun MarketAnalysisGraph(
    viewModel: HomeViewModel,
    onNavigationItemClicked: (Screen) -> Unit,
    fetchMarketData: () -> Unit
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .background(color = Color.LightGray.copy(alpha = .3f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .padding(vertical = spacing.extraSmall)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*FarmerTextField(
                text = { text.value },
                onValueChange = { text.postValue(it) },
                modifier = Modifier
                    .padding(spacing.small)
                    .fillMaxWidth(.85f),
                backgroundColor = Color.White,
                trailingIcon = { SearchLeadingIcon() },
                placeholderText = str(id = R.string.search_markets),
                textStyle = MaterialTheme.typography.caption,
                shape = CircleShape,
                hasFocus = hasFocus
            )*/

            MarketPrices(
                getPrices = viewModel::getPrices,
                getIsSelected = { it.uuid == viewModel.selectedPrice.value?.uuid },
                onSelect = {
                    if (viewModel.selectedPrice.value?.uuid != it.uuid) {
                        viewModel.selectedPrice.postValue(it)
                        fetchMarketData()
                    }
                }
            )


            /*Icon(
                painter = painterResource(id = R.drawable.ic_add_green_new),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(start = spacing.extraSmall, end = spacing.small)
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { }
            )*/
        }

        PeriodsRow(
            periods = viewModel.periodLabels,
            selectedIndex = viewModel.selectedPeriodIndex.value,
            onSelect = {
                if (viewModel.selectedPeriodIndex.value != it) {
                    viewModel.selectedPeriodIndex.postValue(it)
                    fetchMarketData()
                }
            }
        )


        AnalysisChart(
            price = viewModel.selectedPrice.value,
            getPointsValues = viewModel::getPointsValues,
            getXValues = viewModel::getXValues,
            getYValues = viewModel::getYValues
        )

        RoundedShapeButton(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .padding(vertical = spacing.small),
            text = str(id = R.string.view_more),
            textPadding = spacing.extraSmall,
            onClick = { onNavigationItemClicked(Screen.MarketAnalysis) }
        )
    }
}