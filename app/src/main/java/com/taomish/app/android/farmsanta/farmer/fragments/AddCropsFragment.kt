package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.MainActivity
import com.taomish.app.android.farmsanta.farmer.background.GetCropListTask
import com.taomish.app.android.farmsanta.farmer.background.GetCropsDivisionsTask
import com.taomish.app.android.farmsanta.farmer.background.GetSearchedCropsTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.AddCropsFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: API Integration is pending.
@Suppress("UNCHECKED_CAST")
class AddCropsFragment : FarmSantaBaseFragment() {

    private val viewModel: SignUpAndEditProfileViewModel by activityViewModels()
    private lateinit var mContext: Context

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
                    AddCropsFragmentScreen(
                        viewModel = viewModel,
                        onSearch = this@AddCropsFragment::searchCrops,
                        onClose = this@AddCropsFragment::fetchCrops,
                        onDone = this@AddCropsFragment::onBack
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
        savedState: Bundle?,
    ): View? {
        return null
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        mContext = requireContext()
        fetchCropsGlobalIndicators()
    }

    private fun fetchCropsGlobalIndicators() {
        val task = GetCropsDivisionsTask()
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage(mContext.getString(R.string.loading_crops_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    val list = data.toList() as List<GlobalIndicatorDTO>?
                    if (!list.isNullOrEmpty()) {
                        viewModel.allCropDivisions.clear()
                        viewModel.allCropDivisions.addAll(list)
                        val names = viewModel.allCropDivisions.flatMap { listOf(it.name) }
                        viewModel.tabs.clear()
                        viewModel.tabs.addAll(names)
                        fetchCrops()
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {}
        })
        task.execute()
    }


    private fun fetchCrops() {
        viewModel.allCrops.postValue(emptyMap())
        DataHolder.getInstance().cropArrayList?.let { list ->
            if (list.isNotEmpty()) {
                CoroutineScope(Dispatchers.Default).launch {
                    val map = list.groupBy { it.cropDivision }
                    map.values.forEach { it.sortedBy { crop -> crop.cropName } }
                    viewModel.allCrops.postValue(map)
                }
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
                    val list = data.toList() as List<CropMaster>?
                    if (!list.isNullOrEmpty()) {
                        CoroutineScope(Dispatchers.Default).launch {
                            val map = list.groupBy { it.cropDivision }
                            map.values.forEach { it.sortedBy { crop -> crop.cropName } }
                            viewModel.allCrops.postValue(map)
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {}
        })
        task.execute()
    }


    private fun searchCrops() {
        viewModel.allCrops.postValue(emptyMap())
        val task = GetSearchedCropsTask()
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage(mContext.getString(R.string.loading_crops_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    val list = data.toList() as List<CropMaster>?
                    if (!list.isNullOrEmpty()) {
                        viewModel.allCrops.postValue(list.groupBy { it.cropDivision })
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {}
        })
        task.execute(viewModel.searchText.value)
    }


    private fun onBack() {
        (activity as MainActivity).navController.popBackStack()
    }
}