package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.*
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SocialWallViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.SocialWallFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants
import com.taomish.app.android.farmsanta.farmer.constants.BookmarkConstants
import com.taomish.app.android.farmsanta.farmer.controller.DBController
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.models.api.tags.TrendingTags
import com.taomish.app.android.farmsanta.farmer.models.api.user.BookMark
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@Suppress("UNCHECKED_CAST")
class SocialWallFragment : FarmSantaBaseFragment() {

    private val viewModel: SocialWallViewModel by activityViewModels()
    private val trendingTags: MutableState<List<String>?> = mutableStateOf(null)
    private var mContext: Context? = null
    private var isInitialLoad = true
    private var userName: String? = null
    private var profileImage: String? = null

    override fun init() {
        isInitialLoad = true
    }

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle,
    ): View {
        return inflater.inflate(R.layout.fragment_social_wall, container, false)
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
                    SocialWallFragmentScreen(
                        viewModel = viewModel,
                        trendingTags = trendingTags.value,
                        name = userName ?: "",
                        profileImage = profileImage ?: "",
                        onRefresh = this@SocialWallFragment::initData,
                        onGoToDetailClicked = this@SocialWallFragment::goToPostDetails,
                        onAllPostSelected = this@SocialWallFragment::getSocialMessages,
                        onMostLikedPostsSelected = this@SocialWallFragment::getMostLikedSocialMessages,
                        onMostCommentedPostSelected = this@SocialWallFragment::getMostCommentedSocialMessages,
                        onClickMyActivities = this@SocialWallFragment::goToMyActivitiesFragment,
                        onCreatePostClicked = this@SocialWallFragment::goToCreateNewPost,
                        onGoToOtherFarmerProfileClicked = {
                            viewModel.selectedOtherFarmer = it
                            goToOtherFarmerProfileFragment()
                        },
                        onAddClick = this@SocialWallFragment::goToCreateNewPost,
                        onPostLike = { index, message ->
                            viewModel.onPostLikeClick(
                                requireContext(),
                                message,
                                index
                            )
                        },
                        onDeleteClicked = {
                            viewModel.onDeletePost(requireContext(), it)
                        },
                        onSearch = this@SocialWallFragment::getSearchedSocialMessages,
                        onTrendingTagSelected = this@SocialWallFragment::getSocialMessages,
                        onClose = this@SocialWallFragment::getSocialMessages
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
        getSocialMessages()
        getMySocialMessages()
        if (DataHolder.getInstance().selectedFarmer != null) {
            userName = DataHolder.getInstance().selectedFarmer.firstName
            profileImage = DataHolder.getInstance().selectedFarmer.profileImage
        }
        fetchTrendingTags()
        getBookMarks()
    }


    private fun getSocialMessages(tag: String? = null) {
        val task = GetSocialMessagesTask()
        task.context = mContext
        task.setLoadingMessage("Getting posts")
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                Log.d("SocialWallFragment", "onTaskSuccess")
                if (data is ArrayList<*>) {
                    Log.d("SocialWallFragment", "data is ArrayList")
                    val list = data.toList().reversed() as List<Message?>
                    DataHolder.getInstance().userMessageArrayList = ArrayList(list)
                    viewModel.socialMessages.clear()
                    viewModel.socialMessages.addAll(list)
                    Log.d("SocialWallFragment", "data is stored in viewModel")
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showToast("ERROR --> $errorMessage, reason: $reason")
            }
        })
        task.execute(tag)
    }


    private fun getSearchedSocialMessages() {
        val task = GetSearchedTalks()
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage(getString(R.string.searching))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is ArrayList<*>) {
                    val list = data.toList() as List<Message>
                    viewModel.socialMessages.clear()
                    viewModel.socialMessages.addAll(list)
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {

            }
        })
        task.execute(viewModel.searchText.value)
    }


    private fun getMostCommentedSocialMessages() {
        val task = GetMostCommentedSocialMessagesTask()
        task.context = mContext
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    val list = data.toList() as List<Message?>
                    viewModel.socialMessages.clear()
                    viewModel.socialMessages.addAll(list)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                mContext?.showToast("ERROR-> $errorMessage\nReason->$reason")
            }
        })
        task.execute()
    }


    private fun getMostLikedSocialMessages() {
        val task = GetMostLikedSocialMessagesTask()
        task.context = mContext
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    val list = data.toList() as List<Message?>
                    viewModel.socialMessages.clear()
                    viewModel.socialMessages.addAll(list)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                mContext?.showToast("ERROR-> $errorMessage\nReason->$reason")
            }
        })
        task.execute()
    }

    private fun getMySocialMessages() {
        val task = GetMySocialMessagesTask()
        task.context = mContext
        task.setLoadingMessage("Getting my posts...")
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    viewModel.mySocialMessages.clear()
                    viewModel.mySocialMessages.addAll(data as ArrayList<Message?>)
                    viewModel.mySocialMessages.forEach {
                        it?.myPost = true
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    private fun goToCreateNewPost() {
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FARMER_NEW_POST_FRAGMENT)
        }
    }

    private fun goToPostDetails(message: Message?) {
        DataHolder.getInstance().dataObject = message
        if (fragmentChangeHelper != null) {
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.SOCIAL_MESSAGE_FRAGMENT)
        }
    }

    private fun goToMyActivitiesFragment() {
        if (fragmentChangeHelper != null)
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.FARMER_MY_ACTIVITIES)
    }

    private fun goToOtherFarmerProfileFragment() {
        if (fragmentChangeHelper != null)
            fragmentChangeHelper.onFragmentChange(AppConstants.FragmentConstants.OTHER_FARMER_PROFILE)
    }

    private fun fetchTrendingTags() {
        val dbController = DBController(mContext)
        dbController
            .postTagDao
            .getAllTrendingTags()
            .observe(viewLifecycleOwner) { trendingTags: TrendingTags? ->
                if (trendingTags != null) {
                    DataHolder.getInstance().cropTrendingTagArrayList = trendingTags.tags
                    this.trendingTags.value = trendingTags.tags
                }
            }
        if (DataHolder.getInstance().cropTrendingTagArrayList == null
            || DataHolder.getInstance().cropTrendingTagArrayList.size == 0
        ) {
            val task = GetTrendingTags(dbController)
            task.setShowLoading(true)
            task.context = mContext
            task.execute()
        }
    }

    private fun getBookMarks() {
        with(GetAllBookMarksByType(BookmarkConstants.FARM_TALK)) {
            context = requireContext()
            setShowLoading(false)
            setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any?) {
                    if (data is ArrayList<*>) {
                        viewModel.myBookMarks.postValue(
                            data as ArrayList<BookMark>
                        )
                    }
                }

                override fun onTaskFailure(reason: String?, errorMessage: String?) {

                }

            })
            execute()
        }
    }

}