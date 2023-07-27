package com.taomish.app.android.farmsanta.farmer.fragments

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.activityViewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.*
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseActivity
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FarmScoutingViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.HomeFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.cultivar.Cultivar
import com.taomish.app.android.farmsanta.farmer.models.api.disease.Disease
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.gender.GenderItem
import com.taomish.app.android.farmsanta.farmer.models.api.home.AllFarmSanta
import com.taomish.app.android.farmsanta.farmer.models.api.home.MarketDto
import com.taomish.app.android.farmsanta.farmer.models.api.home.Price
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.models.api.master.Language
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.models.api.notification.AdvisoryTag
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.models.api.notification.Subscribe
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage
import com.taomish.app.android.farmsanta.farmer.models.api.weather.WeatherData
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationRequestResult.LocationData
import com.taomish.app.android.farmsanta.farmer.utils.*

@Suppress("UNCHECKED_CAST")
class HomeFragment : FarmSantaBaseFragment() {
    private var userProfile: Farmer? = null
    private var weather: WeatherData? = null
    private lateinit var appPrefs: AppPrefs
    private var mLastKnownLocation: Location? = null
    private var mBroadcastReceiver: BroadcastReceiver? = null
    private lateinit var mContext: Context
    private val viewModel: HomeViewModel by activityViewModels()
    private val scoutingViewModel: FarmScoutingViewModel by activityViewModels()
    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater, container: ViewGroup, savedState: Bundle,
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    HomeFragmentScreen(
                        homeViewModel = viewModel,
                        onNavigationItemClicked = this@HomeFragment::onNavigationItemClicked,
                        openViewAdvisoryFragment = this@HomeFragment::goToViewAdvisoryFragment,
                        goToQueryDetailsFragment = this@HomeFragment::goToFarmScoutDetailFragment,
                        goToViewPop = this@HomeFragment::goToPopViewPop,
                        goToDiseaseDetails = this@HomeFragment::goToDiseaseDetailsFragment,
                        fetchMarketData = this@HomeFragment::fetchMarketAnalysisData
                    )
                }
            }
        }
        return view
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        mContext = requireContext()
        appPrefs = AppPrefs(mContext)
        getUserProfile()
        checkNotificationData()
    }


    private fun onNavigationItemClicked(screen: Screen) {
        when (screen) {
            Screen.AskQueries -> goToAddScouting()
            Screen.MyQueries -> goToScoutingImageFragment()
            Screen.FarmTalks -> goToTalksFragment()
            Screen.Pops -> goToPopHomeFragment()
            Screen.CropAdvisory -> goToCropAdvisoryInboxFragment()
            Screen.NutriSource -> goToNutriSourceFragment()
            Screen.MarketAnalysis -> goToMarketAnalysisFragment()
            Screen.CropCalendar -> goToCropCalendarFragment()
            Screen.MyCrops -> goToMyCropsFragment()
            Screen.Weather -> goToWeatherDetailsFragment()
            Screen.Diseases -> goToDiseasesFragment()
            Screen.FertilizerCalculator -> goToFertilizerCalculatorFragment()
        }
    }

    private fun goToAddScouting() {
        findNavController().navigate(R.id.action_scout_list_to_add_scout_new)
    }

    override fun onResume() {
        super.onResume()
        locationPermission()
        fetchFarmScouting()
        if (mBroadcastReceiver == null) mBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                fetchCropAdvisories()
            }
        }
        try {
            LocalBroadcastManager.getInstance(mContext).registerReceiver(
                mBroadcastReceiver!!, IntentFilter(
                    AppConstants.DataTransferConstants.KEY_NOTIFICATION_RECEIVED
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mBroadcastReceiver != null) {
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mBroadcastReceiver!!)
        }
    }

    private fun getUserProfile() {
        val appPrefs = AppPrefs(mContext)
        val token = appPrefs.userToken
        val jwtUtil = JwtUtil()
        val userId = jwtUtil.getUserId(token)
        userProfile = DataHolder.getInstance().selectedFarmer
        fetchLanguages()
        viewModel.fetchTerritory(mContext)
        fetchGender()
        fetchCultivarList()
        fetchAdvisoryTags()
        fetchCultivarType()
        if (userProfile == null) {
            fetchUserDetails(userId)
        } else fetchAllData(userId)

    }

    private fun fetchAllData(userId: String) {
        fetchPopList()
        fetchMarketPrice(userId)
        fetchSocialMessages()
        fetchCropStages()
        fetchSocialMessages()
        fetchMarketPrice(userId)
        showHomePageData()
        fetchCropAdvisories()
        fetchFarmScouting()
        fetchCropDiseases()
        sendRegistrationToServer()
    }


    private fun fetchCrops() {
        val task = GetCropListTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    if (data.isNotEmpty()) {
                        val crops = data as ArrayList<CropMaster>?
                        if (!crops.isNullOrEmpty()) {
                            viewModel.crops.clear()
                            val diseasesCrops = mutableListOf<CropMaster>()
                            crops.forEach { crop ->
                                val disease = viewModel.diseases
                                    .find { it.crops?.contains(crop.uuid) == true }
                                if (disease != null) {
                                    diseasesCrops.add(crop)
                                }
                            }
                            viewModel.crops.addAll(diseasesCrops)
                            fetchMyCrops()
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {

            }
        })
        task.execute()
    }

    private fun fetchCropDiseases() {
        if (viewModel.diseases.isEmpty()) {
            val task = GetAllCropDiseases()
            task.context = requireContext()
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any?) {
                    if (data is Array<*>) {
                        if (data.isNotEmpty()) {
                            viewModel.diseases.postValue(data.toList() as List<Disease>)
                            fetchCrops()
                        }
                    }
                }

                override fun onTaskFailure(reason: String?, errorMessage: String?) {

                }

            })
            task.execute()
        }
    }

    private fun fetchCropStages() {
        val task = GetCropStageListByCropId("")
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                (data as Array<*>).also {
                    if (it.isNotEmpty()) {
                        viewModel.growthStages.clear()
                        viewModel.growthStages.addAll(data as Array<CropStage>)
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showToast(reason)
            }
        })
        task.execute()
    }


    private fun fetchMyCrops() {
        viewModel.myCrops.clear()
        DataHolder.getInstance().cropMasterMap.let { map ->
            map[userProfile?.crop1]?.let { viewModel.myCrops.add(it) }
            map[userProfile?.crop2]?.let { viewModel.myCrops.add(it) }
            map[userProfile?.crop3]?.let { viewModel.myCrops.add(it) }
        }
    }

    private fun fetchFarmScouting() {
        if (userProfile != null && userProfile!!.uuid != null) {
            val task = GetFarmScoutingTask("", userProfile!!.uuid)
            task.context = mContext
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    if (data is Array<*>) {
                        if (data.isEmpty().not()) {
                            val c = data.toList() as List<FarmScouting?>
                            viewModel.farmsScouting.postValue(c)
                            DataHolder.getInstance().scoutingImagesCount = c.size
                            loadAllData()
                        } else {
                            viewModel.farmsScouting.postValue(emptyList())
                        }
                    }
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {
                    viewModel.farmsScouting.postValue(emptyList())
                    showToast(reason)
                }
            })
            task.execute()
        }
    }

    private fun fetchUserDetails(token: String) {
        val task = GetCurrentFarmerTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Farmer) {
                    userProfile = data
                    DataHolder.getInstance().selectedFarmer = userProfile
                    viewModel.setProfile(userProfile!!)
                    appPrefs.userName = userProfile!!.userName
                    appPrefs.firstName = userProfile!!.firstName
                    appPrefs.lastName = userProfile!!.lastName
                    appPrefs.phoneNumber = PhoneNumberUtils.formatNumber(userProfile!!.mobile, "IN")
                    appPrefs.userId = userProfile!!.userId
                    DataHolder.getInstance().selectedFarmer = data
                    fetchAllData(token)
                    fetchFarmScouting()
                    showHomePageData()
                    if (viewModel.myCrops.isEmpty()) fetchMyCrops()
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showHomePageData()
            }
        })
        task.execute(token)
    }

    private fun fetchPopList() {
        val task = GetPopListTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    if (data.isNotEmpty()) {
                        val list = data.toList() as List<PopDto>
                        viewModel.userPops.postValue(list)
                        DataHolder.getInstance().popDtoArrayList = ArrayList(list)
                    } else {
                        viewModel.userPops.postValue(emptyList())
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                viewModel.userPops.postValue(emptyList())
                showToast("reason : $reason, errorMessage : $errorMessage")
            }
        })
        task.execute()
    }

    private fun fetchCultivarType() {
        if (DataHolder.getInstance().cultivarTypeList != null) {
            return
        } else {
            val task = GetCultivarTypeTask()
            task.context = requireContext()
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    if (data is ArrayList<*>) {
                        if (data.isNotEmpty()) {
                            DataHolder.getInstance().cultivarTypeList =
                                data as ArrayList<GlobalIndicatorDTO?>
                        }
                    }

                }

                override fun onTaskFailure(reason: String, errorMessage: String) {
//                    showToast(reason)
                }
            })
            task.execute()
        }
    }

    /*Get notifications from crop advisories service API*/
    private fun fetchCropAdvisories() {
        val task = GetCropAdvisoriesTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is List<*>) {
                    if (data.isNotEmpty()) {
                        val c = ArrayList<CropAdvisory>()
                        for (advisory in (data as List<CropAdvisory>)) {
                            if (advisory.status.equals("published", ignoreCase = true)) {
                                c.add(advisory)
                            }
                        }
                        viewModel.cropAdvisories.postValue(c)
                        DataHolder.getInstance().advisoryCount = c.size
                        DataHolder.getInstance().cropAdvisoryArrayList = c
                        loadAllData()
                    } else {
                        viewModel.cropAdvisories.postValue(ArrayList())
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                viewModel.cropAdvisories.postValue(ArrayList())
//                showToast(reason)
            }
        })
        task.execute()
    }

    private fun fetchCultivarList() {
        if (DataHolder.getInstance().cultivarArrayList != null) {
            return
        }
        val task = GetCultivarListTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    if (data.isEmpty()) {
                        DataHolder.getInstance().cultivarArrayList = data as ArrayList<Cultivar?>
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    private fun fetchAdvisoryTags() {
        val task = GetAdvisoryTagsTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    if (data.isNotEmpty()) {
                        val c = data.toList() as List<AdvisoryTag?>
                        DataHolder.getInstance().setAdvisoryTagMap(c)
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    private fun fetchLocationWeather() {
        val task = GetAllWeatherTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is WeatherData) {
                    DataHolder.getInstance().weatherAll = data
                    loadWeatherInfo()
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute("" + mLastKnownLocation?.latitude, "" + mLastKnownLocation?.longitude)
    }

    private fun fetchSocialMessages() {
        val task = GetSocialMessagesTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    if (data.isNotEmpty()) {
                        val messages = arrayListOf<Message?>()
                        data.forEach {
                            if (it is Message) {
                                messages.add(0, it)
                            }
                        }
                        viewModel.talks.postValue(messages)
                        val map = mutableMapOf<String, String>()
                        viewModel.talks.value?.forEach { message ->
                            message?.let {
                                map[message.firstName ?: "N/A"] = message.profileImage ?: ""
                            }
                        }
                        viewModel.profiles.postValue(map)
                        DataHolder.getInstance().userMessageArrayList =
                            viewModel.talks.value as ArrayList<Message>
                        loadTalksData()
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
//                mContext.showToast("reason: $reason, error: $errorMessage")
            }
        })
        task.execute(null)
    }

    /*Get Market price from market service API*/
    private fun fetchMarketPrice(token: String) {

        if (!DataHolder.getInstance().priceArrayList.isNullOrEmpty()) {
            viewModel.prices.postValue(DataHolder.getInstance().priceArrayList)
            viewModel.selectedPrice.postValue(viewModel.prices.value?.firstOrNull())
            fetchMarketAnalysisData()
            return
        }

        val task = GetMarketPriceTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    val list = data.toList() as List<Price>?
                    if (!list.isNullOrEmpty()) {
                        viewModel.prices.postValue(list)
                        DataHolder.getInstance().priceArrayList =
                            viewModel.prices.value?.let { ArrayList(it) }
                        viewModel.prices.value.isNotEmptyOrNull {
                            viewModel.selectedPrice.postValue(it.firstOrNull())
                            fetchMarketAnalysisData()
                        }
                    } else {
                        viewModel.prices.postValue(emptyList())
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                viewModel.prices.postValue(emptyList())
//                mContext.showToast("reason: $reason \n error: $errorMessage")
            }
        })
        task.execute(token)
    }

    private fun sendRegistrationToServer() {
        val token = appPrefs.firebaseToken
        if (token != null) {
            val subscribe = Subscribe()
            val tokenList: MutableList<String> = ArrayList()
            tokenList.add(token)
            subscribe.tokens = tokenList
            val task = SubscribeToPushTask(subscribe)
            task.context = farmSantaContext
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {}
                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.setShowLoading(false)
            task.execute()
        }
    }

    private fun showHomePageData() {
        loadAllData()
        loadTalksData()
        loadWeatherInfo()
    }

    private fun locationPermission() {
        if (requireActivity() is FarmSantaBaseActivity) {
            if (DataHolder.getInstance().weatherAll == null) {
                (requireActivity() as FarmSantaBaseActivity).requestForLocation()
            }
            (requireActivity() as FarmSantaBaseActivity).locationDataMutableLiveData.observe(
                viewLifecycleOwner
            ) { (location): LocationData ->
                mLastKnownLocation = location
                fetchLocationWeather()
            }
        }
    }


    private fun loadAllData() {
        val idList = arrayOf(
            R.drawable.ic_cropcalendar, R.drawable.ic_scouting_gallery, R.drawable.ic_crop_advisory
        )
        val textList = arrayOf(
            "Crop calendar", "Scouting gallery", "Crop advisory inbox"
        )
        val countList = arrayOf(
            "0",
            DataHolder.getInstance().scoutingImagesCount.toString(),
            DataHolder.getInstance().advisoryCount.toString()
        )
        val data = ArrayList<AllFarmSanta>()
        for (i in idList.indices) {
            val allFarmSanta = AllFarmSanta()
            allFarmSanta.iconId = idList[i]
            allFarmSanta.text = textList[i]
            allFarmSanta.textColor = "#3C403C"
            allFarmSanta.count = countList[i]
            data.add(allFarmSanta)
        }
    }

    private fun loadTalksData() {
        if (viewModel.talks.value == null || viewModel.talks.value!!.size == 0) {
            viewModel.talks.postValue(DataHolder.getInstance().userMessageArrayList)
        }
    }

    /*Set Price values in UI widgets*/

    /*Set weather values to textview widgets*/
    @SuppressLint("SetTextI18n")
    private fun loadWeatherInfo() {
        weather = DataHolder.getInstance().weatherAll
        if (weather == null) return
        viewModel.weatherData = weather
    }


    private fun goToMyCropsFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_MY_CROPS)
        }
    }

    private fun goToCropCalendarFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FARMER_CROP_CALENDAR)
        }
    }


    private fun goToNutriSourceFragment() {
        /*val fileName =
            if (mContext.language == ApiConstants.Language.EN) "nsw_product_catalogue_en.html"
            else "nsw_product_catalogue_fr.html"*/
        if (fragmentChangeHelper != null) {
            findNavController().navigate(WebViewFragmentDirections.actionToWebviewFragment().apply {
                title = getString(R.string.nutri_source)
                /*url = "file:///android_asset/$fileName"*/
                url = ""
            })
        }
    }


    private fun goToFertilizerCalculatorFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_FERTILIZER_CALCULATOR)
        }
    }


    private fun goToDiseasesFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_DISEASES)
        }
    }

    private fun goToDiseaseDetailsFragment(disease: Disease) {
        viewModel.selectedDisease = disease
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(AppConstants.FragmentConstants.FRAGMENT_DISEASE_DETAILS_FROM_HOME)
        }
    }

    private fun goToMarketAnalysisFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FRAGMENT_MARKET_ANALYSIS)
        }
    }


    private fun goToFarmScoutDetailFragment(farmScouting: FarmScouting, position: Int) {
        scoutingViewModel.selectedScouting.value = farmScouting
        DataHolder.getInstance().selectedScoutingIndex = position
        DataHolder.getInstance().dataObject = viewModel.farmsScouting.value
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.SCOUT_FARM_IMG_DETAILS_FROM_HOME, farmScouting.uuid
            )
        }
    }


    private fun goToPopViewPop(popDto: PopDto) {
        fragmentChangeHelper?.onFragmentData(
            AppConstants.FragmentConstants.FRAGMENT_VIEW_POP_FROM_HOME, popDto.uuid
        )
    }


    private fun goToViewAdvisoryFragment(advisory: CropAdvisory) {
        DataHolder.getInstance().dataObject = advisory
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_VIEW_ADVISORY_FROM_HOME
            )
        }
    }

    private fun goToScoutingImageFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.HOME_LIST_FARM_SCOUT, ""
            )
        }
    }

    private fun goToTalksFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FARMER_TALKS_FRAGMENT)
        }
    }

    private fun goToScoutingDetailsFragment(uuid: String?) {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.SCOUTING_DETAILS_HOME, uuid
            )
        }
    }

    private fun goToWeatherDetailsFragment() {
        if (DataHolder.getInstance().weatherAll == null) return
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.WEATHER_DETAILS)
        }
    }

    private fun goToPopHomeFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.POP_HOME)
        }
    }

    private fun goToCropAdvisoryInboxFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.CROP_ADVISORY_INBOX)
        }
    }


    private fun checkNotificationData() {
        val notificationType =
            requireActivity().intent.getStringExtra(AppConstants.DataTransferConstants.KEY_NOTIF_TYPE)
        val notificationUuid =
            requireActivity().intent.getStringExtra(AppConstants.DataTransferConstants.KEY_NOTIF_UUID)
        if (notificationType != null && notificationType.isNotEmpty()) {
            requireActivity().intent.putExtra(AppConstants.DataTransferConstants.KEY_NOTIF_TYPE, "")
            if (notificationType == "Advisory") {
                DataHolder.getInstance().cropAdvisoryArrayList.find { it.uuid == notificationUuid }
                    ?.let { goToViewAdvisoryFragment(advisory = it) }
            } else if (notificationType == "Scouting") {
                goToScoutingDetailsFragment(notificationUuid)
            }
        }
    }


    private fun fetchGender() {
        if (DataHolder.getInstance().genderItemList != null && DataHolder.getInstance().genderItemList.isNotEmpty()) {
            return
        }
        val task = GetGenderTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    DataHolder.getInstance().genderItemList = listOf(*data as Array<GenderItem?>)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    private fun fetchMarketAnalysisData() {
        val task = GetMarketDataTask()
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage(mContext.getString(R.string.loading_market_data))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is MarketDto?) {
                    viewModel.marketData = data
                    viewModel.setXAndYValues()
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                viewModel.marketData = null
                showToast("Reason: $reason\nmessage: $errorMessage")
            }
        })

        task.execute(
            viewModel.selectedPrice.value?.commodityName,
            viewModel.selectedPrice.value?.commodityType,
            viewModel.selectedPeriod
        )
    }

    private fun fetchLanguages() {
        val task = GetLanguagesTask()
        task.context = requireContext()
        task.setShowLoading(true)
        task.setLoadingMessage(getString(R.string.language_loading_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    val list = data.toList() as List<Language>?
                    if (!list.isNullOrEmpty()) {
                        DataHolder.getInstance().setLanguages(list.toTypedArray())
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }
}