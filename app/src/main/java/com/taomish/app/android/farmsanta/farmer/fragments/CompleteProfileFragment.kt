package com.taomish.app.android.farmsanta.farmer.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.ActivityCompat
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hbb20.CountryCodePicker
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.GetRegionsTask
import com.taomish.app.android.farmsanta.farmer.background.GetTerritoryTask
import com.taomish.app.android.farmsanta.farmer.background.SaveFarmerTask
import com.taomish.app.android.farmsanta.farmer.background.UploadProfilePicTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.constants.UserConstants
import com.taomish.app.android.farmsanta.farmer.controller.AppController
import com.taomish.app.android.farmsanta.farmer.controller.DBController
import com.taomish.app.android.farmsanta.farmer.fragments.dialog.FarmSantaDatePickerDialog
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.master.Region
import com.taomish.app.android.farmsanta.farmer.models.api.master.Territory
import com.taomish.app.android.farmsanta.farmer.models.api.profile.Country
import com.taomish.app.android.farmsanta.farmer.models.api.profile.Gender
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import com.taomish.app.android.farmsanta.farmer.utils.FileUtil
import com.taomish.app.android.farmsanta.farmer.utils.ImageUtil
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import kotlin.jvm.internal.Intrinsics

@Suppress("OVERRIDE_DEPRECATION", "UNCHECKED_CAST")
class CompleteProfileFragment : FarmSantaBaseFragment() {
    private var buttonVerify: Button? = null
    private var editTextFirstName: EditText? = null
    private var editTextMiddleName: EditText? = null
    private var editTextLastName: EditText? = null
    private var editTextDob: EditText? = null
    private var autoCompleteTextViewEducation: AutoCompleteTextView? = null
    private var editTextAddress: EditText? = null
    private var editTextPin: EditText? = null
    private var editTextMobile: EditText? = null
    private var imageButtonProfile: ImageButton? = null
    private var autoCompleteTextViewCountry: AutoCompleteTextView? = null
    private var autoCompleteTextViewRegion: AutoCompleteTextView? = null
    private var autoCompleteTextViewState: AutoCompleteTextView? = null
    private var autoCompleteTextViewGender: AutoCompleteTextView? = null
    private var textInputLayoutDob: TextInputLayout? = null
    private var textInputLayoutFirstName: TextInputLayout? = null
    private var textInputLayoutMiddleName: TextInputLayout? = null
    private var textInputLayoutLastName: TextInputLayout? = null
    private var textInputLayoutCountry: TextInputLayout? = null
    private var textInputLayoutRegion: TextInputLayout? = null
    private var textInputLayoutState: TextInputLayout? = null
    private var textInputLayoutPin: TextInputLayout? = null
    private var countryCodePicker: CountryCodePicker? = null
    private var imageViewBack: ImageView? = null
    private var imageViewClose: ImageView? = null
    private var userProfileBitmap: Bitmap? = null
    private val educationAdapter: ArrayAdapter<String>? = null
    private lateinit var countries: Array<String?>
    private lateinit var states: Array<String?>
    private lateinit var genders: Array<String?>
    private lateinit var regions: Array<Region>
    var allowedRegions: MutableList<String?> = ArrayList()
    private lateinit var territories: Array<Territory>
    private val selectedTerritories: MutableList<String> = ArrayList()
    private val selectedRegions: MutableList<String> = ArrayList()
    private var ageOfPerson = 0
    private val mCurrentPhotoPath: String? = null
    private var countryArrayList: ArrayList<Country>? = null
    private var selectedFarmer: Farmer? = null
    override fun init() {
        ageOfPerson = 0
    }

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
    ): View {
        val contextThemeWrapper: Context = ContextThemeWrapper(
            activity, R.style.AppThemeMaterial
        )
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return localInflater.inflate(R.layout.fragment_farmer_profile_1, container, false)
    }

    override fun initViewsInLayout() {
        editTextFirstName = initThisView(R.id.farmerProf1_textInputEdit_firstName)
        editTextMiddleName = initThisView(R.id.farmerProf1_textInputEdit_middleName)
        editTextLastName = initThisView(R.id.farmerProf1_textInputEdit_lastName)
        editTextAddress = initThisView(R.id.profile1_textInputEdit_address)
        editTextDob = initThisView(R.id.farmerProf1_textInputEdit_dob)
        editTextPin = initThisView(R.id.profile1_textInputEdit_pin)
        editTextMobile = initThisView(R.id.profile1_textInputEdit_mobile)
        textInputLayoutDob = initThisView(R.id.farmerProf1_textInputLayout_dob)
        textInputLayoutFirstName = initThisView(R.id.farmerProf1_textInputLayout_firstName)
        textInputLayoutMiddleName = initThisView(R.id.farmerProf1_textInputLayout_middleName)
        textInputLayoutLastName = initThisView(R.id.farmerProf1_textInputLayout_lastName)
        textInputLayoutCountry = initThisView(R.id.profile1_textInputLayout_country)
        textInputLayoutRegion = initThisView(R.id.farmerProfile1_textInputLayout_region)
        textInputLayoutState = initThisView(R.id.profile1_textInputLayout_state)
        textInputLayoutPin = initThisView(R.id.profile1_textInputLayout_pin)
        autoCompleteTextViewCountry = initThisView(R.id.profile1_autoComplete_country)
        autoCompleteTextViewRegion = initThisView(R.id.farmerProfile1_textInputEdit_region)
        autoCompleteTextViewEducation = initThisView(R.id.profile1_autoComplete_education)
        autoCompleteTextViewState = initThisView(R.id.profile1_autoComplete_state)
        autoCompleteTextViewGender = initThisView(R.id.profile1_autoComplete_gender)
        countryCodePicker = initThisView(R.id.profile1_ccp_code)
        imageButtonProfile = initThisView(R.id.profile1_imageButton_image)
        imageViewBack = initThisView(R.id.profile1_image_back)
        imageViewClose = initThisView(R.id.profile1_image_close)
        buttonVerify = initThisView(R.id.farmerProf1_button_save)
    }

    override fun initListeners() {
        imageButtonProfile!!.setOnClickListener { v: View? -> showImageOptions() }
        editTextDob!!.onFocusChangeListener = OnFocusChangeListener { view: View?, b: Boolean ->
            if (b) {
                openDatePicker()
            }
        }
        editTextDob!!.setOnClickListener { view: View? -> openDatePicker() }
        buttonVerify!!.setOnClickListener { view: View? -> userDetails }
        editTextFirstName!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().isNotEmpty()) {
                    textInputLayoutFirstName!!.error = null
                }
            }
        })
        editTextLastName!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().isNotEmpty()) {
                    textInputLayoutLastName!!.error = null
                }
            }
        })
        autoCompleteTextViewState!!.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    autoCompleteTextViewState!!.showDropDown()
                }
            }
        autoCompleteTextViewCountry!!.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    autoCompleteTextViewCountry!!.showDropDown()
                }
            }
        autoCompleteTextViewGender!!.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    setAdapterGender()
                    autoCompleteTextViewGender!!.showDropDown()
                }
            }
        setAdapterEducation()
        autoCompleteTextViewEducation!!.setOnClickListener { v: View? -> autoCompleteTextViewEducation!!.showDropDown() }
        autoCompleteTextViewCountry!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                validateTerritories()
                reloadRegionForTerritory()
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        /*autoCompleteTextViewCountry.setOnItemClickListener((parent, view, position, id) ->
                reloadStateForCountry(((TextView) view).getText().toString()));*/autoCompleteTextViewRegion!!.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                if (!selectedRegions.contains(
                        regions[position].uuid
                    )
                ) {
                    selectedRegions.add(regions[position].uuid)
                }
            }
        autoCompleteTextViewRegion!!.setOnClickListener { v: View? ->
            if (autoCompleteTextViewRegion!!.adapter.count > 0) {
                autoCompleteTextViewRegion!!.showDropDown()
            } else {
                Toast.makeText(requireContext(), "Region not available", Toast.LENGTH_SHORT).show()
            }
        }
        imageViewBack!!.setOnClickListener { v: View? -> requireActivity().onBackPressed() }
        imageViewClose!!.setOnClickListener { v: View? -> goToLandingFragment() }
    }

    private fun validateTerritories() {
        selectedTerritories.clear()
        val addedTerritories =
            autoCompleteTextViewCountry!!.text.toString().split(",").toTypedArray()
        for (territory in addedTerritories) {
            if (!territory.trim { it <= ' ' }.isEmpty()) {
                for (territory1 in territories) {
                    if (territory1.territoryName != null && territory1.territoryName == territory.trim { it <= ' ' }) {
                        if (!selectedTerritories.contains(territory1.uuid)) {
                            selectedTerritories.add(territory1.uuid)
                        }
                        break
                    }
                }
            }
        }
        setTerritoriesAdapter(territories, selectedTerritories)
    }

    private fun reloadRegionForTerritory() {
        selectedRegions.clear()
        autoCompleteTextViewCountry!!.text.clear()
        allowedRegions.clear()
        if (selectedTerritories.isEmpty()) {
            for (region in regions) {
                allowedRegions.add(region.regionName)
            }
        } else {
            for (region in regions) {
                val name = region.territory
                if (name != null && selectedTerritories.contains(name)) {
                    allowedRegions.add(region.regionName)
                }
            }
        }
        setRegionsAdapter(allowedRegions.toTypedArray(), selectedRegions)
    }

    override fun initData() {
        //textViewLogin.setText(Html.fromHtml(getString(R.string.login_text)));
//        getCountryListFromAssets();
        gendersFromAssets
        setAdapterEducation()
        fetchTerritory()
        fetchRegionList()
        autoCompleteTextViewCountry!!.threshold = 1
        autoCompleteTextViewState!!.threshold = 1
        autoCompleteTextViewGender!!.threshold = 1
        autoCompleteTextViewEducation!!.threshold = 1
        if (DataHolder.getInstance().selectedFarmer != null) {
            selectedFarmer = DataHolder.getInstance().selectedFarmer
            loadFarmerDetails()
        }
        userPhoneNumber
        userProfilePicture
    }

    private fun fetchTerritory() {
        val dbController = DBController(requireContext())
        dbController.masterDao.getAllTerritories()
            .observe(viewLifecycleOwner) { data: List<Territory?> ->
                if (!data.isEmpty()) {
                    loadTerritories(data)
                }
            }
        val task = GetTerritoryTask(dbController)
        task.context = requireContext()
        task.setShowLoading(false)
        task.execute()
    }

    private fun loadTerritories(data: Any) {
        if (data is ArrayList<*>) {
            territories = (data as List<Territory>).toTypedArray()
            setTerritoriesAdapter(territories, selectedTerritories)
        }
    }

    private fun setTerritoriesAdapter(
        territories: Array<Territory>,
        selectedTerritories: List<String>
    ) {
        val territoriesData = arrayOfNulls<String>(territories.size)
        var pos = 0
        for (territory in territories) {
            val tName = territory.uuid
            if (tName != null && !selectedTerritories.contains(tName)) {
                territoriesData[pos] = territory.territoryName
                ++pos
            }
        }
        setTerritoriesAdapter(territoriesData)
    }

    private fun setTerritoriesAdapter(territoriesData: Array<String?>) {
        val regionNameAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            territoriesData
        )
        autoCompleteTextViewCountry!!.threshold = 1
        //        autoCompleteTextViewCountry.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        autoCompleteTextViewCountry!!.setAdapter(regionNameAdapter)
    }

    private fun fetchRegionList() {
        val dbController = DBController(requireContext())
        dbController.masterDao.getAllRegions().observe(viewLifecycleOwner) { data: List<Region?> ->
            if (!data.isEmpty()) {
                loadRegions(data.toTypedArray())
            }
        }
        val task = GetRegionsTask(dbController)
        task.context = requireContext()
        task.setShowLoading(false)
        task.execute()
    }

    private fun loadRegions(data: Any) {
        if (data is ArrayList<*>) {
            (data as ArrayList<Region>).also { regions = it.toTypedArray() }
            val regionsData = arrayOfNulls<String>(regions.size)
            for (i in regions.indices) {
                regionsData[i] = regions[i].regionName
            }
            setRegionsAdapter(regionsData, selectedRegions)
        }
    }

    private fun setRegionsAdapter(regionsData: Array<String?>, selected: List<String>) {
        val filtered: MutableList<String> = ArrayList()
        for (rName in regionsData) {
            if (rName != null && !selected.contains(rName.trim { it <= ' ' })) {
                filtered.add(rName)
            }
        }
        val regionNameAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            filtered.toTypedArray()
        )
        //        editTextCounty.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        autoCompleteTextViewRegion!!.setAdapter(regionNameAdapter)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == AppConstants.StartActivityConstants.CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                //Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Camera permission denied")
            }
        } else if (requestCode == AppConstants.StartActivityConstants.GALLERY_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                //Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Gallery storage permission denied")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.StartActivityConstants.CAMERA_REQUEST) {
                if (data != null && data.extras != null) {
                    val bmp = data.extras!!["data"] as Bitmap?
                    val stream = ByteArrayOutputStream()
                    bmp?.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val byteArray = stream.toByteArray()
                    // convert byte array to Bitmap
                    userProfileBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    imageButtonProfile!!.setImageBitmap(userProfileBitmap)
                }
            } else if (requestCode == AppConstants.StartActivityConstants.GALLERY_REQUEST) {
                val selectedImage = data!!.data
                val path = FileUtil().copyFile(requireContext(), selectedImage)
                userProfileBitmap = BitmapFactory.decodeFile(path)
                try {
                    userProfileBitmap = ImageUtil().rotateIfImageNeeded(userProfileBitmap, path)
                } catch (ignored: IOException) {
                }
                imageButtonProfile!!.setImageBitmap(userProfileBitmap)

                //performCrop(picturePath);
            } else if (requestCode == AppConstants.StartActivityConstants.CROP_PIC) {
                val extras = data!!.extras
                if (extras != null) {
                    userProfileBitmap = extras.getParcelable("data")
                    // Set The Bitmap Data To ImageView
                    imageButtonProfile!!.setImageBitmap(userProfileBitmap)
                    imageButtonProfile!!.scaleType = ImageView.ScaleType.FIT_XY
                }
            }
        }
    }

    private val countryListFromAssets: Unit
        private get() {
            val countriesJsonString = AppController()
                .loadJSONFromAsset(
                    requireActivity(),
                    "resources/countries.json"
                )
            val gson = GsonBuilder().create()
            countryArrayList = gson.fromJson(
                countriesJsonString,
                object : TypeToken<ArrayList<Country?>?>() {}.type
            )
            val stateList = ArrayList<String>()
            val countryList = ArrayList<String>()
            for (country in countryArrayList!!) {
                countryList.add(country.name)
                for (state in country.states) {
                    stateList.add(state.name)
                }
            }
            countries = arrayOfNulls(countryList.size)
            countries = countryList.toArray(countries)
            states = arrayOfNulls(stateList.size)
            states = stateList.toArray(states)
            setAdapterForCountryAndState()
        }

    private fun reloadStateForCountry(countryName: String) {
        val stateList = ArrayList<String>()
        for (country in countryArrayList!!) {
            if (country.name == countryName) {
                for (state in country.states) {
                    stateList.add(state.name)
                }
                break
            }
        }
        states = arrayOfNulls(stateList.size)
        states = stateList.toArray(states)
        autoCompleteTextViewState!!.setText("")
        setAdapterForCountryAndState()
    }

    private fun setAdapterForCountryAndState() {
        val countriesAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            countries
        )
        val statesAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            states
        )
        autoCompleteTextViewState!!.setAdapter(statesAdapter)
        autoCompleteTextViewCountry!!.setAdapter(countriesAdapter)
    }

    private val userPhoneNumber: Unit
        private get() {
            val appPrefs = AppPrefs(requireContext())
            val countryCode = appPrefs.countryCode
            val phoneNumber = appPrefs.phoneNumber
            if (!TextUtils.isEmpty(countryCode)) countryCodePicker!!.setCountryForPhoneCode(
                countryCode.toInt()
            )
            editTextMobile!!.setText(phoneNumber)
        }
    private val userProfilePicture: Unit
        private get() {
            val userProfile = DataHolder.getInstance().selectedFarmer
            if (userProfile != null) {
                imageButtonProfile!!.setImageBitmap(userProfileBitmap)
            }
        }

    private fun takePicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    AppConstants.StartActivityConstants.CAMERA_PERMISSION
                )
            } else {
                openCamera()
            }
        } else {
            openCamera()
        }
    }

    private fun takeGalleryPic() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    AppConstants.StartActivityConstants.GALLERY_PERMISSION
                )
            } else {
                openGallery()
            }
        } else {
            openGallery()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, AppConstants.StartActivityConstants.CAMERA_REQUEST)
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*")
        galleryIntent.putExtra("crop", "true")
        galleryIntent.putExtra("scale", true)
        galleryIntent.putExtra("aspectX", 1)
        galleryIntent.putExtra("aspectY", 1)
        startActivityForResult(galleryIntent, AppConstants.StartActivityConstants.GALLERY_REQUEST)
    }

    private fun openDatePicker() {
        val newFragment = FarmSantaDatePickerDialog()
            .setOnDateSelectListener { year: Int, month: Int, date: Int ->
                ageOfPerson = getAge(year, month, date)
                if (ageOfPerson > 14) {
                    val mm = if (month < 10) "0$month" else month.toString()
                    val dd = if (date < 10) "0$date" else date.toString()
                    val dateString = "$dd-$mm-$year"
                    editTextDob!!.setText(dateString)
                    textInputLayoutDob!!.error = null
                } else {
                    editTextDob!!.setText("")
                    textInputLayoutDob!!.error = "Age less than 15!"
                }
            }
        newFragment.show(parentFragmentManager, "DatePicker")
        Handler(Looper.getMainLooper()).postDelayed({
            val dpd = newFragment.dateDialog
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                Log.d(AppConstants.TAG, "Date picker select")
                dpd?.datePicker?.touchables?.get(1)?.performClick()
            } else {
                Log.d(AppConstants.TAG, "Date picker select post lollipop")
                if (dpd != null) {
                    Log.d(AppConstants.TAG, "Date picker not null")
                    dpd.datePicker.touchables[0].performClick()
                }
            }
        }, 500)
    }

    private fun getAge(year: Int, month: Int, date: Int): Int {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()
        dob[Calendar.YEAR] = year
        dob[Calendar.MONTH] = month - 1
        dob[Calendar.DATE] = date
        var age = today[Calendar.YEAR] - year
        val todayDayOfYear = today[Calendar.DAY_OF_YEAR]
        val dobDayOfYear = dob[Calendar.DAY_OF_YEAR]
        if (todayDayOfYear < dobDayOfYear) {
            age--
        }
        return age
    }

    private val userDetails: Unit
        private get() {
            if (TextUtils.isEmpty(editTextFirstName!!.text)) {
                textInputLayoutFirstName!!.error =
                    getString(R.string.text_layout_error_name_required)
            } else if (TextUtils.isEmpty(editTextLastName!!.text)) {
                textInputLayoutLastName!!.error =
                    getString(R.string.text_layout_error_name_required)
            } else if (editTextDob!!.text.toString().length > 0 && ageOfPerson < 15) {
                textInputLayoutDob!!.error = getString(R.string.text_layout_error_age)
            } else if (TextUtils.isEmpty(autoCompleteTextViewCountry!!.text)) {
                textInputLayoutCountry!!.error = getString(R.string.text_layout_error_country)
            } else if (TextUtils.isEmpty(autoCompleteTextViewRegion!!.text)) {
                textInputLayoutRegion!!.error = getString(R.string.text_layout_error_region)
            } else if (TextUtils.isEmpty(autoCompleteTextViewState!!.text)) {
                textInputLayoutState!!.error = getString(R.string.text_layout_error_state)
            } else if (TextUtils.isEmpty(editTextPin!!.text)) {
                textInputLayoutPin!!.error = getString(R.string.text_layout_error_postbox)
            } else {
                saveUserDetails()
            }
        }

    private fun saveUserDetails() {
        textInputLayoutFirstName!!.error = null
        textInputLayoutLastName!!.error = null
        textInputLayoutDob!!.error = null
        textInputLayoutDob!!.error = null
        textInputLayoutCountry!!.error = null
        textInputLayoutRegion!!.error = null
        textInputLayoutState!!.error = null
        textInputLayoutPin!!.error = null
        var userProfile = DataHolder.getInstance().selectedFarmer
        if (userProfile == null) userProfile = Farmer()
        userProfile.dateOfBirth = DateUtil().getApiFormat(editTextDob!!.text.toString())
        userProfile.address = editTextAddress!!.text.toString()
        userProfile.country = autoCompleteTextViewCountry!!.text.toString()
        userProfile.firstName = editTextFirstName!!.text.toString()
        userProfile.midName = editTextMiddleName!!.text.toString()
        userProfile.lastName = editTextLastName!!.text.toString()
        userProfile.education = autoCompleteTextViewEducation!!.text.toString()
        userProfile.state = autoCompleteTextViewState!!.text.toString()
        userProfile.pin = editTextPin!!.text.toString()
        userProfile.mobile =
            countryCodePicker!!.selectedCountryCode + editTextMobile!!.text.toString()
        userProfile.gender = autoCompleteTextViewGender!!.text.toString()
        //userProfile.setUserName(countryCodePicker.getSelectedCountryCode() + editTextMobile.getText().toString());
        //userProfile.setRole("Farmer");
        userProfile.status = UserConstants.STATUS_ACTIVE
        if (userProfileBitmap != null) {
            try {
                uploadUserImage(userProfile)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            saveUserProfileService(userProfile)
        }
    }

    private fun saveUserProfileService(profile: Farmer) {
        val task = SaveFarmerTask(profile)
        task.context = requireContext()
        task.setLoadingMessage("Saving profile...")
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                showProfileOkDialog()
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    @Throws(IOException::class)
    private fun uploadUserImage(profile: Farmer) {
        val profilePicFile =
            FileUtil().getFileFromBitmap(userProfileBitmap, requireContext(), "profile_pic")
        val task = UploadProfilePicTask(profilePicFile)
        task.context = requireContext()
        task.setLoadingMessage(getString(R.string.saving_profile))
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    val docs = listOf(*data as Array<String?>)
                    profile.documents = docs
                }
                saveUserProfileService(profile)
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    private fun showProfileOkDialog() {
        val appPrefs = AppPrefs(requireActivity())
        appPrefs.isUserProfileCompleted = true
        AppController.hideKeyboard(requireActivity())
    }

    private fun goToLandingFragment() {
        AppPrefs(requireContext()).clearUserDetails()
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.PROFILE_TO_LANDING)
        }
    }

    private fun showImageOptions() {
        val builderSingle = AlertDialog.Builder(requireContext())
        val arrayAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1
        )
        arrayAdapter.add(getString(R.string.camera))
        arrayAdapter.add(getString(R.string.gallery))
        builderSingle.setAdapter(arrayAdapter) { dialog: DialogInterface?, which: Int ->
            when (Objects.requireNonNull(arrayAdapter.getItem(which))) {
                "Camera" -> takePicture()
                "Gallery" -> takeGalleryPic()
            }
        }
        builderSingle.show()
    }

    private val gendersFromAssets: Unit
        private get() {
            val countriesJsonString = AppController()
                .loadJSONFromAsset(
                    requireActivity(),
                    "resources/gender.json"
                )
            val gson = GsonBuilder().create()
            val genderArrayList = gson.fromJson<ArrayList<Gender>>(
                countriesJsonString,
                object : TypeToken<ArrayList<Gender?>?>() {}.type
            )
            val genderList = ArrayList<String>()
            for (gender in genderArrayList) {
                genderList.add(gender.name)
            }
            genders = arrayOfNulls(genderList.size)
            genders = genderList.toArray(genders)
            setAdapterGender()
        }

    private fun setAdapterGender() {
        val genderAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            genders
        )
        autoCompleteTextViewGender!!.setAdapter(genderAdapter)
    }

    private fun setAdapterEducation() {
        val education = arrayOf(
            getString(R.string.graduate),
            getString(R.string.specialized_in_crop),
            getString(R.string.post_graduate),
            getString(R.string.doctor), getString(R.string.higher_secondary),
            getString(R.string.metric_passed), getString(R.string.primary_education),
            getString(R.string.nil)
        )
        val educationAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            education
        )
        autoCompleteTextViewEducation!!.setAdapter(educationAdapter)
    }

    private fun loadFarmerDetails() {
        editTextDob!!.setText(getDateAsFormatted(selectedFarmer!!.dateOfBirth))
        editTextAddress!!.setText(selectedFarmer!!.address)
        autoCompleteTextViewCountry!!.setText(selectedFarmer!!.country)
        editTextFirstName!!.setText(selectedFarmer!!.firstName)
        editTextMiddleName!!.setText(selectedFarmer!!.midName)
        editTextLastName!!.setText(selectedFarmer!!.lastName)
        autoCompleteTextViewEducation!!.setText(selectedFarmer!!.education)
        autoCompleteTextViewState!!.setText(selectedFarmer!!.state)
        editTextPin!!.setText(selectedFarmer!!.pin)
        autoCompleteTextViewGender!!.setText(selectedFarmer!!.gender)
        ageOfPerson = 18
        reloadStateForCountry(selectedFarmer!!.country)
    }

    private fun getDateAsFormatted(dateTime: String?): String {
        var dt = dateTime
        if (dateTime?.length ?: 1 > 23) {
            dt = dateTime?.dropLast(dateTime.length - 23)
        }
        return if (dateTime != null) {
            val var10000 = DateUtil().getMobileFormat(dt)
            Intrinsics.checkNotNullExpressionValue(var10000, "DateUtil().getMobileFormat(dt)")
            var10000
        } else {
            ""
        }
    }

    companion object {
        private const val TAG = "skt"
    }
}