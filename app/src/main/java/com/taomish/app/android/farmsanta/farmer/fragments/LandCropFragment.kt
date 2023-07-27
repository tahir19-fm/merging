package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.adapters.LandCropFragmentAdapter
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer

/*This Fragment is added to add new farm details
 * and user can add intercrop and other information
 * about crop*/
class LandCropFragment : FarmSantaBaseFragment() {
    private var buttonBack: Button? = null
    private var myFarmsRecycler: RecyclerView? = null
    private var fabAddLand: FloatingActionButton? = null
    private var profile: Farmer? = null
    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
    ): View {
        return inflater.inflate(R.layout.fragment_land_crop, container, false)
    }

    override fun initViewsInLayout() {
        buttonBack = initThisView(R.id.landCrop_button_back)
        myFarmsRecycler = initThisView(R.id.landCrop_recycler_myFarms)
        fabAddLand = initThisView(R.id.landCrop_fab_add)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun initListeners() {
        buttonBack!!.setOnClickListener { v: View? -> goToHomeFragment() }
        fabAddLand!!.setOnClickListener { v: View? -> goToAddLandFragment() }
    }

    override fun initData() {
        profile = DataHolder.getInstance().selectedFarmer
        if (profile != null && profile!!.lands != null) setMyFarmsAdapter()
    }

    private fun setMyFarmsAdapter() {
        val adapter = LandCropFragmentAdapter(profile!!.lands)
        val mLayoutManager = LinearLayoutManager(activity)
        adapter.setOnRecyclerItemClickListener { v: View, tag: String, index: Int ->
            onFarmClick(
                v,
                tag,
                index
            )
        }
        myFarmsRecycler!!.layoutManager = mLayoutManager
        myFarmsRecycler!!.adapter = adapter
    }

    private fun goToAddLandFragment() {
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onFragmentChange(AppConstants.FragmentConstants.ADD_LAND)
        }
    }

    private fun goToHomeFragment() {
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onFragmentChange(AppConstants.FragmentConstants.FARMER_HOME_FRAGMENT)
        }
    }

    private fun onFarmClick(v: View, tag: String, index: Int) {
//        if (getOnFragmentInteractionListener() != null) {
//            getOnFragmentInteractionListener().onFragmentData(
//                    AppConstants.FragmentConstants.FARM_DETAILS, index);
//        }
    }
}