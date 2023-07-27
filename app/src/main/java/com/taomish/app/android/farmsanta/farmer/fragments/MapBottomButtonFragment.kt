package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.FarmerMapSelectActivity
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants

class MapBottomButtonFragment : FarmSantaBaseFragment() {
    private val SAVE = "Save"
    private val COMPLETE = "Complete"
    private var buttonCancel: Button? = null
    private var buttonSave: Button? = null
    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
    ): View {
        return inflater.inflate(R.layout.fragment_map_bottom_button, container, false)
    }

    override fun initViewsInLayout() {
        buttonCancel = initThisView(R.id.mapBottomButton_button_cancel)
        buttonSave = initThisView(R.id.mapBottomButton_button_save)
    }

    override fun initListeners() {
        buttonCancel!!.setOnClickListener { v: View? -> cancelMapPlotting() }
        buttonSave!!.setOnClickListener { v: View? -> saveMapPlot() }
    }

    override fun initData() {}
    override fun update(tag: String) {
        super.update(tag)
        if (tag == AppConstants.DataTransferConstants.KEY_MAP_ENABLE) {
            buttonSave!!.isEnabled = true
        } else if (tag == AppConstants.DataTransferConstants.KEY_MAP_DISABLE) {
            buttonSave!!.isEnabled = false
        }
    }

    private fun cancelMapPlotting() {
        (requireActivity() as FarmerMapSelectActivity)
            .updateMap(
                AppConstants.DataTransferConstants.KEY_MAP_CANCEL_DRAW,
                null
            )
        (requireActivity() as FarmerMapSelectActivity).navController.popBackStack()
    }

    private fun saveMapPlot() {

    }
}