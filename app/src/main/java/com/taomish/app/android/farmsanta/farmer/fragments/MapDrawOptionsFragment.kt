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

class MapDrawOptionsFragment : FarmSantaBaseFragment() {
    private var buttonStartMapDraw: Button? = null
    private var buttonSkipMapDraw: Button? = null
    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
    ): View {
        return inflater.inflate(R.layout.fragment_map_draw_options, container, false)
    }

    override fun initViewsInLayout() {
        buttonStartMapDraw = initThisView(R.id.mapDrawOptions_button_drawBoundary)
        buttonSkipMapDraw = initThisView(R.id.mapDrawOptions_button_skipDraw)
    }

    override fun initListeners() {
        buttonStartMapDraw!!.setOnClickListener { v: View? -> showDrawButtonBar() }
        buttonSkipMapDraw!!.setOnClickListener { v: View? -> onSkipDrawClick() }
    }

    override fun initData() {}
    private fun showDrawButtonBar() {
        (requireActivity() as FarmerMapSelectActivity)
            .updateMap(
                AppConstants.DataTransferConstants.KEY_MAP_START_DRAW,
                null
            )
    }

    private fun onSkipDrawClick() {
        // ((FarmerMapSelectActivity) requireActivity()).getNavController().popBackStack();
        (requireActivity() as FarmerMapSelectActivity)
            .updateMap(AppConstants.DataTransferConstants.KEY_MAP_SKIP_DRAW)
    }
}