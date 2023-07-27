package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.*
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_library.POPLibraryFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_library.components.SortType
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.constants.PopConstants.BOOKMARK_POP
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.models.api.user.BookMark
import com.taomish.app.android.farmsanta.farmer.utils.Share.share
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class PopLibraryFragment : FarmSantaBaseFragment() {

    private val viewModel by activityViewModels<POPViewModel>()
    private lateinit var mContext: Context

    override fun init() {}

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedState: Bundle?,
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
        init()
        initData()
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    POPLibraryFragmentScreen(
                        viewModel = viewModel,
                        onRefresh = this@PopLibraryFragment::fetchPopList,
                        onSearch = this@PopLibraryFragment::searchPops,
                        onClose = this@PopLibraryFragment::fetchPopList,
                        onBookmarkPop = this@PopLibraryFragment::addBookMark,
                        onSharePop = this@PopLibraryFragment::onShare,
                        onPopItemClicked = this@PopLibraryFragment::goToViewPopFragment,
                        onBookmarksClicked = this@PopLibraryFragment::openMyBookmarksFragment
                    )
                }
            }
        }
        return root
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        mContext = requireContext()
        fetchPopBookMarksList()
    }

    private fun fetchPopList(sortType: SortType = SortType.Alphabetically) {
        viewModel.crops = DataHolder.getInstance().cropMasterMap
        val task = GetPopListTask(sortType)
        task.context = requireContext()
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    viewModel.pops.postValue(data.toList() as List<PopDto>)
                    DataHolder.getInstance().popDtoArrayList = ArrayList(viewModel.pops.value)
                    viewModel.isRefreshing.postValue(false)
                    mapPopAndBookMarks()
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                viewModel.isRefreshing.postValue(false)
            }
        })
        task.execute()
    }

    private fun mapPopAndBookMarks() {
        val userId = DataHolder.getInstance().selectedFarmer?.userId
        viewModel.pops.value.forEach { popDto ->
            popDto.bookmarkedUUID = null
            popDto.bookmarked = false
            viewModel.popBookMarks.value
                .find { it.referenceUUID == popDto.uuid && userId == it.createdBy }?.let {
                    popDto.bookmarkedUUID = it.uuid
                    popDto.bookmarked = true
                }
        }
        viewModel.pops.value = viewModel.pops.value
    }

    private fun removeBookMark(popDto: PopDto, position: Int) {
        val task = DeleteBookMarkTask(popDto.bookmarkedUUID ?: "")
        task.context = context
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                context?.showToast(R.string.removed_from_bookmark)
                DataHolder.getInstance().popDtoArrayList.getOrNull(position)?.bookmarked = false
                viewModel.pops.value[position].bookmarked = false
                viewModel.pops.postValue(viewModel.pops.value)
                fetchPopBookMarksList()
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {

            }
        })
        task.execute()
    }

    private fun addBookMark(popDto: PopDto, position: Int) {
        if (popDto.bookmarked) {
            removeBookMark(popDto, position)
            return
        }
        val bookMark = BookMark()
        bookMark.bookmarkType = BOOKMARK_POP
        bookMark.referenceUUID = popDto.uuid
        bookMark.createdBy = DataHolder.getInstance().selectedFarmer?.userId ?: ""

        val task = AddToBookMarkTask(bookMark)
        task.context = context
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is BookMark) {
                    context?.showToast(R.string.added_bookmark)
                    DataHolder.getInstance().popDtoArrayList.getOrNull(position)?.bookmarked = true
                    viewModel.pops.value[position].bookmarked = true
                    viewModel.pops.postValue(viewModel.pops.value)
                    fetchPopBookMarksList()
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                mContext.showToast(reason)
            }
        })
        task.execute()
    }

    private fun goToViewPopFragment(uuid: String) {
        fragmentChangeHelper?.onFragmentData(
            AppConstants.FragmentConstants.FRAGMENT_VIEW_POP, uuid
        )
    }


    private fun searchPops() {
        val task = GetSearchedPopsTask()
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage(mContext.getString(R.string.searching))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    val list = data.toList() as List<PopDto>?
                    if (list != null) {
                        viewModel.pops.postValue(list)
                        viewModel.isRefreshing.postValue(false)
                        mapPopAndBookMarks()
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                mContext.showToast("reason: $reason")
                viewModel.isRefreshing.postValue(false)
            }
        })
        task.execute(viewModel.searchText.value.trim())
    }


    private fun onShare(index: Int) {
        val pop = viewModel.pops.value.getOrNull(index)
        val url = URLConstants.getPOPShareURL(pop?.uuid ?: "")
        share(requireContext(), url)
    }

    private fun openMyBookmarksFragment() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FARMER_MY_BOOKMARKS_POPS)
        }
    }

    private fun fetchPopBookMarksList() {
        val task = GetAllBookMarks()
        task.context = requireContext()
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    if (data.isNotEmpty()) {
                        val dataList = data as ArrayList<BookMark>
                        viewModel.popBookMarks.postValue(dataList.filter { it.bookmarkType == BOOKMARK_POP })
                        fetchPopList()
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                Log.d("PopLibraryFragment", "Reason--> $reason, \nError--> $errorMessage")
            }
        })
        task.execute()
    }

}
