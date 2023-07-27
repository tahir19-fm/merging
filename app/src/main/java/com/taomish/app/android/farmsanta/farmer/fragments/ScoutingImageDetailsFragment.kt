package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.FarmerMapSelectActivity
import com.taomish.app.android.farmsanta.farmer.adapters.CropNameAdapter
import com.taomish.app.android.farmsanta.farmer.background.*
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Land
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.CategoryItem
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.ScoutImage
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.Weather
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage
import com.taomish.app.android.farmsanta.farmer.models.api.profile.UploadedFile
import com.taomish.app.android.farmsanta.farmer.models.api.weather.Current
import java.io.File
import java.io.IOException

@Suppress("UNCHECKED_CAST")
class ScoutingImageDetailsFragment : FarmSantaBaseFragment() {
    private lateinit var uploadScoutingImagesFab: FloatingActionButton
    private lateinit var bottomSheetCancel: TextView
    private lateinit var farmNameTextView: TextView
    private lateinit var scoutingImageCropName: Spinner
    private lateinit var mScoutingImageStageName: Spinner
    private lateinit var mScoutingImageCategory: Spinner
    private lateinit var mScoutingPlantPart: AutoCompleteTextView
    private lateinit var mScoutingDetailsNotes: EditText
    private var land: Land? = null

    private lateinit var mFile: File

    private var mFarmScouting: FarmScouting? = null
    private var cropStageList: Array<CropStage>? = null
    private var plantPartList: List<CropStage>? = null
    private var cropCategoryList: List<CategoryItem?>? = null


    private lateinit var mUrlPath: String
    private lateinit var mLandId: String
    private var latitude: Float = 360.0f
    private var longitude: Float = 360.0f

    private var mContext: Context? = null
    private lateinit var crop: CropMaster

    private var cropName: String? = null
    private var stageName: String? = null
    private var stageId: String? = null
    private var categoryId: String? = null
    private var plantPart: String? = null

    private lateinit var cropNameAdapter: CropNameAdapter

    private val cropStages = ArrayList<String>()
    private var cropList: ArrayList<CropMaster>? = null
    private var addedCrop: String? = null

    private var selectedFarmer: Farmer? = null

