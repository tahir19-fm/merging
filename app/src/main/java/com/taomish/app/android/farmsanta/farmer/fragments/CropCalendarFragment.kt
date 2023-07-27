package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.background.GetCropCalendarStagesTask
import com.taomish.app.android.farmsanta.farmer.background.GetCropCalendarsByUserId
import com.taomish.app.android.farmsanta.farmer.background.GetCropStageCalendarByCropId
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.CropCalendarFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.CropCalendar
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.CropStageCalendar
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST", "unused")
class CropCalendarFragment : FarmSantaBaseFragment() {

    private val viewModel by activityViewModels<CropCalendarViewModel>()
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
            CoroutineScope(Dispatchers.Main).launch {
                setContent {
                    MyApp {
                        CropCalendarFragmentScreen(
                            viewModel = viewModel,
                            getCropCalendarsForCrop = this@CropCalendarFragment::fetchCropStageCalendars,
                            goToAddCropCalendarFragment = this@CropCalendarFragment::goToAddCropCalendarFragment,
                            goToCroppingStageDetailsFragment = this@CropCalendarFragment::goToCroppingStageDetailsFragment
                        )
                    }
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
        viewModel.cropCalendarCrops.clear()
        viewModel.date.postValue(null)
        viewModel.userCrops.clear()
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
                    val list = data as? List<GlobalIndicatorDTO>
                    if (!list.isNullOrEmpty()) {
                        viewModel.stagesMap.putAll(list.associateBy { it.uuid })
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {

            }

            override fun onTaskComplete() {
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

}