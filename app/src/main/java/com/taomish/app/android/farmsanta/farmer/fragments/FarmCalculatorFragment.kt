package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants

class FarmCalculatorFragment : FarmSantaBaseFragment() {
    private var cardViewSeedRate: CardView? = null
    private var cardViewPesticide: CardView? = null
    private var cardViewPlantPopulation: CardView? = null
    private var cardViewFertilizer: CardView? = null
    private var buttonBack: Button? = null
    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
    ): View {
        return inflater.inflate(R.layout.fragment_farm_calculator, container, false)
    }

    override fun initViewsInLayout() {
        cardViewSeedRate = initThisView(R.id.farmCalc_card_seedRate)
        cardViewPesticide = initThisView(R.id.farmCalc_card_pesticideCalculator)
        cardViewFertilizer = initThisView(R.id.farmCalc_card_fertilizerCalculator)
        cardViewPlantPopulation = initThisView(R.id.farmCalc_card_plantPopulation)
        buttonBack = initThisView(R.id.calculator_button_back)
    }

    override fun initListeners() {
        cardViewSeedRate!!.setOnClickListener { v: View? -> goToSeedRateFragment() }
        cardViewPlantPopulation!!.setOnClickListener { v: View? -> goToPlantPopulationFragment() }
        cardViewFertilizer!!.setOnClickListener { v: View? -> goToFertilizerCalcFragment() }
        cardViewPesticide!!.setOnClickListener { v: View? -> goToPesticideCalcFragment() }
        buttonBack!!.setOnClickListener { v: View? -> goBack() }
    }

    override fun initData() {}
    private fun goToSeedRateFragment() {
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onFragmentChange(AppConstants.FragmentConstants.SEED_RATE_FRAGMENT)
        }
    }

    private fun goToPlantPopulationFragment() {
//        if (getOnFragmentInteractionListener() != null) {
//            getOnFragmentInteractionListener().onFragmentChange(AppConstants.FragmentConstants.PLANT_POPULATION_FRAGMENT);
//        }
    }

    private fun goToFertilizerCalcFragment() {
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onFragmentChange(AppConstants.FragmentConstants.FERTILIZER_CALC_FRAGMENT)
        }
    }

    private fun goToPesticideCalcFragment() {
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onFragmentChange(AppConstants.FragmentConstants.PESTICIDE_CALC_FRAGMENT)
        }
    }

    private fun goBack() {
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onFragmentChange(AppConstants.FragmentConstants.FARMER_HOME_FRAGMENT)
        }
    }
}