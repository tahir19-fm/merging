package com.taomish.app.android.farmsanta.farmer.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.*
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FertilizerCalculatorViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.FertilizerCalculatorFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.*
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

@Suppress("UNCHECKED_CAST")
class FertilizerCalcFragment : FarmSantaBaseFragment() {

    private val viewModel: FertilizerCalculatorViewModel by activityViewModels()

    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle,
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
        setupKeyboardDetection(root)
        init()
        initData()
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    FertilizerCalculatorFragmentScreen(
                        viewModel = viewModel,
                        goToFertilizerRecommendation = {
                            generateFertilizerReport()
                        },
                        goToViewFertilizerRecommendation = {
                            viewModel.fertilizerGeneratedReport = it
                            goToFertilizerRecommendationFragment()
                        }
                    )
                }
            }
        }
        return root
    }

    private fun generateFertilizerReport() {
        val generateFertilizerReportPayload = if (viewModel.isFruitCropSelected)
            GenerateFertilizerReportPayload(
                ageOfPlant = viewModel.selectedFruitCrop.value?.planting,
                cropType = 4,
                cropPriority = viewModel.selectedFruitCrop.value?.priority?.toInt(),
                area = viewModel.cropArea.value.toDoubleOrNull()
            )
        else
            GenerateFertilizerReportPayload(
                area = viewModel.cropArea.value.toDoubleOrNull(),
                cropId = viewModel.selectedCrop.value?.cropId,
                cropType = viewModel.selectedCrop.value?.fertilizerType?.toIntOrNull(),
                nitrogenousFertilizer = viewModel.nitrogenousSelected.value.id,
                nitrogenLevelN = viewModel.nitrogenLevel.value,
                nitrogenN = viewModel.nitrogenTextField.value.toDoubleOrNull(),
                npkFertilizer = viewModel.npkComplexSelected.value.id,
                photassiumLevelK = viewModel.potassiumLevel.value,
                photassiumK = viewModel.potassiumTextField.value.toDoubleOrNull(),
                potassiumFertilizer = viewModel.potassicSelected.value.id,
                zincFertilizer = viewModel.zincSelected.value.id,
                zincLevelZN = viewModel.zincLevel.value,
                zincZN = viewModel.zincTextField.value.toDoubleOrNull(),
                phosphorusFertilizer = viewModel.phosphorousSelected.value.id,
                phosphorusLevelP = viewModel.phosphorousLevel.value,
                phosphorusP = viewModel.phosphorousTextField.value.toDoubleOrNull(),
                boronFertilizer = viewModel.bronSelected.value.id,
                borronLevelB = viewModel.boronLevel.value,
                borronB = viewModel.boronTextField.value.toDoubleOrNull(),
                sulphurS = viewModel.sulphurTextField.value.toDoubleOrNull(),
                sulphurLevelS = viewModel.sulphurLevel.value,
                potentialHydrogenPH = viewModel.pHTextField.value.toDoubleOrNull(),
                potentialHydrogenLevelPH = viewModel.pHLevel.value,
                testReportAvailable = viewModel.hasReport.value
            )
        with(GetFertilizerReportTask(generateFertilizerReportPayload)) {
            context = requireContext()
            setShowLoading(true)
            setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any?) {
                    if (data is FertilizerGeneratedReport) {
                        viewModel.fertilizerGeneratedReport = data
                    }
                    goToFertilizerRecommendationFragment()
                }

                override fun onTaskFailure(reason: String?, errorMessage: String?) {
                    showToast(
                        "GetFertilizerReportTask : ${getString(R.string.reason)} : $reason\n" +
                                "${getString(R.string.message)} : $errorMessage"
                    )
                }

            })
            execute()
        }

    }


    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        fetchFertilizerCrops()
        fetchFertilizerCropDetails()
        fetchFertilizerSourceDetails()
        fetchSavedFertilizerReports()
    }

    private fun fetchFertilizerCropDetails() {
        val task = GetFertilizerFruitCropDetails()
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is List<*>) {
                    viewModel.fertilizerFruitDetails.clear()
                    viewModel.fertilizerFruitDetails.addAll(data as List<FertilizerFruitDetails>)

                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {

            }

        })
        task.execute()
    }

    private fun fetchSavedFertilizerReports() {
        val task = GetFertilizerSavedReports()
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is List<*>) {
                    viewModel.fertilizerReportsSavedList.clear()
                    viewModel.fertilizerReportsSavedList.addAll(
                        data as List<FertilizerGeneratedReport>
                    )
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {

            }

        })
        task.execute()
    }

    private fun fetchFertilizerSourceDetails() {
        if (viewModel.fertilizerSourceDetails.isEmpty()) {
            val task = GetFertilizerSourceDetailsTask()
            task.context = requireContext()
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any?) {
                    if (data is List<*>) {
                        if (data.isNotEmpty()) {
                            viewModel.fertilizerSourceDetails.clear()
                            viewModel.fertilizerSourceDetails.addAll(
                                data as List<FertilizerSourceDetails>
                            )
                        }
                    }
                }

                override fun onTaskFailure(reason: String?, errorMessage: String?) {}

            })
            task.execute()
        }
    }

    private fun setupKeyboardDetection(contentView: View) {
        contentView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            contentView.getWindowVisibleDisplayFrame(r)
            val screenHeight = contentView.rootView.height
            val keypadHeight = screenHeight - r.bottom
            // 0.15 ratio is perhaps enough to determine keypad height.
            viewModel.isKeyboardOpened.value = keypadHeight > screenHeight * 0.15
        }
    }

    private fun goToFertilizerRecommendationFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(AppConstants.FragmentConstants.FRAGMENT_FERTILIZER_RECOMMENDATION)
        }
    }

    private fun fetchCrops() {
        viewModel.allCropsList.clear()
        viewModel.allFertilizerCrops.postValue(emptyMap())
        DataHolder.getInstance().cropArrayList?.let { list ->
            DataHolder.getInstance().setCropMasterMap()
            if (list.isNotEmpty()) {
                viewModel.allCropsList.addAll(list)
                setCropsMap()
                return
            }
        }
        val task = GetCropListTask()
        task.context = requireContext()
        task.setShowLoading(true)
        task.setLoadingMessage(requireContext().getString(R.string.loading_crops_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    val list = data.toList() as List<CropMaster>?
                    if (!list.isNullOrEmpty()) {
                        DataHolder.getInstance().cropArrayList = ArrayList(list)
                        viewModel.allCropsList.addAll(list)
                        DataHolder.getInstance().setCropMasterMap()
                        setCropsMap()
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                showToast(R.string.fetch_crops_error_msg)
            }
        })
        task.execute()
    }


    private fun fetchFertilizerCrops() {
        GetFertilizerCropsTask().apply {
            context = requireContext()
            setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any?) {
                    if (data is FertilizerCropsResponse?) {
                        viewModel.fertilizerCropsResponse.postValue(data)
                        fetchCrops()
                    }
                }

                override fun onTaskFailure(reason: String?, errorMessage: String?) {
                    showToast("GetFertilizerCropsTask : reason: $reason\nmessage: $errorMessage")
                }
            })
            execute()
        }
    }


    private fun setCropsMap() {
        val allCropsMap = mutableMapOf<Int, List<FertilizerCrop>>()
        allCropsMap[1] = viewModel.fertilizerCropsResponse.value?.FIELDCROP?.toList().orEmpty()
        allCropsMap[2] = viewModel.fertilizerCropsResponse.value?.VEGITABLECORP?.toList().orEmpty()
        allCropsMap[3] = viewModel.fertilizerCropsResponse.value?.OILSEEDCROP?.toList().orEmpty()
        allCropsMap[4] = viewModel.fertilizerCropsResponse.value?.FRUITCROP?.toList().orEmpty()
        viewModel.allFertilizerCrops.value = allCropsMap
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearValues()
    }

}
