package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SearchLeadingIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.MarketAnalysisViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.components.AnalysisChart
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.components.MarketPrices
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.components.PeriodsRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Bahia
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun MarketAnalysisFragmentScreen(viewModel: MarketAnalysisViewModel, fetchMarketData: () -> Unit) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    BackHandler(hasFocus.value) { focusManager.clearFocus() }
    Column(modifier = Modifier) {
        CommonTopBar(
            activity = context as AppCompatActivity,
            isAddRequired = false,
            title = str(id = R.string.market_analysis),
            addClick = {}
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FarmerTextField(
                    modifier = Modifier.fillMaxWidth(.85f),
                    text = { viewModel.searchText.value },
                    onValueChange = { viewModel.searchText.postValue(it) },
                    placeholderText = str(id = R.string.search),
                    shape = CircleShape,
                    leadingIcon = { SearchLeadingIcon() },
                    hasFocus = hasFocus,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {})
                )

                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { },
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = null,
                    tint = Color.LightGray.copy(alpha = .5f)
                )
            }

            /*Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {*/
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

            /*CustomDropDown(
                selected = viewModel.selectedGraph,
                items = viewModel.graphTypes
            )
        }*/


            AnalysisChart(
                price = viewModel.selectedPrice.value,
                getPointsValues = viewModel::getPointsValues,
                getXValues = viewModel::getXValues,
                getYValues = viewModel::getYValues
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            if (viewModel.selectedCrop.value == str(id = R.string.select_product)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.small)
                        .background(color = Color.LightGray.copy(alpha = .2f), shape = shape)
                        .padding(spacing.medium)
                ) {
                    Text(
                        text = "${str(id = R.string.current_commodity_rate)}: ",
                        color = Color.Gray,
                        style = MaterialTheme.typography.body2
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing.medium, horizontal = spacing.small),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${viewModel.selectedCrop.value} ${str(id = R.string.price)} : ",
                        color = Color.Cameron,
                        style = MaterialTheme.typography.body2
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
            }
        }
    }
}