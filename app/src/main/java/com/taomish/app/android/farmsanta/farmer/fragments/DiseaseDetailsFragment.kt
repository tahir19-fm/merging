package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_disease_details.DiseaseDetailsFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants.FragmentConstants.DISEASE_DETAIL_TO_ASK_QUERY
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.disease.Disease
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel

class DiseaseDetailsFragment : FarmSantaBaseFragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

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
                    DiseaseDetailsFragmentScreen(
                        homeViewModel = homeViewModel,
                        onAskQuery = this@DiseaseDetailsFragment::goToAskQuery
                    )
                }
            }
        }
        return root
    }

    override fun init() = Unit

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?
    ): View? {
        return null
    }

    override fun initViewsInLayout() = Unit

    override fun initListeners() = Unit

    override fun initData() = Unit

    private fun goToAskQuery(disease: Disease) {
        DataHolder.getInstance().dataObject = disease
        fragmentChangeHelper?.onFragmentChange(DISEASE_DETAIL_TO_ASK_QUERY)
    }

}