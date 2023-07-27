package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropAdvisoryViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_advisories.SavedAdvisoriesFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory

// TODO: All the functionalities for this fragment is remained.
class SavedAdvisoriesFragment : FarmSantaBaseFragment() {

    private var advisoryArrayList: List<CropAdvisory> = emptyList()
    private val viewModel by activityViewModels<CropAdvisoryViewModel>()

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
                    SavedAdvisoriesFragmentScreen(
                        viewModel = viewModel,
                        advisories = advisoryArrayList
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
        savedState: Bundle?
    ): View? {
        return null
    }

    override fun initViewsInLayout() {

    }

    override fun initListeners() {

    }

    override fun initData() {
        advisoryArrayList = DataHolder.getInstance().cropAdvisoryArrayList
    }

}