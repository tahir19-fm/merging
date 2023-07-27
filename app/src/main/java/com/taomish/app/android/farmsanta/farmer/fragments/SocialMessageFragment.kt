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
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_social_message.SocialMessageFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.constants.BookmarkConstants
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.message.Comment
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.models.api.message.MessageLikeDto
import com.taomish.app.android.farmsanta.farmer.models.api.user.BookMark
import com.taomish.app.android.farmsanta.farmer.utils.postValue

@Suppress("UNCHECKED_CAST")
class SocialMessageFragment : FarmSantaBaseFragment() {
    private var userName: String? = null
    private var message: MutableState<Message?> = mutableStateOf(null)
    private val socialWallViewModel: SocialWallViewModel by activityViewModels()
    private var commentsArrayList: ArrayList<Comment>? = null
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun init() {}
    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle,
    ): View {
        return inflater.inflate(R.layout.fragment_social_message, container, false)
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
                    message.value?.let {
                        SocialMessageFragmentScreen(
                            message = it,
                            name = userName ?: "",
                            onLikeClicked = this@SocialMessageFragment::onPostLikeClick,
                            onShareClicked = { }
                        ) { performBookmarkAction() }
                    }
                }
            }
        }
        return root
    }

    private fun performBookmarkAction() {
        message.value?.let {
            if (it.bookMarked == true)
                deleteBookMark(it)
            else
                saveBookMark(it)
        }
    }

    override fun initViewsInLayout() {

    }

    override fun initListeners() {

    }

    override fun initData() {
        val msg = DataHolder.getInstance().dataObject as? Message
        if (msg != null) {
            (msg).let {
                socialWallViewModel.selectedMessage.postValue(it)
            }
        } else {
            val args = SocialMessageFragmentArgs.fromBundle(arguments ?: Bundle())
            if (args.uuid.isNotEmpty()) {
                getMessage(args.uuid, args.languageId)
            }
        }

        getMessages()
        socialWallViewModel.myBookMarks.observe(viewLifecycleOwner) {
            val message = message.value
            this.message.postValue(null)
            message?.bookMarked = it?.any { bookmark -> bookmark.referenceUUID == message?.uuid }
            this.message.postValue(message)
        }
        if (DataHolder.getInstance().selectedFarmer != null)
            userName = DataHolder.getInstance().selectedFarmer.userName
    }

    private fun onPostLikeClick(message: Message?) {
        // check if post already liked
        if (message?.selfLike != null && message.selfLike > 0) {
            val task = GetDisLikeMessageTask(message.uuid)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    message.selfLike = 0
                    onMessageLike(false)
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.context = requireContext()
            task.setShowLoading(true)
            task.execute(message.languageId?.toString())
        } else {
            val messageLikeDto = MessageLikeDto()
            messageLikeDto.messageId = message?.uuid
            val task = SaveMessageLikeTask(messageLikeDto)
            task.context = requireContext()
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    onMessageLike(true)
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.setLoadingMessage("Liking your post")
            task.setShowLoading(false)
            task.execute(message?.languageId.toString())
        }
    }

    private fun onMessageLike(isLiked: Boolean) {
        if (isLiked) {
            message.value?.selfLike = 1
            message.value?.likes =
                if (message.value?.likes != null) message.value?.likes!! + 1 else 1
        } else {
            message.value!!.selfLike = 0
            message.value!!.likes = message.value!!.likes - 1
        }
        val copy = message.value
        message.postValue(null)
        message.postValue(copy)
    }

    fun getBookMarks() {
        with(GetAllBookMarksByType(BookmarkConstants.FARM_TALK)) {
            context = requireContext()
            setShowLoading(false)
            setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any?) {
                    if (data is ArrayList<*>) {
                        socialWallViewModel.myBookMarks.postValue(data as ArrayList<BookMark>)
                    }
                }

                override fun onTaskFailure(reason: String?, errorMessage: String?) {

                }

            })
            execute()
        }
    }

    private fun saveBookMark(message: Message) {
        val bookMark = BookMark().apply {
            bookmarkType = BookmarkConstants.FARM_TALK
            referenceUUID = message.uuid
        }
        val task = AddToBookMarkTask(bookMark)
        task.context = context
        task.setShowLoading(true)
        task.setLoadingMessage("Saving please wait...")
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                getBookMarks()
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
            }
        })
        task.execute()

    }

    private fun deleteBookMark(message: Message) {
        socialWallViewModel.myBookMarks.value?.find { it.referenceUUID == message.uuid }?.uuid?.let { bookmarkId ->
            val task = DeleteBookMarkTask(bookmarkId)
            task.context = context
            task.setShowLoading(true)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    getBookMarks()
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {

                }
            })
            task.execute()
        }

    }


    private fun getMessage(uuid: String, languageId: String) {
        val task = GetSocialMessageByIdTask()
        task.context = mContext
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is Message) {
                    (data as? Message)?.let {
                        Log.d("SocialMessageFragment", "message found : ${it.title}")
                        message.value = it
                        socialWallViewModel.selectedMessage.value = it
                    }
                    getBookMarks()
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {

            }
        })
        task.execute(uuid, languageId)
    }

    private fun getMessages() {
        val dataObject = DataHolder.getInstance().dataObject
        if (dataObject is Message) {
            message.value = dataObject
            commentsArrayList = message.value?.comments as ArrayList<Comment>?
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DataHolder.getInstance().dataObject = null
    }
}