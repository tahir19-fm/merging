package com.taomish.app.android.farmsanta.farmer.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.activities.FarmerMapSelectActivity
import com.taomish.app.android.farmsanta.farmer.adapters.CultivarAdapter
import com.taomish.app.android.farmsanta.farmer.background.*
import com.taomish.app.android.farmsanta.farmer.background.db.GetCultivarByIdFromDBTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.constants.CropConstants
import com.taomish.app.android.farmsanta.farmer.fragments.dialog.FarmSantaDatePickerDialog
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.cultivar.Cultivar
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Area
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Crop
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil

class AddFarmerCropFragment : FarmSantaBaseFragment() {
    private var imageViewClose: ImageView? = null
    private var textInputLayoutCurrentCrop: TextInputLayout? = null
    private var textInputLayoutSowingDate: TextInputLayout? = null
    private var textInputLayoutArea: TextInputLayout? = null
    private var textInputLayoutInterCrop: TextInputLayout? = null
    private var textInputLayoutSowingDateInterCrop: TextInputLayout? = null
    private lateinit var editTextSowingDate: EditText
    private lateinit var editTextExpectedYield: EditText
    private lateinit var editTextLastYield: EditText
    private var editTextInterCrop: MaterialAutoCompleteTextView? = null
    private lateinit var editTextSowingDateInterCrop: EditText
    private lateinit var editTextArea: EditText
    private lateinit var editTextPlantSpacing: EditText
    private lateinit var editTextRowSpacing: EditText
    private var autoCompleteTextViewCurrentCrop: MaterialAutoCompleteTextView? = null
    private var autoCompleteTextViewLastCrop: MaterialAutoCompleteTextView? = null
    private var autoCompleteTextViewInterCropDuration: MaterialAutoCompleteTextView? = null
    private var autoCompleteTextViewCultivarType: AutoCompleteTextView? = null
    private var autoCompleteTextViewCultivarDuration: AutoCompleteTextView? = null
    private var autoCompleteTextViewCultivar: AutoCompleteTextView? = null
    private var linearInterCrop: LinearLayout? = null
    private var textViewAddInterCrop: TextView? = null
    private var buttonSave: Button? = null
    private var currentCropSelected: Crop? = null
    private var lastCropSelected: Crop? = null
    private var interCropSelected: Crop? = null
    private var isEditCrop = false
    private var selectedFarmer: Farmer? = null
    private var selectedCultivar: Cultivar? = null
    private var selectedCultivarType: GlobalIndicatorDTO? = null
    private var selectedCultivarDuration: GlobalIndicatorDTO? = null
    private var interCropCultivarDuration: GlobalIndicatorDTO? = null
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
        return localInflater.inflate(R.layout.fragment_add_crop_details, container, false)
    }

    override fun initViewsInLayout() {
        imageViewClose = initThisView(R.id.addCropDetails_image_close)
        textInputLayoutCurrentCrop = initThisView(R.id.addCropDetails_textInputLayout_currentCrop)
        textInputLayoutSowingDate = initThisView(R.id.addCropDetails_textInputLayout_date)
        textInputLayoutArea = initThisView(R.id.addCropDetails_textInputLayout_area)
        textInputLayoutInterCrop = initThisView(R.id.addCropDetails_textInputLayout_interCrop)
        textInputLayoutSowingDateInterCrop =
            initThisView(R.id.addCropDetails_textInputLayout_interCropDate)
        autoCompleteTextViewCurrentCrop =
            initThisView(R.id.addCropDetails_textInputEdit_currentCrop)
        autoCompleteTextViewLastCrop = initThisView(R.id.addCropDetails_textInputEdit_lastCrop)
        autoCompleteTextViewInterCropDuration =
            initThisView(R.id.addCropDetails_textInputEdit_interCropDuration)
        editTextSowingDate = initThisView(R.id.addCropDetails_textInputEdit_date)
        editTextPlantSpacing = initThisView(R.id.addCropDetails_textInputEdit_plantSpacing)
        editTextExpectedYield = initThisView(R.id.addCropDetails_textInputEdit_expected)
        editTextLastYield = initThisView(R.id.addCropDetails_textInputEdit_lastYield)
        editTextInterCrop = initThisView(R.id.addCropDetails_textInputEdit_interCrop)
        editTextSowingDateInterCrop = initThisView(R.id.addCropDetails_textInputEdit_interCropDate)
        editTextArea = initThisView(R.id.addCropDetails_textInputEdit_area)
        editTextRowSpacing = initThisView(R.id.addCropDetails_textInputEdit_rowSpacing)
        autoCompleteTextViewCultivarType = initThisView(R.id.addCropDetails_autoComplete_cultivar)
        autoCompleteTextViewCultivarDuration =
            initThisView(R.id.addCropDetails_autoComplete_cultivarDuration)
        autoCompleteTextViewCultivar = initThisView(R.id.addCropDetails_autoComplete_cultivarName)
        autoCompleteTextViewInterCropDuration =
            initThisView(R.id.addCropDetails_textInputEdit_interCropDuration)
        linearInterCrop = initThisView(R.id.addCropDetails_linear_interCropContainer)
        textViewAddInterCrop = initThisView(R.id.addCropDetails_text_addInterCrop)
        buttonSave = initThisView(R.id.addCropDetails_button_save)
    }

    override fun initListeners() {
        imageViewClose!!.setOnClickListener { closeFragment() }
        buttonSave!!.setOnClickListener { onSaveClick() }
        textViewAddInterCrop!!.setOnClickListener { onAddInterCropClick(true) }
        editTextSowingDate.onFocusChangeListener =
            OnFocusChangeListener { _: View?, b: Boolean ->
                if (b) {
                    openDatePicker(editTextSowingDate)
                }
            }
        editTextSowingDate.setOnClickListener { openDatePicker(editTextSowingDate) }
        autoCompleteTextViewLastCrop!!.threshold = 1
        autoCompleteTextViewLastCrop!!.onFocusChangeListener =
            OnFocusChangeListener { _: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    autoCompleteTextViewLastCrop!!.showDropDown()
                }
            }
        autoCompleteTextViewLastCrop!!.setOnClickListener { autoCompleteTextViewLastCrop!!.showDropDown() }
        autoCompleteTextViewCurrentCrop!!.threshold = 1
        autoCompleteTextViewCurrentCrop!!.onFocusChangeListener =
            OnFocusChangeListener { _: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    autoCompleteTextViewCurrentCrop!!.showDropDown()
                }
            }
        autoCompleteTextViewCurrentCrop!!.setOnClickListener { autoCompleteTextViewCurrentCrop!!.showDropDown() }
        editTextInterCrop!!.threshold = 1
        editTextInterCrop!!.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    editTextInterCrop!!.showDropDown()
                }
            }
        editTextInterCrop!!.setOnClickListener { v: View? -> editTextInterCrop!!.showDropDown() }
        autoCompleteTextViewCultivar!!.threshold = 1
        autoCompleteTextViewCultivar!!.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    autoCompleteTextViewCultivar!!.showDropDown()
                }
            }
        autoCompleteTextViewCultivarDuration!!.threshold = 1
        autoCompleteTextViewCultivarDuration!!.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    autoCompleteTextViewCultivarDuration!!.showDropDown()
                }
            }
        autoCompleteTextViewCultivarDuration!!.setOnClickListener { v: View? -> autoCompleteTextViewCultivarDuration!!.showDropDown() }
        autoCompleteTextViewInterCropDuration!!.threshold = 1
        autoCompleteTextViewInterCropDuration!!.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    autoCompleteTextViewInterCropDuration!!.showDropDown()
                }
            }
        autoCompleteTextViewInterCropDuration!!.setOnClickListener { v: View? -> autoCompleteTextViewInterCropDuration!!.showDropDown() }
        autoCompleteTextViewCultivarType!!.threshold = 1
        autoCompleteTextViewCultivarType!!.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    autoCompleteTextViewCultivarType!!.showDropDown()
                }
            }
        autoCompleteTextViewCultivarType!!.setOnClickListener { v: View? -> autoCompleteTextViewCultivarType!!.showDropDown() }
        editTextSowingDateInterCrop.onFocusChangeListener =
            OnFocusChangeListener { view: View?, b: Boolean ->
                if (b) {
                    openDatePicker(editTextSowingDateInterCrop)
                }
            }
        editTextSowingDateInterCrop.setOnClickListener { view: View? ->
            openDatePicker(
                editTextSowingDateInterCrop
            )
        }
    }

    override fun initData() {
        selectedFarmer = (requireActivity() as FarmerMapSelectActivity).farmer
        val selectedLandIndex = (requireActivity() as FarmerMapSelectActivity).selectedLandIndex
        if (selectedLandIndex != -1) {
            isEditCrop = true
            val farmer = selectedFarmer
            val farmerCrops = farmer?.lands?.get(selectedLandIndex)?.crops
            if (farmerCrops != null) {
                for (crop in farmerCrops) {
                    if (crop.cropType != null) {
                        when (crop.cropType) {
                            CropConstants.CURRENT_CROP -> currentCropSelected = crop
                            CropConstants.LAST_CROP -> lastCropSelected = crop
                            CropConstants.INTER_CROP -> interCropSelected = crop
                        }
                    }
                }
            }
            loadEditCropDetails()
        }
        fetchCurrentCrops()
        fetchCultivarDuration()
        fetchCultivarType()
    }

    // on edit crop set main crop and inter crop values
    private fun loadEditCropDetails() {
        if (currentCropSelected != null) {
            autoCompleteTextViewCurrentCrop!!.setText(currentCropSelected!!.cropName)
            editTextSowingDate.setText(DateUtil().getMobileFormat(currentCropSelected!!.sowingDate))
            autoCompleteTextViewCultivarType!!.setText(currentCropSelected!!.cultivationType)
            autoCompleteTextViewCultivar!!.setText(currentCropSelected!!.cultivar)
            autoCompleteTextViewCultivarDuration!!.setText(currentCropSelected!!.stage)
            editTextArea.setText(if (currentCropSelected!!.area != null) currentCropSelected!!.area.unit.toString() else 0.toString())
            editTextPlantSpacing.setText(currentCropSelected!!.plantSpacing.toString())
            editTextRowSpacing.setText(currentCropSelected!!.rowSpacing.toString())
            if (currentCropSelected!!.expectedYield != null) editTextExpectedYield.setText(
                currentCropSelected!!.expectedYield.toString()
            )
            fetchCultivarById(currentCropSelected!!.cultivar)
        }
        if (lastCropSelected != null) {
            autoCompleteTextViewLastCrop!!.setText(lastCropSelected!!.cropName)
            editTextLastYield.setText(lastCropSelected!!.previousYield.toString())
        }
        if (interCropSelected != null) {
            editTextSowingDateInterCrop.setText(DateUtil().getMobileFormat(interCropSelected!!.sowingDate))
            editTextInterCrop!!.setText(interCropSelected!!.cropName)
            autoCompleteTextViewInterCropDuration!!.setText(interCropSelected!!.stage)
            onAddInterCropClick(false)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onAddInterCropClick(scroll: Boolean) {
        if (linearInterCrop!!.visibility == View.VISIBLE) {
            textViewAddInterCrop!!.text     = "+Add an inter crop"
            linearInterCrop!!.visibility = View.GONE
        } else {
            textViewAddInterCrop!!.text = "Hide inter crop"
            linearInterCrop!!.visibility = View.VISIBLE
            if (scroll) {
                editTextSowingDateInterCrop.parent.requestChildFocus(
                    editTextSowingDateInterCrop,
                    editTextSowingDateInterCrop
                )
            }
        }
    }

    private fun onSaveClick() {
        clearAllError()
        if (TextUtils.isEmpty(autoCompleteTextViewCurrentCrop!!.text)) {
            textInputLayoutCurrentCrop!!.error = "Current crop needed"
        } else if (TextUtils.isEmpty(editTextSowingDate.text)) {
            textInputLayoutSowingDate!!.error = "Date missing"
        } else if (TextUtils.isEmpty(editTextArea.text)) {
            textInputLayoutArea!!.error = "Area is needed"
        } else if (linearInterCrop!!.visibility == View.VISIBLE) {
            if (TextUtils.isEmpty(editTextInterCrop!!.text)) {
                textInputLayoutInterCrop!!.error = "Inter crop missing"
            } else if (TextUtils.isEmpty(editTextSowingDateInterCrop.text)) {
                textInputLayoutSowingDateInterCrop!!.error = "Inter crop date is needed"
            } else if (isEditCrop) {
                saveEditCropDetails()
            } else {
                saveDetails()
            }
        } else if (isEditCrop) {
            saveEditCropDetails()
        } else {
            saveDetails()
        }
    }

    private fun openDatePicker(editText: EditText?) {
        val newFragment: DialogFragment =
            FarmSantaDatePickerDialog().setOnDateSelectListener { year: Int, month: Int, date: Int ->
                val mm = if (month < 10) "0$month" else month.toString()
                val dd = if (date < 10) "0$date" else date.toString()
                val dateString = "$dd-$mm-$year"
                editText!!.setText(dateString)
            }
        newFragment!!.show(parentFragmentManager, "DatePicker")
    }

    private fun clearAllError() {
        textInputLayoutCurrentCrop!!.error = null
        textInputLayoutSowingDate!!.error = null
        textInputLayoutArea!!.error = null
        textInputLayoutInterCrop!!.error = null
        textInputLayoutSowingDateInterCrop!!.error = null
    }

    private fun closeFragment() {}
    private fun saveDetails() {
        setCropValues()
    }

    private fun setCropValues() {
        if (currentCropSelected == null) currentCropSelected = Crop()
        if (lastCropSelected == null) lastCropSelected = Crop()

        //crop.setCropName(autoCompleteTextViewCurrentCrop.getText().toString());
        currentCropSelected!!.cultivationType = autoCompleteTextViewCultivarType!!.text.toString()
        currentCropSelected!!.comment = ""
        if (!TextUtils.isEmpty(editTextExpectedYield.text)) currentCropSelected!!.expectedYield =
            java.lang.Double.valueOf(
                editTextExpectedYield.text.toString()
            )
        currentCropSelected!!.sowingDate =
            formatDateToApiAccepted(editTextSowingDate.text.toString())
        currentCropSelected!!.stage = autoCompleteTextViewCultivarDuration!!.text.toString()
        currentCropSelected!!.cultivar =
            if (selectedCultivar != null) selectedCultivar!!.uuid else autoCompleteTextViewCultivar!!.text.toString()
        if (!TextUtils.isEmpty(editTextPlantSpacing.text.toString())) {
            currentCropSelected!!.plantSpacing = editTextPlantSpacing.text.toString().toDouble()
        } else {
            currentCropSelected!!.plantSpacing = 0.0
        }
        if (!TextUtils.isEmpty(editTextRowSpacing.text.toString())) {
            currentCropSelected!!.rowSpacing = editTextRowSpacing.text.toString().toDouble()
        } else {
            currentCropSelected!!.rowSpacing = 0.0
        }
        val area = Area()
        area.unit = editTextArea.text.toString().toDouble()
        area.uom = "ha"
        currentCropSelected!!.area = area
        if (!TextUtils.isEmpty(editTextLastYield.text)) currentCropSelected!!.previousYield =
            java.lang.Double.valueOf(
                editTextLastYield.text.toString()
            )
        currentCropSelected!!.cropType = CropConstants.CURRENT_CROP
        val cropList: MutableList<Crop> = ArrayList()
        cropList.add(currentCropSelected!!)
        if (!TextUtils.isEmpty(autoCompleteTextViewLastCrop!!.text)) {
            lastCropSelected!!.cropId = lastCropSelected!!.cropId
            lastCropSelected!!.cropName = lastCropSelected!!.cropName
            lastCropSelected!!.previousYield =
                java.lang.Double.valueOf("0" + editTextLastYield.text.toString())
            lastCropSelected!!.cropType = CropConstants.LAST_CROP
            cropList.add(lastCropSelected!!)
        }
        if (linearInterCrop!!.visibility == View.VISIBLE) {
            if (interCropSelected == null) interCropSelected = Crop()
            interCropSelected!!.cropName = editTextInterCrop!!.text.toString()
            interCropSelected!!.sowingDate =
                formatDateToApiAccepted(editTextSowingDateInterCrop.text.toString())
            interCropSelected!!.stage = autoCompleteTextViewInterCropDuration!!.text.toString()
            interCropSelected!!.cropType = CropConstants.INTER_CROP
            cropList.add(interCropSelected!!)
        }
        selectedFarmer = (requireActivity() as FarmerMapSelectActivity).farmer
        val farmer = selectedFarmer
        val lands = farmer?.lands
        if (lands != null) {
            val landIndex = (requireActivity() as FarmerMapSelectActivity).selectedLandIndex
            lands[landIndex].crops = cropList
        }
        farmer?.lands = lands
        saveDetailsAPICall()
    }

    private fun formatDateToApiAccepted(dateString: String): String {
        return DateUtil().getApiFormat(dateString)
    }

    private fun saveEditCropDetails() {
        setCropValues()
    }

    private fun saveDetailsAPICall() {
        val task = SaveFarmerTask(selectedFarmer!!)
        task.context = requireContext()
        task.setLoadingMessage("Saving crops")
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Farmer) {
                    selectedFarmer!!.lands = data.lands
                    (requireActivity() as FarmerMapSelectActivity).farmer = selectedFarmer
                    DataHolder.getInstance().selectedFarmer = selectedFarmer
                    goToMapPlotActivity()
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    private fun goToMapPlotActivity() {

    }

    /*Get Cultivar List API*/
    private fun fetchCultivarList(cropId: String) {
        val task = GetCultivarListTask()
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    loadCultivar(data as ArrayList<Cultivar>)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute(cropId)
    }

    private fun fetchCurrentCrops() {
        if (DataHolder.getInstance().cropArrayList != null) {
            loadCurrentCrops()
            loadLastCrops()
            loadInterCrops()
            fetchCultivarList(DataHolder.getInstance().cropArrayList[0].uuid)
        } else {
            val task = GetCropListTask()
            task.context = requireContext()
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    if (data is ArrayList<*>) {
                        DataHolder.getInstance().cropArrayList = data as ArrayList<CropMaster?>
                        loadCurrentCrops()
                        loadLastCrops()
                        loadInterCrops()
                        fetchCultivarList(DataHolder.getInstance().cropArrayList[0].uuid)
                    }
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.execute()
        }
    }

    private fun fetchCultivarType() {
        if (DataHolder.getInstance().cultivarTypeList != null) {
            loadCultivarType(DataHolder.getInstance().cultivarTypeList)
        } else {
            val task = GetCultivarTypeTask()
            task.context = requireContext()
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    DataHolder.getInstance().cultivarTypeList =
                        data as ArrayList<GlobalIndicatorDTO?>
                    loadCultivarType(DataHolder.getInstance().cultivarTypeList)
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.execute()
        }
    }

    private fun fetchCultivarDuration() {
        if (DataHolder.getInstance().cultivarDurationList != null) {
            loadCultivarDuration(DataHolder.getInstance().cultivarDurationList)
        } else {
            val task = GetCultivarDurationTask()
            task.context = requireContext()
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    DataHolder.getInstance().cultivarDurationList =
                        data as ArrayList<GlobalIndicatorDTO?>
                    loadCultivarDuration(DataHolder.getInstance().cultivarDurationList)
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.execute()
        }
    }

    private fun fetchCultivarById(uuid: String) {
        Handler().post {
            val task = GetCultivarByIdFromDBTask()
            task.context = requireContext()
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    if (data is Cultivar) autoCompleteTextViewCultivar!!.setText(data.cultivarName)
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.execute(uuid)
        }
    }

    /*Set crops data to current crop adapter*/
    private fun loadCurrentCrops() {
        val cropArrayList = DataHolder.getInstance().cropArrayList
        val adapter: ArrayAdapter<CropMaster?> = object : ArrayAdapter<CropMaster?>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, android.R.id.text1,
            cropArrayList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var convertView = convertView
                val inflater =
                    requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                if (convertView == null) {
                    convertView = inflater.inflate(
                        R.layout.item_autocomplete, parent,
                        false
                    )
                }
                val view = convertView!!.findViewById<TextView>(R.id.tvCustom)
                if (view != null) {
                    view.text = cropArrayList[position].cropName
                }
                return convertView
            }
        }
        autoCompleteTextViewCurrentCrop!!.setAdapter(adapter)
        adapter.notifyDataSetChanged()
        autoCompleteTextViewCurrentCrop!!.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>, view: View?, position: Int, id: Long ->
                val cropMaster = parent.adapter.getItem(position) as CropMaster
                textInputLayoutCurrentCrop!!.isErrorEnabled = false
                currentCropSelected = Crop()
                currentCropSelected!!.cropId = cropMaster.uuid
                currentCropSelected!!.cropName = cropMaster.cropName
                autoCompleteTextViewCurrentCrop!!.tag = currentCropSelected
                autoCompleteTextViewCurrentCrop!!.setText(currentCropSelected!!.cropName)
                fetchCultivarList(cropMaster.uuid)
            }
    }

    /*Set crops data to last crop adapter*/
    private fun loadLastCrops() {
        val cropArrayList = DataHolder.getInstance().cropArrayList
        val adapter: ArrayAdapter<CropMaster?> = object : ArrayAdapter<CropMaster?>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, android.R.id.text1,
            cropArrayList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var convertView = convertView
                val inflater =
                    requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                if (convertView == null) {
                    convertView = inflater.inflate(
                        R.layout.item_autocomplete, parent,
                        false
                    )
                }
                val view = convertView!!.findViewById<TextView>(R.id.tvCustom)
                if (view != null) {
                    view.text = cropArrayList[position].cropName
                }
                return convertView
            }
        }
        autoCompleteTextViewLastCrop!!.setAdapter(adapter)
        autoCompleteTextViewLastCrop!!.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>, view: View?, position: Int, id: Long ->
                val cropMaster = parent.adapter.getItem(position) as CropMaster
                lastCropSelected = Crop()
                lastCropSelected!!.cropId = cropMaster.uuid
                lastCropSelected!!.cropName = cropMaster.cropName
                autoCompleteTextViewLastCrop!!.tag = lastCropSelected
                autoCompleteTextViewLastCrop!!.setText(lastCropSelected!!.cropName)
            }
    }

    private fun loadInterCrops() {
        val cropArrayList = DataHolder.getInstance().cropArrayList
        val adapter: ArrayAdapter<CropMaster?> = object : ArrayAdapter<CropMaster?>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, android.R.id.text1,
            cropArrayList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var convertView = convertView
                val inflater =
                    requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                if (convertView == null) {
                    convertView = inflater.inflate(
                        R.layout.item_autocomplete, parent,
                        false
                    )
                }
                val view = convertView!!.findViewById<TextView>(R.id.tvCustom)
                if (view != null) {
                    view.text = cropArrayList[position].cropName
                }
                return convertView
            }
        }
        editTextInterCrop!!.setAdapter(adapter)
        editTextInterCrop!!.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>, view: View?, position: Int, id: Long ->
                val cropMaster = parent.adapter.getItem(position) as CropMaster
                interCropSelected = Crop()
                interCropSelected!!.cropId = cropMaster.uuid
                interCropSelected!!.cropName = cropMaster.cropName
                editTextInterCrop!!.tag = interCropSelected
                editTextInterCrop!!.setText(interCropSelected!!.cropName)
            }
    }

    /*Set crops data to current crop adapter*/
    private fun loadCultivar(cultivars: ArrayList<Cultivar>) {
        val adapter = CultivarAdapter(requireContext(), R.layout.item_autocomplete, cultivars)
        autoCompleteTextViewCultivar!!.setAdapter(adapter)
        autoCompleteTextViewCultivar!!.setText("")
        autoCompleteTextViewCultivar!!.onItemClickListener =
            OnItemClickListener { arg0: AdapterView<*>, arg1: View?, arg2: Int, arg3: Long ->
                selectedCultivar = arg0.adapter.getItem(arg2) as Cultivar
                autoCompleteTextViewCultivar!!.tag = selectedCultivar
            }
        if (isEditCrop && currentCropSelected != null) {
            autoCompleteTextViewCultivar!!.setText(getCultivarName(currentCropSelected!!.cultivar))
        }
    }

    /*Set crops data to current crop adapter*/
    private fun loadCultivarType(cultivarTypes: ArrayList<GlobalIndicatorDTO>) {
        val adapter: ArrayAdapter<GlobalIndicatorDTO?> = object : ArrayAdapter<GlobalIndicatorDTO?>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, android.R.id.text1,
            cultivarTypes.toList()
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var convertView = convertView
                val inflater =
                    requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                if (convertView == null) {
                    convertView = inflater.inflate(
                        R.layout.item_autocomplete, parent,
                        false
                    )
                }
                val view = convertView!!.findViewById<TextView>(R.id.tvCustom)
                if (view != null) {
                    view.text = cultivarTypes[position].name
                }
                return convertView
            }
        }
        autoCompleteTextViewCultivarType!!.setAdapter(adapter)
        autoCompleteTextViewCultivarType!!.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>, view: View?, position: Int, id: Long ->
                selectedCultivarType = parent.adapter.getItem(position) as GlobalIndicatorDTO
                autoCompleteTextViewCultivarType!!.tag = selectedCultivar
                autoCompleteTextViewCultivarType!!.setText(selectedCultivarType!!.name)
            }
    }

    /*Set crops data to current crop adapter*/
    private fun loadCultivarDuration(cultivarDurationList: ArrayList<GlobalIndicatorDTO>) {
        val adapter: ArrayAdapter<GlobalIndicatorDTO?> = object : ArrayAdapter<GlobalIndicatorDTO?>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, android.R.id.text1,
            cultivarDurationList.toList()
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var convertView = convertView
                val inflater =
                    requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                if (convertView == null) {
                    convertView = inflater.inflate(
                        R.layout.item_autocomplete, parent,
                        false
                    )
                }
                val view = convertView!!.findViewById<TextView>(R.id.tvCustom)
                if (view != null) {
                    view.text = cultivarDurationList[position].name
                }
                return convertView
            }
        }
        autoCompleteTextViewCultivarDuration!!.setAdapter(adapter)
        autoCompleteTextViewCultivarDuration!!.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>, view: View?, position: Int, id: Long ->
                selectedCultivarDuration = parent.adapter.getItem(position) as GlobalIndicatorDTO
                autoCompleteTextViewCultivarDuration!!.tag = selectedCultivarDuration
                autoCompleteTextViewCultivarDuration!!.setText(selectedCultivarDuration!!.name)
            }
        autoCompleteTextViewInterCropDuration!!.setAdapter(adapter)
        autoCompleteTextViewInterCropDuration!!.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>, view: View?, position: Int, id: Long ->
                interCropCultivarDuration = parent.adapter.getItem(position) as GlobalIndicatorDTO
                autoCompleteTextViewInterCropDuration!!.tag = interCropCultivarDuration
                autoCompleteTextViewInterCropDuration!!.setText(interCropCultivarDuration!!.name)
            }
    }

    private fun getCultivarName(uuid: String): String {
        val cultivarArrayList = DataHolder.getInstance().cultivarArrayList
        if (cultivarArrayList != null) {
            for (cultivar in cultivarArrayList) {
                if (cultivar.uuid == uuid) {
                    selectedCultivar = cultivar
                    return cultivar.cultivarName
                }
            }
        }
        return uuid
    }
}