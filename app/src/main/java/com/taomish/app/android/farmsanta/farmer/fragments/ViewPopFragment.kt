package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.AddToBookMarkTask
import com.taomish.app.android.farmsanta.farmer.background.DeleteBookMarkTask
import com.taomish.app.android.farmsanta.farmer.background.GetCropListTask
import com.taomish.app.android.farmsanta.farmer.background.GetPopListTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_pop.ViewPopFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_pop.components.Section
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.constants.PopConstants.BOOKMARK_POP
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.models.api.user.BookMark
import com.taomish.app.android.farmsanta.farmer.utils.Share
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

@Suppress("UNCHECKED_CAST", "unused")
class ViewPopFragment : FarmSantaBaseFragment() {

    private val viewModel: POPViewModel by activityViewModels()
    private var index = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
                    ViewPopFragmentScreen(
                        viewModel = viewModel,
                        onBookmark = this@ViewPopFragment::addBookMark,
                        onShare = this@ViewPopFragment::onShare,
                        goToZoomImage = this@ViewPopFragment::goToZoomImageFragment,
                        onClickSection = this@ViewPopFragment::onClickSection
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
        savedState: Bundle?,
    ): View? {
        return null
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        fetchCropList()
        index = viewModel.selectedPopIndex
        val args = ViewPopFragmentArgs.fromBundle(arguments ?: Bundle())
        if (args.uuid.isNotEmpty() && DataHolder.getInstance().popDtoArrayList != null) {
            DataHolder.getInstance().popDtoArrayList.find { it.uuid == args.uuid }
                ?.let(viewModel.pop::postValue)
        }
    }


    /**
     * crops should be fetched if App is launched by deep link.
     */
    private fun fetchCropList() {
        if (!DataHolder.getInstance().cropMasterMap.isNullOrEmpty()) {
            if (viewModel.crops.isEmpty()) {
                viewModel.crops = DataHolder.getInstance().cropMasterMap
            }
            fetchPopList()
            return
        }
        val task = GetCropListTask()
        task.context = requireContext()
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    if (data.isNotEmpty()) {
                        val crops = data as? ArrayList<CropMaster>
                        if (!crops.isNullOrEmpty()) {
                            DataHolder.getInstance().cropArrayList = crops
                            if (viewModel.crops.isEmpty()) {
                                viewModel.crops = DataHolder.getInstance().cropMasterMap
                            }
                            fetchPopList()
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showToast("reason: $reason\nmessage: $errorMessage")
            }
        })
        task.execute()
    }


    /**
     * pops should be fetched if this fragment is launched by deep link.
     */
    private fun fetchPopList() {
        val pops = DataHolder.getInstance().popDtoArrayList
        if (!pops.isNullOrEmpty()) {
            requireActivity().intent?.let { intent ->
                intent.data?.lastPathSegment?.let { uuid ->
                    if (viewModel.pop.value == null) {
                        val popDto = pops.find { it.uuid == uuid }
                        viewModel.pop.postValue(popDto)
                    }
                }
            }
            return
        }

        val task = GetPopListTask()
        task.context = requireContext()
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    if (data.isNotEmpty()) {
                        val list = data.toList() as List<PopDto>
                        DataHolder.getInstance().popDtoArrayList = ArrayList(list)
                        requireActivity().intent?.let { intent ->
                            intent.data?.lastPathSegment?.let { uuid ->
                                if (viewModel.pop.value == null) {
                                    val popDto = list.find { it.uuid == uuid }
                                    viewModel.pop.postValue(popDto)
                                }
                            }
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showToast("reason : $reason, errorMessage : $errorMessage")
            }
        })
        task.execute()
    }


    private fun removeBookMark(popDto: PopDto?) {
        val task = DeleteBookMarkTask(popDto?.bookmarkedUUID ?: "")
        task.context = context
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                context?.showToast(R.string.removed_from_bookmark)
                DataHolder.getInstance().popDtoArrayList
                    .getOrNull(viewModel.selectedPopIndex)?.bookmarked = true
                viewModel.pops.value[viewModel.selectedPopIndex].bookmarked = true
                viewModel.pop.postValue(viewModel.pop.value)
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {

            }
        })
        task.execute()
    }


    private fun addBookMark() {
        if (viewModel.pop.value?.bookmarkedUUID != null) {
            removeBookMark(viewModel.pop.value)
            return
        }
        val bookMark = BookMark()
        bookMark.bookmarkType = BOOKMARK_POP
        bookMark.referenceUUID = viewModel.pop.value?.uuid ?: ""

        val task = AddToBookMarkTask(bookMark)
        task.context = context
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is BookMark) {
                    context?.showToast(R.string.added_bookmark)
                    DataHolder.getInstance().popDtoArrayList
                        .getOrNull(viewModel.selectedPopIndex)?.bookmarked = true
                    viewModel.pops.value[viewModel.selectedPopIndex].bookmarked = true
                    viewModel.pop.postValue(viewModel.pop.value)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }


    private fun onShare() {
        val id = viewModel.pops.value.getOrNull(viewModel.selectedPopIndex)?.uuid ?: ""
        val url = URLConstants.getPOPShareURL(id)
        Share.share(requireContext(), url)
    }


    private fun onClickSection(section: Section) {
        goToPopDetailFragment(index, section.tabIndex)
    }

    private fun goToZoomImageFragment(url: String) {
        if (url.removePrefix(URLConstants.S3_IMAGE_BASE_URL).contains("null", true)) {
            showToast(R.string.image_not_available)
        } else {
            fragmentChangeHelper?.onFragmentData(
                AppConstants.FragmentConstants.FRAGMENT_ZOOM_IMAGE_FROM_VIEW_POP, url
            )
        }
    }

    private fun goToPopDetailFragment(index: Int, tab: Int) {
        fragmentChangeHelper?.onFragmentData(
            AppConstants.FragmentConstants.FARMER_POP_DET_FRAGMENT, index, tab
        )
    }

}