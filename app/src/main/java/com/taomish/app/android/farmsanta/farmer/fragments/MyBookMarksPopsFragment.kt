package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.GetBookmarkedPopList
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_bookmarks_pops.MyBookmarksPopsFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

class MyBookMarksPopsFragment : FarmSantaBaseFragment() {
    private val viewModel: POPViewModel by activityViewModels()
    private var currentUser: Farmer? = null

    override fun init() {

    }

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_my_bookmarks_pops, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
                    MyBookmarksPopsFragmentScreen(
                        viewModel = viewModel,
                        goToViewPop = this@MyBookMarksPopsFragment::goToViewPopFragment
                    )
                }
            }
        }
        return root
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        getUserProfile()
    }

    override fun onResume() {
        super.onResume()
        getUserProfile()
    }

    private fun getUserProfile() {
        currentUser = DataHolder.getInstance().selectedFarmer
        if (currentUser != null) {
            fetchPopList()
        }
    }


    @Suppress("UNCHECKED_CAST")
    private fun fetchPopList() {
        val task = GetBookmarkedPopList()
        task.context = requireContext()
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    val list = data.toList() as List<PopDto>
                   val distList= list.distinctBy { select->
                       select.uuid
                   }
                    viewModel.bookmarkedPops.postValue(distList)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showToast("Reason--> $reason, \nError--> $errorMessage")
            }
        })
        task.execute()
    }


    private fun goToViewPopFragment(uuid: String) {
        fragmentChangeHelper?.onFragmentData(
            AppConstants.FragmentConstants.VIEW_POP_FROM_BOOKMARKED_POPS,
            uuid
        )
    }

}