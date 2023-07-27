package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.*
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FarmScoutingViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_image.FarmScoutImageFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@Suppress("UNCHECKED_CAST")
class FarmScoutImageFragment : FarmSantaBaseFragment() {
    private lateinit var landId: String
    private val viewModel by activityViewModels<FarmScoutingViewModel>()
    private lateinit var mContext: Context

    override fun init() {
        val args: FarmScoutImageFragmentArgs =
            FarmScoutImageFragmentArgs.fromBundle(requireArguments())
        landId = args.landId
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?,
    ): View? {
        return null
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
                    FarmScoutImageFragmentScreen(
                        viewModel = viewModel,
                        onClose = this@FarmScoutImageFragment::fetchFarmScouting,
                        onSearch = this@FarmScoutImageFragment::searchScouting,
                        goToFarmScouting = this@FarmScoutImageFragment::goToFarmScoutDetailFragment,
                        onAddClick = this@FarmScoutImageFragment::goToAddScouting
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
    }

    override fun onResume() {
        fetchFarmScouting()
        super.onResume()
    }

    private fun fetchFarmScouting() {
        val farmerId = DataHolder.getInstance().selectedFarmer?.uuid ?: ""
        val task = GetFarmScoutingTask(landId, farmerId)
        task.context = requireContext()
        task.setShowLoading(true)
        task.setLoadingMessage("Getting scouting images...")
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                (data as Array<FarmScouting>).also { scouting ->
                    val list = scouting.toList()
                    if (list.isNotEmpty()) {
                        DataHolder.getInstance().dataObject = list
                        viewModel.scouting.postValue(list.groupBy { it.advisoryExist })
                        fetchCrops()
                        fetchCropStages()
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }


    private fun searchScouting() {
        viewModel.scouting.postValue(emptyMap())
        val task = GetSearchedFarmScoutingTask(viewModel.searchText.value.trim())
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage(mContext.getString(R.string.searching))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is Array<*>) {
                    val list = data.toList() as List<FarmScouting>?
                    if (!list.isNullOrEmpty()) {
                        viewModel.scouting.postValue(list.groupBy { it.advisoryExist })
                    }

                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                mContext.showToast("reason: $reason")
            }
        })
        task.execute()
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


    private fun goToFarmScoutDetailFragment() {
        viewModel.advisory.postValue(null)
        fragmentChangeHelper?.onFragmentData(
            AppConstants.FragmentConstants.SCOUTING_DETAILS_FROM_SCOUTING_LIST
        )
    }

    private fun goToAddScouting() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.SCOUTING_LIST_TO_ADD_SCOUT
            )
        }
    }
}