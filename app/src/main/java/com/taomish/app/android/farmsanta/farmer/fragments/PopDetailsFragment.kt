package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.background.GetPopDetailsTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.PopDetailViewFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components.IPMType
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components.IPMType.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDetailsDTO
import com.taomish.app.android.farmsanta.farmer.utils.postValue

@Suppress("OVERRIDE_DEPRECATION", "UNCHECKED_CAST")
class PopDetailsFragment : FarmSantaBaseFragment() {
    private lateinit var mContext: Context

    private val viewModel by activityViewModels<POPViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
                    PopDetailViewFragmentScreen(
                        viewModel = viewModel,
                        onSectionItemClicked = this@PopDetailsFragment::onSectionItemClicked,
                        goToZoomImage = this@PopDetailsFragment::goToZoomImageFragment
                    )
                }
            }
        }
        return root
    }

    private fun onSectionItemClicked(type: IPMType) {
        when (type) {
            InsectManagement -> openIPMInsectManagementFragment()
            DiseaseManagement -> openIPMDiseaseManagementFragment()
            WeedManagement -> openIPMWeedManagementFragment()
        }
    }

    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle,
    ): View? {
        return null
    }

    override fun initViewsInLayout() {}
    override fun initListeners() {}
    override fun initData() {
        mContext = requireContext()
        val args = PopDetailsFragmentArgs.fromBundle(
            arguments ?: Bundle()
        )
        viewModel.selected.postValue(args.tab)
        fetchPopDetails()
    }


    private fun fetchPopDetails() {
        val task = GetPopDetailsTask()
        task.context = mContext
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is PopDetailsDTO) {
                    viewModel.popDetails.postValue(null)
                    viewModel.popDetails.postValue(data)
                    //cropping process rows states should be equal in number as number of processes
                    //croppingProcessDto has to preserve states in viewModel in order to keep states
                    //of rows as user expands and collapses them.
                    viewModel.rowStates = List(data.croppingProcessDto?.processlist?.size ?: 0) {
                        mutableStateOf(false)
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                viewModel.popDetails.postValue(null)
                Log.d("PopDetailsFragment", "reason : $reason\nmessage : $errorMessage")
            }

        })
        task.execute(viewModel.pop.value?.uuid ?: "")
    }


    private fun goToZoomImageFragment(url: String) {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_ZOOM_IMAGE_FROM_POP_DETAILS,
                url
            )
        }
    }

    private fun openIPMInsectManagementFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.IPM_INSECT_MANAGEMENT_FRAGMENT,
                ""
            )
        }
    }

    private fun openIPMDiseaseManagementFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.IPM_DISEASE_MANAGEMENT_FRAGMENT,
                ""
            )
        }
    }

    private fun openIPMWeedManagementFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.IPM_WEED_MANAGEMENT_FRAGMENT,
                ""
            )
        }
    }

}