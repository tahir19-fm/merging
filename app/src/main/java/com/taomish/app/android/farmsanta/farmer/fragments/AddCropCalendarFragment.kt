package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.GetCropListTask
import com.taomish.app.android.farmsanta.farmer.background.GetCropsDivisionsTask
import com.taomish.app.android.farmsanta.farmer.background.SaveCropCalendarTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseActivity
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.AddCropCalendarFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.ErrorResponse
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.CropCalendar
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.utils.asString
import com.taomish.app.android.farmsanta.farmer.utils.getApiFormatDate
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Suppress("UNCHECKED_CAST")
class AddCropCalendarFragment : FarmSantaBaseFragment() {

    private val viewModel: CropCalendarViewModel by activityViewModels()
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
                    AddCropCalendarFragmentScreen(
                        viewModel = viewModel,
                        saveCropCalendar = this@AddCropCalendarFragment::saveCropCalendar
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
        viewModel.userCrops.clear()
        viewModel.date.postValue(null)
        val farmer = DataHolder.getInstance().selectedFarmer
        DataHolder.getInstance().cropMasterMap.let { map ->
            map[farmer?.crop1]?.let { viewModel.userCrops.add(it) }
            map[farmer?.crop2]?.let { viewModel.userCrops.add(it) }
            map[farmer?.crop3]?.let { viewModel.userCrops.add(it) }
        }
    }

    private fun fetchCropsGlobalIndicators() {
        viewModel.allCropDivisions.clear()
        val task = GetCropsDivisionsTask()
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage(mContext.getString(R.string.loading_crops_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    val list = data.toList() as List<GlobalIndicatorDTO>?
                    if (!list.isNullOrEmpty()) {
                        viewModel.allCropDivisions.addAll(list.flatMap {
                            listOf(Pair(it.uuid ?: "", it.name ?: ""))
                        })
                        fetchCrops()
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {}
        })
        task.execute()
    }


    private fun fetchCrops() {
        viewModel.allCropsByDivisions.postValue(emptyMap())
        DataHolder.getInstance().cropArrayList?.let { list ->
            if (list.isNotEmpty()) {
                CoroutineScope(Dispatchers.Default).launch {
                    val map = list.groupBy { it.cropDivision }
                    map.values.forEach { it.sortedBy { crop -> crop.cropName } }
                    viewModel.allCropsByDivisions.postValue(map)
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
                    val crops = data.toList() as List<CropMaster>?
                    if (!crops.isNullOrEmpty()) {
                        viewModel.allCrops.postValue(crops)
                        CoroutineScope(Dispatchers.Default).launch {
                            val map = crops.groupBy { it.cropDivision }
                            map.values.forEach { it.sortedBy { crop -> crop.cropName } }
                            viewModel.allCropsByDivisions.postValue(map)
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {}
        })
        task.execute()
    }


    private fun saveCropCalendar() {
        val cropCalendar = CropCalendar().apply {
            cropId = viewModel.selectedCrop.value?.uuid ?: ""
            userId = DataHolder.getInstance().selectedFarmer?.userId ?: ""
            startDate = viewModel.date.value?.asString("dd-MM-yyyy").getApiFormatDate(true)
                ?: LocalDate.now().asString("dd-MM-yyyy").getApiFormatDate(true)
        }


        val task = SaveCropCalendarTask(cropCalendar)
        task.context = mContext
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                Log.d("dataofcropcalender", "onTaskSuccess: $data ")
                if (data is CropCalendar) {
                    CropCalendarFragment.GlobalVariables.isInitialCreate = true
                    showToast("Added Successfully")
                    goBack()
                } else {
                    var message: String? = null

                    if (data is ErrorResponse) message =
                        "${data.message} ${viewModel.selectedCrop.value?.cropName}"
                    (requireActivity() as FarmSantaBaseActivity)
                        .alertDialog(
                            message = message ?: (data as String?)
                            ?: getString(R.string.crop_calendar_not_available),
                            cancelable = false,
                            btnOneText = getString(R.string.ok),
                            btnTwoText = "",
                            function = { goBack() }
                        )
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                goBack()
            }
        })
        task.execute()
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

}