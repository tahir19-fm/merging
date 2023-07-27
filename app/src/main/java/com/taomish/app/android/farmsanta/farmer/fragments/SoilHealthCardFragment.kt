package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.adapters.SoilHealthCardExpandableAdapter
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.models.api.soil.Parameter
import com.taomish.app.android.farmsanta.farmer.models.api.soil.SoilHealth

class SoilHealthCardFragment(val soilHealth: SoilHealth?) : FarmSantaBaseFragment() {
    private lateinit var soilHealthExpandableListView: ExpandableListView

    private lateinit var currentContext: Context

    override fun init() {

    }

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_soil_health_card, container, false)
    }

    override fun initViewsInLayout() {
        soilHealthExpandableListView = initThisView(R.id.soilHealthCard_expandable_dataHolder)
    }

    override fun initListeners() {
        soilHealthExpandableListView.setOnGroupExpandListener {
            for (i in 0..soilHealthExpandableListView.expandableListAdapter?.groupCount!!) {
                if (it != i) {
                    soilHealthExpandableListView.collapseGroup(i)
                }
            }
        }
    }

    override fun initData() {
        currentContext = requireContext()
        loadPopList()
    }


    private fun loadPopList() {
        val basicTestsList = ArrayList<Parameter>()
        val primaryNutrientsList = ArrayList<Parameter>()
        val secondaryNutrientsList = ArrayList<Parameter>()
        val microNutrientsList = ArrayList<Parameter>()
        val otherGroupsList = ArrayList<Parameter>()

        if (soilHealth?.parameters != null) {
            for (param: Parameter in soilHealth.parameters) {
                when (param.parameter) {
                    "Phosphorus (P)" -> primaryNutrientsList.add(param)
                    "Magnesium (Mg)" -> secondaryNutrientsList.add(param)
                    "Electrical Conductivity (EC)" -> basicTestsList.add(param)
                    "Copper" -> microNutrientsList.add(param)
                    "Sulphur" -> microNutrientsList.add(param)
                    "Phosphate" -> otherGroupsList.add(param)
                    "Iron" -> microNutrientsList.add(param)
                    "Calcium (Ca)" -> secondaryNutrientsList.add(param)
                    "pH" -> basicTestsList.add(param)
                    "CaCo3" -> otherGroupsList.add(param)
                    "Potassium (K)" -> primaryNutrientsList.add(param)
                    "Boron" -> microNutrientsList.add(param)
                    "Manganese" -> microNutrientsList.add(param)
                    "Soil moisture" -> basicTestsList.add(param)
                    "Nitrate Nitrogen" -> otherGroupsList.add(param)
                    "Nitrogen (N)" -> primaryNutrientsList.add(param)
                    "Zinc" -> microNutrientsList.add(param)
                    "Organic Carbon (OC)" -> basicTestsList.add(param)
                    else -> otherGroupsList.add(param)
                }
            }
        }

        val soilHealthParameters = mapOf(
            "BASIC TESTS" to basicTestsList,
            "PRIMARY NUTRIENTS" to primaryNutrientsList,
            "SECONDARY NUTRIENTS" to secondaryNutrientsList,
            "MICRO NUTRIENTS" to microNutrientsList,
            "OTHER GROUPS" to otherGroupsList
        )
        val adapter = SoilHealthCardExpandableAdapter(
            currentContext,
            soilHealthParameters
        )
        soilHealthExpandableListView.setAdapter(adapter)
    }
}