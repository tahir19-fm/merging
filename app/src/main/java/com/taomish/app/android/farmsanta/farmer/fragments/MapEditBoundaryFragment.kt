package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.FarmerMapSelectActivity
import com.taomish.app.android.farmsanta.farmer.background.SaveFarmerTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Coordinate
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer

class MapEditBoundaryFragment : FarmSantaBaseFragment() {
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

    override fun initData() {
        (requireActivity() as FarmerMapSelectActivity)
            .updateMap(AppConstants.DataTransferConstants.KEY_MAP_EDIT_BOUNDARY)
    }

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
        if (buttonSave!!.text.toString() == SAVE) {
            updateLandBoundaryAPI()
        } else {
            (requireActivity() as FarmerMapSelectActivity).updateMap(
                AppConstants.DataTransferConstants.KEY_MAP_COMPLETE_DRAW,
                null
            )
            buttonSave!!.text = SAVE
        }
    }

    private fun updateLandBoundary(): Farmer {
        val selectedFarmer = (requireActivity() as FarmerMapSelectActivity).farmer
        val list = (requireActivity() as FarmerMapSelectActivity).markerOptionsArrayList
        val coordinates = ArrayList<Coordinate>()
        for (i in list.indices) {
            val mo = list[i]
            val coordinate = Coordinate()
            coordinate.index = i
            coordinate.latitude = mo.position.latitude
            coordinate.longitude = mo.position.longitude
            coordinates.add(coordinate)
        }
        val landIndex = (requireActivity() as FarmerMapSelectActivity).selectedLandIndex
        selectedFarmer.lands[landIndex].coordinates = coordinates
        return selectedFarmer
    }

    private fun updateLandBoundaryAPI() {
        val selectedFarmer = updateLandBoundary()
        val task = SaveFarmerTask(selectedFarmer)
        task.context = requireContext()
        task.setLoadingMessage("Saving farm details...")
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Farmer) {
                    selectedFarmer.lands = data.lands
                    (requireActivity() as FarmerMapSelectActivity).farmer = selectedFarmer
                    DataHolder.getInstance().selectedFarmer = selectedFarmer
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }
}