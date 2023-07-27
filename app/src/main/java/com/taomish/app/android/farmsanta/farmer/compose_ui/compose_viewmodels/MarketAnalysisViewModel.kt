package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.models.api.home.MarketDto
import com.taomish.app.android.farmsanta.farmer.models.api.home.Price
import com.taomish.app.android.farmsanta.farmer.utils.MarketDataFormatter.getXValuesForMonth
import com.taomish.app.android.farmsanta.farmer.utils.MarketDataFormatter.getXValuesForWeek
import com.taomish.app.android.farmsanta.farmer.utils.MarketDataFormatter.getXValuesForYears
import com.taomish.app.android.farmsanta.farmer.utils.MarketDataFormatter.getYValues
import com.taomish.app.android.farmsanta.farmer.utils.postValue

@SuppressLint("MutableCollectionMutableState")
class MarketAnalysisViewModel : ViewModel() {

    val selectedPrice = mutableStateOf<Price?>(null)
    val commodities = listOf("Crop", "Seed", "Plant")
    val crops = listOf("Rice", "Wheat", "Corn", "Banana", "Mango")
    var marketData by mutableStateOf<MarketDto?>(null)
    private val xValues = mutableStateOf<List<String>>(emptyList())
    private val yValues = mutableStateOf<List<Float>>(emptyList())
    private val pointsValue = mutableStateOf<List<Float>>(emptyList())
    private val periods = listOf("1 week", "1 month", "1 year", "5 year")
    val periodLabels = listOf("1W", "1M", "1Y", "5Y")
    val graphTypes = listOf(
        Pair(R.drawable.ic_graph, "Line"),
        Pair(R.drawable.ic_graph, "Graph"),
        Pair(R.drawable.ic_graph, "Chart")
    )

    val selectedGraph = mutableStateOf(graphTypes[0])
    val searchText = mutableStateOf("")
    val selectedPeriodIndex = mutableStateOf(0)
    val selectedCommodity = mutableStateOf("Select Here")
    val commodityExpanded = mutableStateOf(false)
    val selectedCrop = mutableStateOf("Select Product")
    val cropExpanded = mutableStateOf(false)
    val prices = mutableStateOf<ArrayList<Price>?>(null)

    fun getPrices(): ArrayList<Price>? = prices.value
    fun getXValues(): List<String> = xValues.value
    fun getYValues(): List<Float> = yValues.value
    fun getPointsValues(): List<Float> = pointsValue.value

    fun setXAndYValues() {
        when (selectedPeriodIndex.value) {
            0 -> xValues.postValue(marketData?.list?.getXValuesForWeek().orEmpty())
            1 -> xValues.postValue(marketData?.list?.getXValuesForMonth().orEmpty())
            2, 3 -> xValues.postValue(marketData?.list?.getXValuesForYears().orEmpty())
        }
        yValues.postValue(marketData?.list?.getYValues().orEmpty())
        pointsValue.postValue(
            marketData?.list?.map { it.price?.value?.toFloat() ?: 0F }.orEmpty()
        )
        Log.d(this.javaClass.name.take(15), "setXAndYVales: ${pointsValue.value}")
    }

    val selectedPeriod: String
        get() = periods[selectedPeriodIndex.value]

}