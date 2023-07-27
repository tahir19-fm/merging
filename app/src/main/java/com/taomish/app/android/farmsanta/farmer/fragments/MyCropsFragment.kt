package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.background.GetCropAdvisoriesTask
import com.taomish.app.android.farmsanta.farmer.background.GetCurrentFarmerTask
import com.taomish.app.android.farmsanta.farmer.background.GetFarmScoutingTask
import com.taomish.app.android.farmsanta.farmer.background.GetPopListTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.MyCropsViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_crops.MyCropsFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import com.taomish.app.android.farmsanta.farmer.utils.JwtUtil
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

@Suppress("UNCHECKED_CAST")
class MyCropsFragment : FarmSantaBaseFragment() {

    private val myCropsViewModel: MyCropsViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var mContext: Context
    private lateinit var farmer: Farmer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root
        init()
        initData()
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    MyCropsFragmentScreen(
                        myCropsViewModel = myCropsViewModel,
                        homeViewModel = homeViewModel,
                        onSectionViewMoreClicked = this@MyCropsFragment::onSectionViewMoreClicked,
                        goToViewAdvisoryFragment = this@MyCropsFragment::goToViewAdvisoryFragment,
                        goToQueryDetailsFragment = this@MyCropsFragment::goToFarmScoutDetailFragment,
                        goToViewPop = this@MyCropsFragment::goToPopViewPop,
                        goToDiseaseDetails = this@MyCropsFragment::goToDiseaseDetailsFragment
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
        savedState: Bundle?
    ): View? {
        return null
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        mContext = requireContext()
        if (DataHolder.getInstance().selectedFarmer !== null) {
            farmer = DataHolder.getInstance().selectedFarmer
        } else {
            getCurrentUser()
        }
        if (myCropsViewModel.selectedCrop.value == null) {
            myCropsViewModel.selectedCrop.postValue(homeViewModel.myCrops.firstOrNull())
        }
        getUserProfile()
    }

    private fun onSectionViewMoreClicked(screen: Screen) {
        when (screen) {
            Screen.AskQueries -> goToScoutingImageFragment()
            Screen.Pops -> goToPopHomeFragment()
            Screen.CropAdvisory -> goToCropAdvisoryInboxFragment()
            Screen.Diseases -> goToDiseasesFragment()
            Screen.MarketAnalysis -> goToMarketAnalysisFragment()
            else -> {}
        }
    }

    private fun getUserProfile() {
        val appPrefs = AppPrefs(mContext)
        val currentUser = appPrefs.userToken
        val jwtUtil = JwtUtil()
        val userId = jwtUtil.getUserId(currentUser)
        if (userId != null) {
            fetchPopList(userId)
//        fetchCropAdvisories()
//        fetchFarmScouting()
        }
    }

    private fun fetchPopList(token: String) {
        val task = GetPopListTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    val list = data.toList() as List<PopDto>
                    myCropsViewModel.popDtoArrayList.postValue(ArrayList(list))
                    DataHolder.getInstance().popDtoArrayList =
                        myCropsViewModel.popDtoArrayList.value
                    homeViewModel.cropPops.postValue(
                        homeViewModel.userPops.value?.filter {
                            it.crop == myCropsViewModel.selectedCrop.value?.uuid
                        }.orEmpty()
                    )
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showToast("reason : $reason, errorMessage : $errorMessage")
            }
        })
        task.execute(token)
    }


    private fun fetchCropAdvisories() {
        val task = GetCropAdvisoriesTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is List<*>) {
                    val c = ArrayList<CropAdvisory>()
                    for (advisory in (data as List<CropAdvisory>)) {
                        if (advisory.status.equals("published", ignoreCase = true)) {
                            c.add(advisory)
                        }
                    }
                    myCropsViewModel.cropAdvisoriesArrayList.postValue(c)
                    DataHolder.getInstance().advisoryCount = c.size
                    DataHolder.getInstance().cropAdvisoryArrayList = c
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }


    private fun fetchFarmScouting() {
        if (farmer.uuid != null) {
            val task = GetFarmScoutingTask("", farmer.uuid)
            task.context = mContext
            task.setShowLoading(false)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    if (data is Array<*>) {
                        if (data.isNotEmpty()) {
                            val c = data.toMutableList() as ArrayList<FarmScouting?>
                            myCropsViewModel.farmScoutingArrayList.postValue(c)
                            DataHolder.getInstance().scoutingImagesCount = c.size
                        }
                    }
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.execute()
        }
    }


    private fun getCurrentUser() {
        val task = GetCurrentFarmerTask()
        task.context = context
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                (data as Farmer).also {
                    DataHolder.getInstance().selectedFarmer = it
                    farmer = it
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })

        task.execute()
    }


    private fun goToScoutingImageFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_FARM_SCOUTING_LIST_FROM_MY_CROPS,
                ""
            )
        }
    }


    private fun goToPopHomeFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.POP_HOME)
        }
    }


    private fun goToPopViewPop(popDto: PopDto) {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper?.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_VIEW_POP_FROM_MY_CROPS, popDto.uuid
            )
        }
    }


    private fun goToCropAdvisoryInboxFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(
                AppConstants.FragmentConstants.CROP_ADVISORY_INBOX_FROM_MY_CROPS
            )
        }
    }

    private fun goToFarmScoutDetailFragment(farmScouting: FarmScouting, position: Int) {
        DataHolder.getInstance().selectedScoutingIndex = position
        DataHolder.getInstance().dataObject = myCropsViewModel.farmScoutingArrayList.value
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.SCOUT_FARM_IMG_DETAILS_FROM_MY_CROPS,
                farmScouting.uuid
            )
        }
    }

    private fun goToViewAdvisoryFragment(advisory: CropAdvisory) {
        DataHolder.getInstance().dataObject = advisory
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_VIEW_ADVISORY_FROM_MY_CROPS
            )
        }
    }

    private fun goToDiseasesFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_DISEASES_FROM_MY_CROPS,
                myCropsViewModel.selectedCrop.value?.uuid ?: ""
            )
        }
    }

    private fun goToDiseaseDetailsFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_DISEASE_DETAILS_FROM_MY_CROPS
            )
        }
    }

    private fun goToMarketAnalysisFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(
                AppConstants.FragmentConstants.FRAGMENT_MARKET_ANALYSIS_FROM_MY_CROPS
            )
        }
    }
}