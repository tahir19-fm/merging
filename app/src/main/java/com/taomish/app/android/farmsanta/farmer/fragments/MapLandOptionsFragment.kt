package com.taomish.app.android.farmsanta.farmer.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.FarmerMapSelectActivity
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer

@Suppress("OVERRIDE_DEPRECATION")
class MapLandOptionsFragment : FarmSantaBaseFragment() {
    private var linearLayoutAddCrop: LinearLayout? = null
    private var linearLayoutAddLand: LinearLayout? = null
    private var linearLayoutSoilHealth: LinearLayout? = null
    private var linearLayoutAddFarmScout: LinearLayout? = null
    private var linearLayoutGallery: LinearLayout? = null
    private var linearLayoutScoutingView: LinearLayout? = null
    private var textViewCropLabel: TextView? = null
    private var textViewLandLabel: TextView? = null
    private var scoutingCountTextView: TextView? = null
    private var selectedFarmer: Farmer? = null
    private var viewExpandCollapse: View? = null
    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
    ): View {
        return inflater.inflate(R.layout.fragment_map_land_options, container, false)
    }

    override fun initViewsInLayout() {
        linearLayoutAddCrop = initThisView(R.id.mapLandOptions_linear_addCrop)
        linearLayoutAddLand = initThisView(R.id.mapLandOptions_linear_addLand)
        linearLayoutSoilHealth = initThisView(R.id.mapLandOptions_linear_addSoil)
        linearLayoutAddFarmScout = initThisView(R.id.mapLandOptions_linear_addScouting)
        linearLayoutGallery = initThisView(R.id.mapLandOptions_linear_gallery)
        textViewCropLabel = initThisView(R.id.mapLandOptions_text_addCrop)
        textViewLandLabel = initThisView(R.id.mapLandOptions_text_addLand)
        viewExpandCollapse = initThisView(R.id.mapLandOptions_expand_collapse)
        linearLayoutScoutingView = initThisView(R.id.mapLandOptions_linear_scouting)
        scoutingCountTextView = initThisView(R.id.mapLandOptions_text_readCountBadge)
    }

    override fun initListeners() {
        linearLayoutAddCrop!!.setOnClickListener { v: View? -> onCropClick() }
        linearLayoutAddLand!!.setOnClickListener { v: View? -> onLandClick() }
        linearLayoutSoilHealth!!.setOnClickListener { v: View? -> onSoilHealthClick() }
        linearLayoutAddFarmScout!!.setOnClickListener { v: View? -> onAddScoutingClick() }
        linearLayoutGallery!!.setOnClickListener { v: View? -> onGalleryClick() }
        viewExpandCollapse!!.setOnClickListener { v: View? -> onExpandAndCollapseClick() }
    }

    override fun initData() {
        selectedFarmer = (requireActivity() as FarmerMapSelectActivity).farmer
        setLabels()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_land, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuItem_edit_land) {
            onMenuClick()
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun setLabels() {
        val landIndex = (requireActivity() as FarmerMapSelectActivity).selectedLandIndex
        if (selectedFarmer == null || selectedFarmer!!.lands == null || landIndex == -1) {
            textViewLandLabel!!.text = "+ Add land info"
            textViewCropLabel!!.text = "+ Add Crop"
            textViewCropLabel!!.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            textViewLandLabel!!.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        } else if (selectedFarmer!!.lands[landIndex] != null
            && selectedFarmer!!.lands[landIndex].crops == null
        ) {
            textViewCropLabel!!.text = "+ Add Crop"
            textViewCropLabel!!.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
        scoutingCountTextView!!.text =
            DataHolder.getInstance().scoutingImagesCount.toString()
    }

    private fun onCropClick() {
        val landIndex = (requireActivity() as FarmerMapSelectActivity).selectedLandIndex
        if (selectedFarmer != null && selectedFarmer!!.lands != null && landIndex >= 0 && selectedFarmer!!.lands.size > landIndex && selectedFarmer!!.lands[landIndex].crops != null) {
            goToCropDetails()
        } else if (selectedFarmer != null && (selectedFarmer!!.lands == null || landIndex < 0)) {
            goToLandDetailsMissingDialog()
        } else {
            goToAddCropDetails()
        }
    }

    private fun onSoilHealthClick() {

    }

    private fun onAddScoutingClick() {
        val landIndex = (requireActivity() as FarmerMapSelectActivity).selectedLandIndex
        if (landIndex == -1) {
            Toast.makeText(requireContext(), "No land added", Toast.LENGTH_SHORT).show()
        } else {
            val uuid = selectedFarmer!!.lands[landIndex].landId
        }
    }

    private fun onGalleryClick() {
        val landIndex = (requireActivity() as FarmerMapSelectActivity).selectedLandIndex
        if (landIndex == -1) {
            Toast.makeText(requireContext(), "No land added", Toast.LENGTH_SHORT).show()
        } else {
            val uuid = selectedFarmer!!.lands[landIndex].landId
        }
    }

    private fun onLandClick() {
        val landIndex = (requireActivity() as FarmerMapSelectActivity).selectedLandIndex
        if (landIndex == -1) {
            goToAddLandDetails()
        } else {
            if (selectedFarmer!!.lands == null || selectedFarmer!!.lands.size == 0) {
                goToAddLandDetails()
            } else {
                DataHolder.getInstance().dataObject = selectedFarmer!!.lands[landIndex]
            }
        }
    }

    private fun goToCropDetails() {
        val landIndex = (requireActivity() as FarmerMapSelectActivity).selectedLandIndex
        DataHolder.getInstance().dataObject = selectedFarmer!!.lands[landIndex].crops[0]
    }

    private fun goToLandDetailsMissingDialog() {

    }

    private fun goToAddLandDetails() {

    }

    private fun goToAddCropDetails() {

    }

    private fun goToEditLandName() {

    }

    private fun goToEditLandBoundary() {

    }

    private fun goToEditLandLocation() {

    }

    private fun onExpandAndCollapseClick() {
        if (linearLayoutScoutingView!!.visibility == View.GONE) {
            linearLayoutScoutingView!!.visibility = View.VISIBLE
        } else {
            linearLayoutScoutingView!!.visibility = View.GONE
        }
        // ChangeBounds myTransition               = new ChangeBounds();
        // myTransition.setDuration(5000L);
        // TransitionManager.go(new Scene(linearLayoutScoutingView), myTransition);
        // fadeOutAndHideView(linearLayoutScoutingView);
    }

    private fun onMenuClick() {
        if (activity == null) return
        val editMenu = requireActivity().findViewById<View>(R.id.menuItem_edit_land)
        val popup = PopupMenu(requireContext(), editMenu)
        popup.menuInflater.inflate(R.menu.edit_land_options, popup.menu)
        popup.setOnMenuItemClickListener { item: MenuItem -> getMessagesByType(item) }
        popup.show() //
    }

    @SuppressLint("NonConstantResourceId")
    private fun getMessagesByType(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editLand_menu_name -> {
                goToEditLandName()
                true
            }
            R.id.editLand_menu_location -> {
                goToEditLandLocation()
                true
            }
            R.id.editLand_menu_boundary -> {
                goToEditLandBoundary()
                true
            }
            else -> false
        }
    }
}