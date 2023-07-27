package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.NutriSourceViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_nutri_source.NutriSourceFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding

// TODO: API Integration is pending.
class NutriSourceFragment : FarmSantaBaseFragment() {

    private val nutriSourceViewModel: NutriSourceViewModel by activityViewModels()

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
                    NutriSourceFragmentScreen(
                        nutriSourceViewModel = nutriSourceViewModel,
                        goToNutriSourceDetails = { goToNutriSourceDetails() },
                        goToSavedNutriSource = { goToSavedNutriSource() }
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

    private fun goToNutriSourceDetails() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(AppConstants.FragmentConstants.FRAGMENT_NUTRI_SOURCE_DETAILS)
        }
    }

    private fun goToSavedNutriSource() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_SAVED_NUTRI_SOURCE)
        }
    }
}