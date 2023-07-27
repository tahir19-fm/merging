package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.GetAdvisoryByIdTask
import com.taomish.app.android.farmsanta.farmer.background.GetCropListTask
import com.taomish.app.android.farmsanta.farmer.background.GetCropStageListByCropId
import com.taomish.app.android.farmsanta.farmer.background.GetFarmScoutingByIdTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FarmScoutingViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details.FarmScoutDetailsFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.Advisory
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

@Suppress("UNCHECKED_CAST")
class FarmScoutImageDetailsFragment : FarmSantaBaseFragment() {

    private val viewModel: FarmScoutingViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var mContext: Context

    override fun init() {
        mContext = requireContext()
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?,
    ): View? = null

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
                    FarmScoutDetailsFragmentScreen(viewModel = viewModel)
                }
            }
        }
        return root
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        viewModel.growthStageDataList = homeViewModel.growthStages
        mContext = requireContext()
        val args = FarmScoutImageDetailsFragmentArgs.fromBundle(arguments ?: Bundle())
        if (args.scoutingUuid.isNotEmpty()) {
            getScoutingDetails(args.scoutingUuid)
        } else {
            getAdvisoryDetailsById(viewModel.selectedScouting.value?.uuid ?: "")
            viewModel.loadData(mContext)
        }
    }

    private fun getScoutingDetails(uuid: String) {
        val task = GetFarmScoutingByIdTask(uuid)
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage("Getting scouting details...")
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is FarmScouting) {
                    viewModel.selectedScouting.postValue(data)
                    getAdvisoryDetailsById(viewModel.selectedScouting.value?.uuid ?: "")
                    viewModel.loadData(mContext)
                    fetchCrops()
                    fetchCropStages()
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    private fun getAdvisoryDetailsById(uuid: String) {
        val task = GetAdvisoryByIdTask()
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage("Getting advisory details...")
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    if (data.isNotEmpty()) {
                        val dataList = data.toList() as List<Advisory>
                        viewModel.advisory.postValue(dataList.firstOrNull())
                        fetchCrops()
                        fetchCropStages()
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute(uuid)
    }


    private fun fetchCrops() {
        viewModel.crops.postValue(emptyMap())
        if (DataHolder.getInstance().cropMasterMap.isNullOrEmpty().not()) {
            DataHolder.getInstance().cropMasterMap.let {
                viewModel.crops.postValue(it)
                return
            }
        }
        val task = GetCropListTask()
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage(mContext.getString(R.string.loading_crops_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    (data.toList() as List<CropMaster>?)?.let {
                        viewModel.crops.postValue(it.associateBy { crop -> crop.uuid })
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                mContext.showToast(R.string.fetch_crops_error_msg)
            }
        })
        task.execute()
    }


    private fun fetchCropStages() {
        val task = GetCropStageListByCropId("")
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                (data as Array<*>).also {
                    if (it.isNotEmpty()) {
                        (data.toList() as List<CropStage>?)?.associateBy { stage -> stage.uuid }
                            ?.let { map -> viewModel.stages.postValue(map) }
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

}