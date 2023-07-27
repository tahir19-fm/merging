package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.background.DeleteUserPostTask
import com.taomish.app.android.farmsanta.farmer.background.GetDisLikeMessageTask
import com.taomish.app.android.farmsanta.farmer.background.SaveMessageLikeTask
import com.taomish.app.android.farmsanta.farmer.background.SaveSocialCommentTask
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.message.Comment
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.models.api.message.MessageLikeDto
import com.taomish.app.android.farmsanta.farmer.models.api.user.BookMark
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import com.taomish.app.android.farmsanta.farmer.R

class SocialWallViewModel : ViewModel() {

    var selectedOtherFarmer by mutableStateOf<Message?>(null)
    val selectedMessage = mutableStateOf<Message?>(null)
    val comment: MutableState<String> = mutableStateOf("")
    val textError: MutableState<Boolean> = mutableStateOf(false)
    var socialMessages = mutableStateListOf<Message?>()
    var mySocialMessages = mutableStateListOf<Message?>()
    var myBookMarks = MutableLiveData<List<BookMark>>()
    val searchText = mutableStateOf("")

    fun onComment(context: Context, userName: String, refresh: () -> Unit) {
        val message = Comment()
        message.comment = comment.value
        message.messageId = selectedMessage.value?.uuid ?: ""
        message.userName = userName
        message.firstName = DataHolder.getInstance().selectedFarmer.firstName
        message.lastName = DataHolder.getInstance().selectedFarmer.lastName

        val task = SaveSocialCommentTask(message)
        task.context = context
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                context.showToast("comment is added successfully")
                comment.postValue("")
                refresh()
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                context.showToast("ERROR! -> reason : $reason \n message : $errorMessage")
                comment.postValue("")
            }
        })
        task.setLoadingMessage("Saving your post")
        task.setShowLoading(false)
        task.execute()
    }

    fun onPostLikeClick(context: Context, message: Message?, index: Int) {
        // check if post already liked
        if (message?.selfLike != null && message.selfLike > 0) {
            val task = GetDisLikeMessageTask(message.uuid)
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    message.selfLike = 0
                    message.likes = ((message.likes ?: 0) - 1)
                    if (message.myPost == true) {
                        mySocialMessages.removeAt(index)
                        if (index < mySocialMessages.size) {
                            mySocialMessages.add(index, message)
                        } else {
                            mySocialMessages.add(message)
                        }
                    } else {
                        socialMessages.removeAt(index)
                        if (index < socialMessages.size) {
                            socialMessages.add(index, message)
                        } else {
                            socialMessages.add(message)
                        }
                    }
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {
                    context.showToast("${context.getString(R.string.reason)} : $reason")
                }
            })
            task.context = context
            task.setShowLoading(true)
            task.execute(message.languageId?.toString())
        } else {
            val messageLikeDto = MessageLikeDto()
            messageLikeDto.messageId = message?.uuid
            messageLikeDto.createdBy = DataHolder.getInstance().selectedFarmer?.userId
            val task = SaveMessageLikeTask(messageLikeDto)
            task.context = context
            task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
                override fun onTaskSuccess(data: Any) {
                    (data as Boolean?)?.let {
                        if (it) {
                            message?.selfLike = 1
                            message?.likes = ((message?.likes ?: 0) + 1)
                            if (message?.myPost == true) {
                                mySocialMessages.removeAt(index)
                                if (index < mySocialMessages.size) {
                                    mySocialMessages.add(index, message)
                                } else {
                                    mySocialMessages.add(message)
                                }
                            } else {
                                socialMessages.removeAt(index)
                                if (index < socialMessages.size) {
                                    socialMessages.add(index, message)
                                } else {
                                    socialMessages.add(message)
                                }
                            }
                        }
                    }
                }

                override fun onTaskFailure(reason: String, errorMessage: String) {}
            })
            task.setLoadingMessage("Liking your post")
            task.setShowLoading(true)
            task.execute(message?.languageId.toString())
        }
    }

    fun onDeletePost(context: Context, message: Message?) {
        val deleteTask = DeleteUserPostTask(message?.uuid ?: "")
        deleteTask.context = context
        deleteTask.setShowLoading(true)
        deleteTask.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                mySocialMessages.remove(message)
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {

            }

        })
        deleteTask.execute()
    }

}