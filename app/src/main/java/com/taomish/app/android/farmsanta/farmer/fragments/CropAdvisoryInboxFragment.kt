package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.GetCropAdvisoriesTask
import com.taomish.app.android.farmsanta.farmer.background.GetFilteredAdvisory
import com.taomish.app.android.farmsanta.farmer.background.GetGrowthStagesTask
import com.taomish.app.android.farmsanta.farmer.background.GetSearchedAdvisoriesTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropAdvisoryViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.CropAdvisoryInboxFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.utils.isNotEmptyOrNull
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

@Suppress("UNCHECKED_CAST")
class CropAdvisoryInboxFragment : FarmSantaBaseFragment() {

    private val viewModel by activityViewModels<CropAdvisoryViewModel>()
    private lateinit var mContext: Context
    override fun init() {}

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle,
    ): View {
        return inflater.inflate(R.layout.fragment_crop_advisory_inbox, container, false)
    }

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
                    CropAdvisoryInboxFragmentScreen(
                        viewModel = viewModel,
                        goToAdvisoryDetails = this@CropAdvisoryInboxFragment::goToAdvisoryDetails,
                        onSearch = this@CropAdvisoryInboxFragment::searchAdvisories,
                        onClose = this@CropAdvisoryInboxFragment::setCropAdvisories,
                        onFilterApply = this@CropAdvisoryInboxFragment::filterAdvisories
                    )
                }
            }
        }
        return root
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}
    override fun initData() {
        mContext = requireContext()
        setCropAdvisories()
        fetchGrowthStages()
        DataHolder.getInstance().cropMasterMap?.let {
            viewModel.cropsMap.postValue(it)
        }
        DataHolder.getInstance().advisoryTagMap?.let {
            viewModel.advisoryTags.postValue(it)
        }
    }


    private fun setCropAdvisories() {
        viewModel.cropAdvisories.clear()
        if (!DataHolder.getInstance().cropAdvisoryArrayList.isNullOrEmpty()) {
            viewModel.cropAdvisories.addAll(DataHolder.getInstance().cropAdvisoryArrayList)
            return
        }
        val task = GetCropAdvisoriesTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is List<*>) {
                    if (data.isNotEmpty()) {
                        val c = ArrayList<CropAdvisory>()
                        for (advisory in (data as List<CropAdvisory>)) {
                            if (advisory.status.equals("published", ignoreCase = true)) {
                                c.add(advisory)
                            }
                        }
                        viewModel.cropAdvisories.addAll(c)
                        DataHolder.getInstance().advisoryCount = c.size
                        DataHolder.getInstance().cropAdvisoryArrayList = c
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }


    private fun searchAdvisories() {
        val task = GetSearchedAdvisoriesTask()
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage(mContext.getString(R.string.loading_crops_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    val list = data.toList() as List<CropAdvisory>?
                    viewModel.cropAdvisories.clear()
                    viewModel.cropAdvisories.addAll(list.orEmpty())
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                showToast(R.string.fetch_crops_error_msg)
            }
        })
        task.execute(viewModel.text.value)
    }


    private fun fetchGrowthStages() {
        DataHolder.getInstance().growthStagesMap.isNotEmptyOrNull {
            viewModel.growthStages.postValue(it)
            return@isNotEmptyOrNull
        }
        val task = GetGrowthStagesTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    val list = data as ArrayList<GlobalIndicatorDTO>?
                    list.isNotEmptyOrNull {
                        DataHolder.getInstance().setGrowthStagesMap(list)
                        DataHolder.getInstance().growthStagesMap?.let {
                            viewModel.growthStages.postValue(it)
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                showToast("reason : $reason\nmessage: $errorMessage")
            }
        })
        task.execute()
    }


    private fun filterAdvisories() {
        if (viewModel.filterCrops.isEmpty() &&
            viewModel.filterGrowthStages.isEmpty() &&
            viewModel.filterAdvisoryTags.isEmpty()
        ) {
            showToast(R.string.filter_options_msg)
            return
        }

        val task = GetFilteredAdvisory(
            viewModel.filterCrops,
            viewModel.filterGrowthStages,
            viewModel.filterAdvisoryTags
        )
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage(getString(R.string.filtering_advisories))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    viewModel.isFiltered = true
                    viewModel.cropAdvisories.clear()
                    viewModel.cropAdvisories.addAll(data as ArrayList<CropAdvisory>)
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                showToast("reason : $reason\nmessage : $errorMessage")
            }
        })
        task.execute()
    }


    private fun goToAdvisoryDetails(advisory: CropAdvisory) {
        DataHolder.getInstance().dataObject = advisory
        fragmentChangeHelper?.onFragmentData(
            AppConstants.FragmentConstants.FRAGMENT_VIEW_ADVISORY_FROM_ADVISORY_LIST
        )
    }

}