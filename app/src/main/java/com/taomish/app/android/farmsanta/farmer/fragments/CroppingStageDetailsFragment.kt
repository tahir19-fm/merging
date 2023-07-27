package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_calendar_stage_datail.CalendarStageDetailsFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding


class CroppingStageDetailsFragment : FarmSantaBaseFragment() {

    private val viewModel by activityViewModels<CropCalendarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root
        init()
        initData()
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    CalendarStageDetailsFragmentScreen(
                        viewModel = viewModel,
                        onBack = this@CroppingStageDetailsFragment::onBack,
                        goToFertilizerCalculator = this@CroppingStageDetailsFragment::goToFertilizerCalculator
                    )
                }
            }
        }
        return root
    }

    override fun init() {}

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?
    ): View? {
        return null
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {}

    private fun goToFertilizerCalculator() {
        fragmentChangeHelper?.onFragmentChange(
            AppConstants.FragmentConstants.FERTILIZER_CALC_FROM_CROP_STAGE_DETAILS
        )
    }

    private fun onBack() {
        findNavController().popBackStack()
    }

}