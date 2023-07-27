package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SimpleChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components.PriceShimmerItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import com.taomish.app.android.farmsanta.farmer.models.api.home.Price
import com.taomish.app.android.farmsanta.farmer.utils.format


@Composable
fun MarketPrices(
    getPrices: () -> List<Price>?,
    getIsSelected: (Price) -> Boolean,
    onSelect: (Price) -> Unit
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val prices = getPrices()

    if (prices == null) {
        LazyRow(modifier = Modifier.padding(spacing.small)) {
            items(5) {
                PriceShimmerItem()
            }
        }
        return
    }

    if (prices.isEmpty()) {
        Text(
            text = str(id = R.string.no_prices_msg),
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2
        )
        return
    }

    LazyRow(modifier = Modifier.padding(spacing.small)) {
        items(prices) { price ->
            SimpleChip(title = price.format(context),
                backgroundColor = if (getIsSelected(price)) Color.Cameron else Color.RiceFlower,
                contentColor = if (getIsSelected(price)) Color.White else Color.Cameron,
                onClick = { onSelect(price) }
            )
        }
    }
}