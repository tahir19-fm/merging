package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.AutoScrollingLazyRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CropChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.home.Price
import com.taomish.app.android.farmsanta.farmer.utils.format


@Composable
fun Prices(
    getPrices: () -> List<Price>?,
    backgroundColor: Color = Color.Cameron,
    contentColor: Color = Color.White
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
    } else {
        if (prices.isEmpty()) {
            Text(
                text = str(id = R.string.no_prices_msg),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )
        } else {
            AutoScrollingLazyRow(list = prices) { price ->
                val priceTag = price.format(context)
                CropChip(
                    title = priceTag,
                    backgroundColor = backgroundColor,
                    contentColor = contentColor
                )
            }
        }
    }
}