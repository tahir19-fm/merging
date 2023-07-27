package com.taomish.app.android.farmsanta.farmer.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.Advisory
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting

class  FarmScoutImageInfoFragment(var advisory: Advisory?, var scouting: FarmScouting?) : FarmSantaBaseFragment() {
    private lateinit var cropNameTextView: TextView
    private lateinit var growthStageTextView: TextView
    private lateinit var plantPartTextView: TextView
    private lateinit var notesTextView: TextView
    private lateinit var temperatureTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var rainfallTextView: TextView
    private lateinit var windSpeedTextView: TextView

    override fun init() {

    }

    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_farm_scout_info, container, false)
    }

    override fun initViewsInLayout() {
        cropNameTextView                        = initThisView(R.id.farmScoutInfo_text_cropName)
        growthStageTextView                     = initThisView(R.id.farmScoutInfo_text_growthStage)
        plantPartTextView                       = initThisView(R.id.farmScoutInfo_text_plantPart)
        notesTextView                           = initThisView(R.id.farmScoutInfo_text_notes)
        temperatureTextView                     = initThisView(R.id.farmScoutInfo_text_temp)
        humidityTextView                        = initThisView(R.id.farmScoutInfo_text_humidity)
        rainfallTextView                        = initThisView(R.id.farmScoutInfo_text_rain)
        windSpeedTextView                       = initThisView(R.id.farmScoutInfo_text_wind)
    }

    override fun initListeners() {

    }

    override fun initData() {
        loadData()
    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {
        if (scouting != null) {
            plantPartTextView.text              = scouting?.images?.getOrNull(0)?.plantPart ?: ""
            cropNameTextView.text               = getCropName(scouting?.crop ?: "")
            growthStageTextView.text            = scouting?.cropStage
            notesTextView.text                  = scouting?.caption ?: "-"

            humidityTextView.text               = "${scouting?.currentWeather?.humidity ?: 0} %"
            temperatureTextView.text            = "${scouting?.currentWeather?.temp ?: 0} °C"
            rainfallTextView.text               = "${scouting?.currentWeather?.rain ?: 0} %"
            windSpeedTextView.text              = "${scouting?.currentWeather?.wind ?: 0} km/h"
        }
    }

    private fun getCropName(uuid: String): String? {
        val cropArrayList = DataHolder.getInstance().cropArrayList
        if (cropArrayList != null) {
            for (crop in cropArrayList) {
                if (crop.uuid == uuid) {
                    return crop.cropName
                }
            }
        }
        return uuid
    }
}