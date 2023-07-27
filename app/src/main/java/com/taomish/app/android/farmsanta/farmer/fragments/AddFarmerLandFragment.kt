package com.taomish.app.android.farmsanta.farmer.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.FarmerMapSelectActivity
import com.taomish.app.android.farmsanta.farmer.adapters.LandDocNameAdapter
import com.taomish.app.android.farmsanta.farmer.background.GetWaterSourceTask
import com.taomish.app.android.farmsanta.farmer.background.SaveFarmerTask
import com.taomish.app.android.farmsanta.farmer.background.UploadLandDocTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.constants.UserConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentAddLandDetailsBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Area
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Coordinate
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Land
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.utils.FileUtil
import java.io.File

class AddFarmerLandFragment : FarmSantaBaseFragment() {
    private var imageViewClose: ImageView? = null
    private var buttonSave: Button? = null
    private var editTextRegNum: EditText? = null
    private var editTextArea: EditText? = null
    private var uploadButton: TextView? = null
    private var autoCompleteTextViewWaterSource: MaterialAutoCompleteTextView? = null
    private var textInputLayoutRegNum: TextInputLayout? = null
    private var textInputLayoutArea: TextInputLayout? = null
    private var textInputLayoutWaterSource: TextInputLayout? = null
    private var landDocsRecyclerView: RecyclerView? = null
    private var data: Land? = null
    private var mFarmer: Farmer? = null
    private var isEditLand = false
    private var mContext: Context? = null
    private val waterSourceList = ArrayList<String>()
    private var fileArrayList: ArrayList<File>? = null
    private var docUrlList: ArrayList<String>? = null
    private var waterSource: ArrayList<GlobalIndicatorDTO>? = null
    private var adapter: LandDocNameAdapter? = null
    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
    ): View {
        val contextThemeWrapper: Context = ContextThemeWrapper(
            activity, R.style.AppThemeMaterial
        )
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return localInflater.inflate(R.layout.fragment_add_land_details, container, false)
    }


    override fun initViewsInLayout() {
        imageViewClose = initThisView(R.id.addLandDetails_image_close)
        buttonSave = initThisView(R.id.addLandDetails_button_save)
        editTextArea = initThisView(R.id.addLandDetails_textInputEdit_area)
        editTextRegNum = initThisView(R.id.addLandDetails_textInputEdit_regNum)
        autoCompleteTextViewWaterSource = initThisView(R.id.addLandDetails_autoComplete_waterSource)
        textInputLayoutRegNum = initThisView(R.id.addLandDetails_textInputLayout_regNum)
        textInputLayoutArea = initThisView(R.id.addLandDetails_textInputLayout_area)
        textInputLayoutWaterSource = initThisView(R.id.addLandDetails_textInputLayout_waterSource)
        uploadButton = initThisView(R.id.addLandDetails_button_upload)
        landDocsRecyclerView = initThisView(R.id.addLandDetails_recyclerview_docs)
    }

    override fun initListeners() {
        imageViewClose!!.setOnClickListener { v: View? -> closeFragment() }
        buttonSave!!.setOnClickListener { v: View? -> onSaveButtonClick() }
        autoCompleteTextViewWaterSource!!.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    setAdapterForWaterSource()
                    autoCompleteTextViewWaterSource!!.showDropDown()
                }
            }
        uploadButton!!.setOnClickListener { v: View? -> onUploadClick() }
    }

    override fun initData() {
        mContext = requireContext()
        mFarmer = (requireActivity() as FarmerMapSelectActivity).farmer
        fetchWaterSource()
        autoCompleteTextViewWaterSource!!.threshold = 1
        val selectedLandIndex = (requireActivity() as FarmerMapSelectActivity).selectedLandIndex
        if (selectedLandIndex != -1) {
            val farmer = mFarmer
            data = farmer?.lands?.get(selectedLandIndex)
            isEditLand = true
            loadEditLandDetails()
        }
    }

    private fun onUploadClick() {
        val chooserTitle = "Choose a file"
        var chooseFile = Intent(Intent.ACTION_GET_CONTENT)
        chooseFile.type = "*/*"
        chooseFile = Intent.createChooser(chooseFile, chooserTitle)
        someActivityResultLauncher.launch(chooseFile)
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    private var someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val uri = data.data
                val fileUtil = FileUtil()
                val filePath = fileUtil.copyFile(mContext, data.data)
                if (filePath != null) {
                    val file = File(filePath)
                    if (fileArrayList == null) fileArrayList = ArrayList()
                    fileArrayList!!.add(file)
                    //TODO
                    // upload the list to service
                    // and save the result to farmer profile
                }
            }
        }
    }

    private fun uploadLandDocToServer() {
//        UploadFilesTask task                    = new UploadFilesTask(fileArrayList, requireContext());
        val task = UploadLandDocTask(fileArrayList!!)
        task.context = requireContext()
        task.setLoadingMessage("Uploading picture")
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    docUrlList = data as ArrayList<String>
                }
                saveLandDetails()
                // saveUserProfileService(mFarmer);
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        //        task.begin();
        task.execute()
    }

    private fun saveUserProfileService(selectedFarmer: Farmer) {
        val task = SaveFarmerTask(selectedFarmer)
        task.context = requireContext()
        task.setLoadingMessage("Saving profile")
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Farmer) {
                    mFarmer!!.uuid = data.uuid
                }
                saveLandDetails()
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    private fun loadEditLandDetails() {
        editTextRegNum!!.setText(data!!.registrationNumber)
        editTextArea!!.setText(data!!.area.unit.toString())
        autoCompleteTextViewWaterSource!!.setText(data!!.waterSource)
        if (data!!.documents != null) {
            setLandDocsAdapter()
        }
    }

    private fun setLandDocsAdapter() {
        landDocsRecyclerView!!.layoutManager = LinearLayoutManager(requireContext())
        adapter = LandDocNameAdapter(mContext!!, data!!.documents)
        landDocsRecyclerView!!.adapter = adapter
    }

    private fun setAdapterForWaterSource() {
        waterSourceList.clear()
        for (globalIndicator in waterSource!!) {
            waterSourceList.add(globalIndicator.name)
        }
        val waterSourceAdapter = ArrayAdapter(
            mContext!!,
            android.R.layout.simple_dropdown_item_1line,
            android.R.id.text1,
            waterSourceList
        )
        autoCompleteTextViewWaterSource!!.setAdapter(waterSourceAdapter)
    }

    private fun onSaveButtonClick() {
        if (TextUtils.isEmpty(editTextArea!!.text)) {
            textInputLayoutArea!!.error = "Area required"
        } else if (TextUtils.isEmpty(autoCompleteTextViewWaterSource!!.text)) {
            textInputLayoutWaterSource!!.error = "What is the water source?"
        } else {
            // upload file upload
            if (fileArrayList != null && fileArrayList!!.size > 0) uploadLandDocToServer() else  //TODO
            // Change to saveUserProfileService
                saveLandDetails()
        }
    }

    private fun fetchWaterSource() {
        if (DataHolder.getInstance().waterSourceList != null) {
            waterSource = DataHolder.getInstance().waterSourceList
        } else {
            val task = GetWaterSourceTask()
            task.context = requireContext()
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    onWaterSourceApiResultSuccess(data)
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.execute()
        }
    }

    private fun onWaterSourceApiResultSuccess(data: Any) {
        if (data is ArrayList<*>) {
            waterSource = data as ArrayList<GlobalIndicatorDTO>
            DataHolder.getInstance().waterSourceList =
                waterSource
            setAdapterForWaterSource()
        }
    }

    private fun saveLandDetails() {
        val land: Land
        var area: Area?
        val regNumber = editTextRegNum!!.text.toString()
        val landArea = editTextArea!!.text.toString()
        val waterSource = autoCompleteTextViewWaterSource!!.text.toString()
        var landIndex = (requireActivity() as FarmerMapSelectActivity).selectedLandIndex
        if (isEditLand && mFarmer?.lands?.isEmpty()?.not() == true) {
            land = mFarmer!!.lands[0]
            area = land.area
            if (area == null) area = Area()
            area.unit = landArea.toDouble()
            area.uom = UserConstants.AREA_UOM_HECTARE
            land.registrationNumber = regNumber
            land.area = area
            land.waterSource = waterSource
            mFarmer!!.lands[landIndex] = land
        } else {
            land = Land()
            area = Area()
            area.unit = landArea.toDouble()
            area.uom = UserConstants.AREA_UOM_HECTARE
            land.registrationNumber = regNumber
            land.area = area
            land.waterSource = waterSource
            land.documents = docUrlList
            land.landName = (requireActivity() as FarmerMapSelectActivity).farmName
            land.farmLocation = (requireActivity() as FarmerMapSelectActivity).farmLocationName
            val list = (requireActivity() as FarmerMapSelectActivity).markerOptionsArrayList
            val coordinates = ArrayList<Coordinate>()
            for (i in list.indices) {
                val mo = list[i]
                val coordinate = Coordinate()
                coordinate.index = i
                coordinate.latitude = mo.position.latitude
                coordinate.longitude = mo.position.longitude
                coordinates.add(coordinate)
            }
            land.coordinates = coordinates
            val center = getCenter(list)
            if (center != null) {
                land.latitude = center.latitude
                land.longitude = center.longitude
            }
            if (mFarmer == null) mFarmer = Farmer()
            var lands = mFarmer!!.lands
            if (lands == null) lands = ArrayList()
            lands.add(land)
            mFarmer!!.lands = lands
            landIndex = lands.size - 1
        }
        (requireActivity() as FarmerMapSelectActivity)
            .updateMap(AppConstants.DataTransferConstants.KEY_MAP_LAND_SAVED, landIndex)
        (requireActivity() as FarmerMapSelectActivity).farmer = mFarmer
    }

    private fun closeFragment() {}
    private fun getCenter(markerOptionsArrayList: ArrayList<MarkerOptions>?): LatLng? {
        var centerLatLng: LatLng? = null
        if (markerOptionsArrayList != null && markerOptionsArrayList.size > 2) {
            val builder = LatLngBounds.Builder()
            for (i in markerOptionsArrayList.indices) {
                builder.include(markerOptionsArrayList[i].position)
            }
            val bounds = builder.build()
            centerLatLng = bounds.center
        }
        return centerLatLng
    }
}