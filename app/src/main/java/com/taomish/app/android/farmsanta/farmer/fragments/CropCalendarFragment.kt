package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.DeleteCropCalendarTask
import com.taomish.app.android.farmsanta.farmer.background.GetCropCalendarStagesTask
import com.taomish.app.android.farmsanta.farmer.background.GetCropCalendarsByUserId
import com.taomish.app.android.farmsanta.farmer.background.GetCropStageCalendarByCropId
import com.taomish.app.android.farmsanta.farmer.background.UpdateCropCalendarTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseActivity
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.CropCalendarFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.fragments.CropCalendarFragment.GlobalVariables.isInitialCreate
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.ErrorResponse
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.CropCalendar
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.CropStageCalendar
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.utils.asString
import com.taomish.app.android.farmsanta.farmer.utils.getApiFormatDate
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Suppress("UNCHECKED_CAST", "unused")
class CropCalendarFragment : FarmSantaBaseFragment() {

    private val viewModel by activityViewModels<CropCalendarViewModel>()
    private lateinit var mContext: Context
    object GlobalVariables {
        var cropStageId: String = ""
        var isInitialCreate: Boolean = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root
        init()
        if (isInitialCreate) {
            initData()
            isInitialCreate = false
        }

        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            CoroutineScope(Dispatchers.Main).launch {
                setContent {
                    MyApp {
                        CropCalendarFragmentScreen(
                            viewModel = viewModel,
                            getCropCalendarsForCrop = this@CropCalendarFragment::fetchCropStageCalendars,
                            goToAddCropCalendarFragment = this@CropCalendarFragment::goToAddCropCalendarFragment,
                            goToCroppingStageDetailsFragment = this@CropCalendarFragment::goToCroppingStageDetailsFragment,
                            updateCropCalendar = this@CropCalendarFragment::updateCropCalendar,
                            initData = this@CropCalendarFragment::initData
                        )
                    }
                }
            }
        }
        return root
    }

    override fun init() {
        mContext=requireContext()
    }
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
        viewModel.cropCalendarCrops.clear()
        viewModel.date.postValue(null)
        viewModel.userCrops.clear()
        viewModel.visibleData.postValue(false)
        fetchCropCalendarStagesIndicators()
        DataHolder.getInstance().cropArrayList?.let { list ->
            if (list.isNotEmpty()) {
                viewModel.allCrops.postValue(list)
            }
        }
    }

    private fun setCropCalendars() {
        DataHolder.getInstance().cropMasterMap?.let { map ->
            viewModel.cropCalendars.forEach {
                map[it.cropId]?.let { crop -> viewModel.userCrops.add(crop) }
            }
        }
        if (viewModel.cropCalendars.isNotEmpty()) {
            fetchCropStageCalendars()
        }
    }


    private fun fetchCropCalendarStagesIndicators() {
        viewModel.stagesMap.clear()
        val task = GetCropCalendarStagesTask()
        task.context = mContext
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is List<*>) {
                    task.setShowLoading(true)
                    val list = data as? List<GlobalIndicatorDTO>
                    if (!list.isNullOrEmpty()) {
                        viewModel.stagesMap.putAll(list.associateBy { it.uuid })
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {

            }

            override fun onTaskComplete() {
                task.setShowLoading(true)
                fetchCropCalendars()
            }
        })
        task.execute()
    }


    private fun fetchCropCalendars() {
        viewModel.cropCalendars.clear()
        val task = GetCropCalendarsByUserId()
        task.context = mContext
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    val list = data as ArrayList<CropCalendar>
                    if (list.isNotEmpty()) {
                        viewModel.cropCalendars.addAll(list)
                        viewModel.selectedCropCalendar.postValue(list.firstOrNull())
                        setCropCalendars()
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {}
        })
        task.execute()
    }


    private fun fetchCropStageCalendars() {
        val task = GetCropStageCalendarByCropId()
        task.context = mContext
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    Log.d("CropCalendarFragment", "data is ArrayList<*>")
                    val stages = data as ArrayList<CropStageCalendar>
                    stages.forEachIndexed { idx, cropCalendar ->
                        cropCalendar.weeks.forEachIndexed { _, stageWeek ->
                            if ((stageWeek.weekInfo ?: -1) == 0) {
                                cropCalendar.isCurrentTask = true
                                stageWeek.isCurrentTask = true
                                viewModel.currentTaskIndex = idx
                            }
                        }
                    }
                    Log.d("CropCalendarFragment", "data is stored")
                    viewModel.cropStages.postValue(stages)
                    viewModel.visibleData.postValue(true)
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {}
        })
        task.execute(
            viewModel.selectedCropCalendar.value?.cropId ?: "",
            viewModel.selectedCropCalendar.value?.id.toString()
        )
    }


    private fun goToAddCropCalendarFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_ADD_CROP_CALENDAR)
        }
    }


    private fun goToCroppingStageDetailsFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_CROPPING_STAGE_DETAILS)
        }
    }

    private fun goToFertilizerCalculator() {
        fragmentChangeHelper?.onFragmentChange(
            AppConstants.FragmentConstants.FERTILIZER_CALC_FROM_CROP_CROP_CALENDAR
        )
    }
    private fun updateCropCalendar(getCropId:String,getStageId:Int,getId:Int) {
        val cropCalendar = CropCalendar().apply {
            cropId = getCropId
            stageId = getStageId
            id = getId
            userId = DataHolder.getInstance().selectedFarmer?.userId ?: ""
            startDate = viewModel.date.value?.asString("dd-MM-yyyy").getApiFormatDate(true)
                ?: LocalDate.now().asString("dd-MM-yyyy").getApiFormatDate(true)
        }


        val task = UpdateCropCalendarTask(cropCalendar)
        task.context = mContext
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is CropCalendar) {
                    showToast("Update crop Successfully")
                    initData()
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
                            function = {}
                        )
                }
            }
            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                Toast.makeText(requireContext(),"Something Wrong",Toast.LENGTH_SHORT).show()
                goBack()
            }
        })
        task.execute()
    }

    private fun deleteCropCalendar(uuid: String?) {
        val deleteTask = DeleteCropCalendarTask(uuid ?:"")
        deleteTask.context = requireContext()
        deleteTask.setShowLoading(true)
        deleteTask.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                Toast.makeText(requireContext(),"Crop deleted Successfully",Toast.LENGTH_SHORT).show()
                initData()
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                Toast.makeText(requireContext(),"Something Wrong",Toast.LENGTH_SHORT).show()
                    goBack()
            }
        })
        deleteTask.execute()
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

}