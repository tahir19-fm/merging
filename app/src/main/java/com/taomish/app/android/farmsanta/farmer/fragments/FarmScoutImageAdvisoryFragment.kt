package com.taomish.app.android.farmsanta.farmer.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.Advisory

class FarmScoutImageAdvisoryFragment(var advisory: Advisory?) : FarmSantaBaseFragment() {

    private lateinit var issueCauseTextView: TextView
    private lateinit var issueNameTextView: TextView
    private lateinit var symptomsTextView: TextView
    private lateinit var favourableTextView: TextView
    private lateinit var preventiveTextView: TextView
    private lateinit var culturalTextView: TextView
    private lateinit var noAdvisoryTextView: TextView
    private lateinit var allDataContainerLinear: LinearLayoutCompat

    //util method
    private var recyclableTextView: TextView? = null

    override fun init() {

    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?
    ): View {
        return inflater!!.inflate(R.layout.fragment_farm_scout_advisory, container, false)
    }

    override fun initViewsInLayout() {
        issueCauseTextView = initThisView(R.id.farmScoutAdvisory_text_issueCause)
        issueNameTextView = initThisView(R.id.farmScoutAdvisory_text_issueName)
        symptomsTextView = initThisView(R.id.farmScoutAdvisory_text_symptoms)
        favourableTextView = initThisView(R.id.farmScoutAdvisory_text_favourable)
        preventiveTextView = initThisView(R.id.farmScoutAdvisory_text_preventive)
        culturalTextView = initThisView(R.id.farmScoutAdvisory_text_culturalControl)
        noAdvisoryTextView = initThisView(R.id.farmScoutAdvisory_text_noAdvisory)

        allDataContainerLinear = initThisView(R.id.farmScoutAdvisory_linear_allContainer);
    }

    override fun initListeners() {

    }

    override fun initData() {
        loadData()
    }

    private fun loadData() {
        if (advisory != null) {
            issueCauseTextView.text = advisory?.category
            issueNameTextView.text = advisory?.advisoryDetails?.localName
            symptomsTextView.text = advisory?.advisoryDetails?.symptomsOfAttack
            favourableTextView.text = advisory?.advisoryDetails?.favourableConditions
            preventiveTextView.text = advisory?.advisoryDetails?.preventiveMeasures
            culturalTextView.text = advisory?.advisoryDetails?.culturalMechanicalControl
            addTableLayout()
        } else {
            allDataContainerLinear.visibility = View.GONE
            noAdvisoryTextView.visibility = View.VISIBLE
        }
    }

    private fun addTableLayout() {
        val wrapWrapTableRowParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        val fixedRowHeight = 100

        var row = TableRow(requireContext())

        val fixedColumn: TableLayout = initThisView(R.id.fixed_column)
        //rest of the table (within a scroll view)

        val textList = arrayOf(
            "Formulation",
            "Formulation type",
            "Product names",
            "Dosage",
            "Dosage method",
            "Water requirement",
            "Pre-harvest interval",
            "Application method", "Mode of action", "Toxicity level"
        )

        val advisoryMutableList: MutableList<MutableMap<String, String>> = mutableListOf()
        val scrollablePart: TableLayout = initThisView(R.id.scrollable_part)

        if (advisory?.advisoryTable != null) {
            for (advisoryTable in advisory!!.advisoryTable) {
                val mutableMap2: MutableMap<String, String> = mutableMapOf()
                mutableMap2["Formulation"] = advisoryTable.formulation
                mutableMap2["Formulation type"] = advisoryTable.formulationType
                mutableMap2["Product names"] = advisoryTable.prodName.toString()
                mutableMap2["Dosage"] = "${advisoryTable.dosage.unit} ${advisoryTable.dosage.uom}"
                mutableMap2["Dosage method"] = advisoryTable.dosageMethod
                mutableMap2["Water requirement"] =
                    "${advisoryTable.waterRequirement.unit}  ${advisoryTable.waterRequirement.uom}"
                mutableMap2["Pre-harvest interval"] =
                    "${advisoryTable.preHarvestInterval.unit} ${advisoryTable.preHarvestInterval.uom}"
                mutableMap2["Application method"] = advisoryTable.applicationMethod
                mutableMap2["Mode of action"] = advisoryTable.modeOfAction
                mutableMap2["Toxicity level"] = advisoryTable.toxicityLevel

                advisoryMutableList += mutableMap2
            }
        }

        for (i in textList.indices) {
            val fixedView: TextView = makeTableRowWithText(
                textList[i],
                40, fixedRowHeight
            )
            with(fixedView) {
                setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.table_view_border
                    )
                )
                setTextColor(ContextCompat.getColor(requireContext(), R.color.light_black))
                setPadding(4)
                setTypeface(fixedView.typeface, Typeface.BOLD);
                textSize = 14F
                background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.fixed_column_border)
                fixedColumn.addView(fixedView)
            }
            row = TableRow(requireContext())
            with(row) {
                layoutParams = wrapWrapTableRowParams
                setBackgroundColor(Color.WHITE)

                for (j in advisoryMutableList) {
                    addView(makeTableRowWithText(j.getValue(textList[i]), 70, fixedRowHeight))
                }
            }
            scrollablePart.addView(row)
        }

    }

    private fun makeTableRowWithText(
        value: String?,
        widthInPercentOfScreenWidth: Int,
        fixedHeightInPixels: Int
    ): TextView {
        val screenWidth = resources.displayMetrics.widthPixels
        recyclableTextView = TextView(requireContext())
        with(recyclableTextView) {
            this!!.text = value
            setTextColor(Color.BLACK)
            textSize = 14f
            setPadding(4)
            background = ContextCompat.getDrawable(requireContext(), R.drawable.border)
            width = widthInPercentOfScreenWidth * screenWidth / 100
            height = fixedHeightInPixels
        }
        return recyclableTextView as TextView
    }
}