    override fun init() {

    }

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedState: Bundle?
    ): View {
        val contextThemeWrapper: Context = ContextThemeWrapper(activity, R.style.CheckBoxStyle)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return localInflater!!.inflate(R.layout.fragment_scouting_image_details, container, false)
    }

    override fun initViewsInLayout() { }

    override fun initListeners() {
        // upload scouting images captured
        if (DataHolder.getInstance().selectedFarmer != null)
            selectedFarmer = DataHolder.getInstance().selectedFarmer

        uploadScoutingImagesFab.setOnClickListener {
            mFile = File(mUrlPath)

            getScoutDetails()
        }

        // on click cancel go to map screen
        bottomSheetCancel.setOnClickListener {
            (requireActivity() as FarmerMapSelectActivity).navController.popBackStack()
        }

        scoutingImageCropName.setOnFocusChangeListener { _: View?, hasFocus: Boolean ->
            if (hasFocus && scoutingImageCropName.adapter != null) {
                if (cropList != null)
                    loadCrops()

            }
        }
        mScoutingImageStageName.setOnFocusChangeListener { _: View?, hasFocus: Boolean ->
            if (hasFocus && mScoutingImageStageName.adapter != null) {
                loadCropStages()
            }
        }
        mScoutingPlantPart.threshold = 1
        mScoutingPlantPart.setOnClickListener {
            if (mScoutingPlantPart.adapter != null) {
                mScoutingPlantPart.showDropDown()
            }
        }

        mScoutingImageCategory.setOnFocusChangeListener { _: View?, hasFocus: Boolean ->
            if (hasFocus && mScoutingImageStageName.adapter != null) {
                loadCategories()
            }
        }

        if (selectedFarmer?.lands?.firstOrNull { it.landId == mLandId }?.landName != null) {
            farmNameTextView.text = selectedFarmer?.lands?.firstOrNull {
                it.landId == mLandId
            }?.landName.toString()
        } else
            farmNameTextView.text = ""

    }

    override fun initData() {
        mContext = requireContext()
        val selectedLandIndex = (requireActivity() as FarmerMapSelectActivity).selectedLandIndex

        if (selectedLandIndex != -1) {
            land = (requireActivity() as FarmerMapSelectActivity).farmer.lands[selectedLandIndex]
        }
        addedCrop = land?.crops?.getOrNull(0)?.cropName
        // API for crops and crop stage
        fetchCropList()
    }


    /*Get Crop List API*/
    private fun fetchCropList() {

        if (DataHolder.getInstance().cropArrayList != null) {
            cropList = DataHolder.getInstance().cropArrayList
            loadCrops()
            return
        }

        val task = GetCropListTask()
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    cropList = ArrayList()
                    @Suppress("UNCHECKED_CAST")
                    DataHolder.getInstance().cropArrayList = data as ArrayList<CropMaster>
                    cropList!!.addAll(data)
                    loadCrops()
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {

            }
        })
        task.execute()
    }

    private fun loadCrops() {
        cropNameAdapter =
            CropNameAdapter(
                requireContext(),
                cropList
            )
        scoutingImageCropName.adapter = cropNameAdapter

        scoutingImageCropName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                // It returns the clicked item.
                val clickedItem: CropMaster? =
                    parent.getItemAtPosition(position) as CropMaster?
                fetchCropStageList(clickedItem?.uuid ?: "")
                crop = clickedItem!!
                cropName = crop.cropName
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        for (i in 0 until cropList!!.size) {
            if (addedCrop == (cropNameAdapter.getItem(i) as CropMaster).cropName) {
                scoutingImageCropName.setSelection(i)
                fetchCropStageList((cropNameAdapter.getItem(i) as CropMaster).uuid)
                break
            }
        }
    }

    private fun loadCropStages() {
        if (cropStageList.isNullOrEmpty().not()) {
            cropStages.clear()
            for (cropStage in cropStageList!!) {
                cropStages.add(cropStage.name ?: "")
            }
            val stageNameAdapter: Any = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                cropStages
            )
            mScoutingImageStageName.adapter = stageNameAdapter as SpinnerAdapter?
            mScoutingImageStageName.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                    ) {
                        if (parent.getItemAtPosition(position) == null) {
                            return
                        }
                        // It returns the clicked item.
                        val clickedItem: String =
                            parent.getItemAtPosition(position) as String
                        val name: String = clickedItem
                        stageName = name
                        stageId = cropStageList?.get(position)?.uuid
                        fetchCropCategories()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    private fun loadCategories() {
        if (cropCategoryList.isNullOrEmpty().not()) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                cropCategoryList?.map { it?.description } ?: ArrayList<String>()
            )
            mScoutingImageCategory.adapter = adapter
            mScoutingImageCategory.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                    ) {

                        categoryId = cropCategoryList?.get(position)?.name
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    private fun fetchCropCategories() {
        if (cropCategoryList.isNullOrEmpty()) {
            val task = GetCropCategoryTask()
            task.context = requireContext()
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any?) {
                    if (data is List<*>) {
                        cropCategoryList = data as List<CategoryItem?>
                        loadCategories()
                    }
                }

                override fun onTaskFailure(reason: String?, errorMessage: String?) {

                }

            })
            task.execute()
        }
    }



    //validation for saving scouting
    // crop,stage is mandatory fields
    private fun getScoutDetails() {
        when {
            TextUtils.isEmpty(cropName) -> {
                Toast.makeText(requireContext(), "Crop name is mandatory", Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(stageName) -> {
                Toast.makeText(requireContext(), "Stage is missing", Toast.LENGTH_SHORT).show()
            }
            else -> {
                uploadFarmScout()
            }
        }
    }

    @Throws(IOException::class)
    private fun uploadFarmScout() {
        val task = SaveFarmScoutImageTask(mFile)
        task.context = mContext
        task.setLoadingMessage("Saving farm scouting image")
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                val savedFileName = (data as UploadedFile).fileName
                saveFarmScouting(savedFileName)
                saveFarmScoutingService()

            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    private fun saveFarmScouting(fileName: String) {
        //get  file name after upload
        val img = ScoutImage()

        img.image = fileName
        img.plantPart = plantPart
        img.category = categoryId
        img.label = fileName
        img.status = "active"

        val images = mutableListOf(img)
        val weatherAll = DataHolder.getInstance().weatherAll
        val currentWeather: Current? = weatherAll?.weatherDetails?.current
        val weather = Weather(
            System.currentTimeMillis(),
            currentWeather?.temp ?: 0.0,
            longitude,
            latitude,
            currentWeather?.pressure ?: 0.0,
            currentWeather?.humidity ?: 0.0,
            currentWeather?.windSpeed ?: 0.0,
            currentWeather?.uvi ?: 0.0,
            currentWeather?.clouds ?: 0.0,
            currentWeather?.visibility ?: 0.0,
            currentWeather?.rain?.toString() ?: "0"
        )

        // save crop name and stage name to farm scouting api
        if (crop.cropName != null && stageName != null) {
            mFarmScouting = FarmScouting(
                caption = mScoutingDetailsNotes.text.toString(),
                landId = mLandId,
                images = images,
                crop = crop.uuid!!,
                cropStage = stageId!!,
                currentWeather = weather,
                createdBy = selectedFarmer?.uuid,
                farmerId = selectedFarmer?.uuid ?: ""
            )
        }
    }

    private fun hideKeyboard() {
        val inputManager: InputMethodManager = this.requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(mScoutingDetailsNotes.windowToken, 0)
    }

    private fun saveFarmScoutingService() {
        mFarmScouting?.let {
            val task = SaveFarmScoutingTask(mFarmScouting!!)
            task.context = mContext
            task.isCancelable = false
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    hideKeyboard()
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.execute()
        }

    }


    private fun fetchCropStageList(cropId: String) {
        val task = GetCropStageListByCropId(cropId)
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                (data as Array<*>).also {
                    if (it.isNotEmpty()) {
                        cropStageList = it as Array<CropStage>
                        loadCropStages()
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {

            }
        })
        task.execute()
    }

    private fun fetchPlantParts(list: List<CropStage>) {
        plantPartList = list
        val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                list.map { it.name }
        )
        mScoutingPlantPart.setAdapter(adapter)
        mScoutingPlantPart.setOnItemClickListener { adapterView, view, i, l ->
            plantPart = list[i].name
        }
    }
}