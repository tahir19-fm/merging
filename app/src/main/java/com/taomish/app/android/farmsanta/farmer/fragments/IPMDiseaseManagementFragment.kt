package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_ipm_disease_management.IPMDiseaseManagementFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding


class IPMDiseaseManagementFragment : FarmSantaBaseFragment() {

    private val viewModel by activityViewModels<POPViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root
        init()
        initData()
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    IPMDiseaseManagementFragmentScreen(
                        viewModel = viewModel,
                        onZoomImage = this@IPMDiseaseManagementFragment::goToZoomImage
                    )
                }
            }
        }
        return root
    }

    override fun init() {

    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?,
    ): View? = null

    override fun initViewsInLayout() = Unit
    override fun initListeners() = Unit
    override fun initData() = Unit


    private fun goToZoomImage(imageUrl: String) {
        fragmentChangeHelper?.onFragmentData(
            AppConstants.FragmentConstants.ZOOM_IMAGE_FRAGMENT_FROM_IPM_DISEASE, imageUrl
        )
    }
}