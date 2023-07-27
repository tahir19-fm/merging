package com.taomish.app.android.farmsanta.farmer.utils

import com.taomish.app.android.farmsanta.farmer.models.api.home.MarketData
import com.taomish.app.android.farmsanta.farmer.models.api.home.MarketDto
import com.taomish.app.android.farmsanta.farmer.models.api.home.PriceValue
import kotlin.math.pow
import kotlin.random.Random

object MarketDataFormatter {

    fun List<MarketData>.getXValuesForDay(): List<String> {
        val xValues = mutableListOf<String>()
        this.forEach {
            it.priceDate?.toLocalDateTime()?.let { dateTime ->
                xValues.add("${dateTime.hour}:${dateTime.minute}")
            }
        }
        return xValues
    }

    fun List<MarketData>.getXValuesForWeek(): List<String> {
        val xValues = mutableListOf<String>()
        this.forEach {
            it.priceDate?.toLocalDateTime()?.let { dateTime ->
                xValues.add(dateTime.dayOfWeek?.name?.take(3) ?: "")
            }
        }
        return xValues
    }

    fun List<MarketData>.getXValuesForMonth(): List<String> {
        val xValues = mutableListOf<String>()
        this.forEach {
            it.priceDate?.toLocalDateTime()?.let { dateTime ->
                xValues.add(dateTime.dayOfMonth.toString())
            }
        }
        return xValues
    }

    fun List<MarketData>.getXValuesForYears(): List<String> {
        val xValues = mutableListOf<String>()
        this.forEach {
            it.priceDate?.toLocalDateTime()?.let { dateTime ->
                xValues.add("${dateTime.dayOfMonth} ${dateTime.month.name.take(3)}")
            }
        }
        return xValues
    }

    fun List<MarketData>.getYValues(): List<Float> {
        var maxValue = this.maxOf { it.price?.value ?: 0.0 }
        val digits = maxValue.toInt().toString().length
        val divisor = (10.0).pow((digits - 1))
        if (divisor > 0.0) {
            val rem = maxValue % divisor
            maxValue = if (rem > 0) ((maxValue - rem) + divisor) else maxValue
        }
        val min = (maxValue / 6).toFloat()
        val yValues = mutableListOf<Float>()
        for (i in 1..6) {
            yValues.add(min * i)
        }
        return yValues
    }


    fun mockMarketData(data: MarketDto, requiredItems: Int): MarketDto? {
        var newData: MarketDto? = null
        val marketDataList = mutableListOf<MarketData>()
        data.list?.isNotEmptyOrNull { list ->
            list.first().let(marketDataList::add)
            repeat(requiredItems) { index ->
                marketDataList[0].let {
                    var maxRandom = 10.0
                    val divisor =
                        (10.0).pow(((it.price?.value ?: 0.0).toInt().toString().length - 1))
                    if (divisor > 0) {
                        maxRandom = (((it.price?.value ?: 0.0) / divisor) / 2) * divisor
                    }
                    marketDataList.add(
                        MarketData(
                            priceDate = it.priceDate?.toLocalDateTime()
                                ?.plusDays((index + 1).toLong())
                                ?.asString("yyyy-MM-dd HH:mm:ss.SSS"),
                            price = PriceValue().apply {
                                currency = it.price?.currency
                                value = if (index % 2 == 0) (it.price?.value ?: 0.0) +
                                        Random.nextDouble(1.0, maxRandom)
                                else (it.price?.value ?: 0.0) -
                                        Random.nextDouble(1.0, maxRandom)
                            }
                        )
                    )
                }
            }
            newData = MarketDto(
                percentage = data.percentage,
                list = marketDataList.toList()
            )
        }
        return newData
    }

}