package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment

class PesticideCalcFragment : FarmSantaBaseFragment() {
    private var linearLayoutBottomSheet: LinearLayout? = null
    private var buttonContinue: Button? = null
    private var imageViewBack: ImageView? = null
    private var behavior: BottomSheetBehavior<*>? = null
    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
    ): View {
        val contextThemeWrapper: Context = ContextThemeWrapper(
            activity, R.style.AppThemeMaterial
        )
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return localInflater.inflate(R.layout.fragment_pesticide_calc, container, false)
    }

    override fun initViewsInLayout() {
        linearLayoutBottomSheet = initThisView(R.id.pesticideCalc_linear_bottomSheet)
        buttonContinue = initThisView(R.id.pesticideCalc_button_continue)
        imageViewBack = initThisView(R.id.pesticideCalc_image_back)
    }

    override fun initListeners() {
        behavior = BottomSheetBehavior.from(linearLayoutBottomSheet!!)
        buttonContinue!!.setOnClickListener { v: View? -> openBottomSheet() }
        imageViewBack!!.setOnClickListener { v: View? -> goBack() }
    }

    override fun initData() {
        behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun openBottomSheet() {
        behavior!!.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun goBack() {
        parentFragmentManager.popBackStackImmediate()
    }
}