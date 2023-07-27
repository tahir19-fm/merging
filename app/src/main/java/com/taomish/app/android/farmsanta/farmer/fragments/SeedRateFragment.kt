package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.CalculateSeedRateTask
import com.taomish.app.android.farmsanta.farmer.background.GetSeedRateCropListTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.CalculatorConstants
import com.taomish.app.android.farmsanta.farmer.controller.AppController
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.calculator.Calculate
import com.taomish.app.android.farmsanta.farmer.models.api.calculator.Properties
import com.taomish.app.android.farmsanta.farmer.models.api.calculator.SeedRateCrop
import com.taomish.app.android.farmsanta.farmer.models.api.profile.Country
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class SeedRateFragment : FarmSantaBaseFragment() {
    private var linearLayoutBottomSheet: LinearLayout? = null
    private var buttonContinue: Button? = null
    private var imageViewBack: ImageView? = null
    private var autoCompleteTextViewCountry: AutoCompleteTextView? = null
    private var autoCompleteTextViewCrop: AutoCompleteTextView? = null
    private var autoCompleteTextViewArea: AutoCompleteTextView? = null
    private var autoCompleteTextViewSeedVariety: AutoCompleteTextView? = null
    private var textViewKg: TextView? = null
    private var textViewKgLong: TextView? = null
    private var textViewLbs: TextView? = null
    private var textViewLbsLong: TextView? = null
    private lateinit var countries: Array<String?>
    private lateinit var cropStrings: Array<String?>
    private lateinit var cropVarieties: Array<String?>
    private lateinit var crops: Array<SeedRateCrop>
    private var behavior: BottomSheetBehavior<*>? = null
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
        return localInflater.inflate(R.layout.fragment_seed_rate, container, false)
    }

    override fun initViewsInLayout() {
        linearLayoutBottomSheet = initThisView(R.id.seedRate_linear_bottomSheet)
        buttonContinue = initThisView(R.id.seedRate_button_continue)
        imageViewBack = initThisView(R.id.seedRate_image_back)
        autoCompleteTextViewArea = initThisView(R.id.seedRate_autoComplete_area)
        autoCompleteTextViewCrop = initThisView(R.id.seedRate_autoComplete_crop)
        autoCompleteTextViewCountry = initThisView(R.id.seedRate_autoComplete_country)
        autoCompleteTextViewSeedVariety = initThisView(R.id.seedRate_autoComplete_variety)
        textViewKg = initThisView(R.id.seedRate_text_kg)
        textViewKgLong = initThisView(R.id.seedRate_text_kgLong)
        textViewLbs = initThisView(R.id.seedRate_text_lbs)
        textViewLbsLong = initThisView(R.id.seedRate_text_lbsLong)
    }

    override fun initListeners() {
        behavior = BottomSheetBehavior.from(linearLayoutBottomSheet!!)
        buttonContinue!!.setOnClickListener { v: View? -> calculateSeedRate() }
        imageViewBack!!.setOnClickListener { v: View? -> goBack() }
        autoCompleteTextViewCrop!!.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>?, view: View, position: Int, id: Long ->
                loadCropVariety((view as TextView).text.toString())
            }
        autoCompleteTextViewCountry!!.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    autoCompleteTextViewCountry!!.showDropDown()
                }
            }
        autoCompleteTextViewCrop!!.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    autoCompleteTextViewCrop!!.showDropDown()
                }
            }
        autoCompleteTextViewSeedVariety!!.onFocusChangeListener =
            OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                if (hasFocus) {
                    autoCompleteTextViewSeedVariety!!.showDropDown()
                }
            }
    }

    override fun initData() {
        behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        autoCompleteTextViewCountry!!.threshold = 1
        autoCompleteTextViewCrop!!.threshold = 1
        autoCompleteTextViewSeedVariety!!.threshold = 1
        countryListFromAssets
        profileLandDetails
        fetchCropList()
    }

    private val profileLandDetails: Unit
        private get() {
            val userProfile = DataHolder.getInstance().selectedFarmer
            val cropProfile = userProfile.lands
            if (cropProfile != null && cropProfile.size > 0) {
                val land = cropProfile[0]
                if (land != null) {
                    val area = land.area.unit
                    val round = ((area ?: 0) as Double).roundToInt()
                    autoCompleteTextViewArea!!.setText(round.toString())
                }
            }
        }

    private fun fetchCropList() {
        val task = GetSeedRateCropListTask()
        task.context = requireContext()
        task.setLoadingMessage("Fetching details")
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    crops = (data as ArrayList<SeedRateCrop>).toTypedArray()
                    cropListFromAssets
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    private fun openBottomSheet(result: Properties) {
        val resultKg = result.result_kg
        val resultLbs = result.result_lbs
        if (resultKg == null || resultLbs == null) {
            return
        }
        val df = DecimalFormat("#0.000")
        val df2 = DecimalFormat("#0.00")
        val resultKgDouble = resultKg.toDouble()
        val resultLbsDouble = resultLbs.toDouble()
        val kgFormatted = df.format(resultKgDouble)
        val kg2Formatted = df2.format(resultKgDouble)
        val lbsFormatted = df.format(resultLbsDouble)
        val lbs2Formatted = df2.format(resultLbsDouble)
        textViewKg!!.text = kgFormatted
        textViewLbs!!.text = lbsFormatted
        textViewKgLong!!.text = String.format("%s kg per square meter", kg2Formatted)
        textViewLbsLong!!.text = String.format("%s lbs per square meter", lbs2Formatted)
        behavior!!.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun goBack() {
        parentFragmentManager.popBackStackImmediate()
    }

    private fun calculateSeedRate() {
        val calculate = Calculate()
        calculate.crop = autoCompleteTextViewCrop!!.text.toString()
        calculate.recordType = "seed-rate"
        calculate.seedVariety = autoCompleteTextViewSeedVariety!!.text.toString()
        calculate.requiredCalculations = Arrays.asList(
            CalculatorConstants.SeedRate.RESULT_KG,
            CalculatorConstants.SeedRate.RESULT_LBS
        )
        val properties = Properties()
        properties.cultivable_area = autoCompleteTextViewArea!!.text.toString()
        calculate.properties = properties
        val task = CalculateSeedRateTask(calculate)
        task.context = requireContext()
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                val result = data as Properties
                openBottomSheet(result)
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.setLoadingMessage("Calculating")
        task.setShowLoading(true)
        task.execute()
    }

    private val countryListFromAssets: Unit
        private get() {
            val countriesJsonString = AppController()
                .loadJSONFromAsset(
                    requireActivity(),
                    "resources/countries.json"
                )
            val gson = GsonBuilder().create()
            val countryArrayList = gson.fromJson<ArrayList<Country>>(
                countriesJsonString,
                object : TypeToken<ArrayList<Country?>?>() {}.type
            )
            val countryList = ArrayList<String>()
            for (country in countryArrayList) {
                countryList.add(country.name)
            }
            countries = arrayOfNulls(countryList.size)
            countries = countryList.toArray(countries)
            setAdapterForCountry()
        }

    private fun setAdapterForCountry() {
        val countriesAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            countries
        )
        autoCompleteTextViewCountry!!.setAdapter(countriesAdapter)
    }

    private val cropListFromAssets: Unit
        private get() {
            if (crops != null) {
                cropStrings = arrayOfNulls(crops.size)
                for (i in crops.indices) {
                    cropStrings[i] = crops[i].crop
                }
                setAdapterForCrop()
            }
        }

    private fun setAdapterForCrop() {
        val userCropAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            cropStrings
        )
        autoCompleteTextViewCrop!!.setAdapter(userCropAdapter)
    }

    private fun loadCropVariety(cropName: String) {
        for (userCrop in crops) {
            if (userCrop.crop == cropName) {
                if (userCrop.varieties != null) {
                    cropVarieties = arrayOfNulls(userCrop.varieties.size)
                    cropVarieties = userCrop.varieties.toTypedArray()
                } else {
                    cropVarieties = arrayOfNulls(1)
                }
                break
            }
        }
        val userStageAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            cropVarieties
        )
        autoCompleteTextViewSeedVariety!!.setAdapter(userStageAdapter)
    }
}