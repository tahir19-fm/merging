package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.background.SaveSocialCommentTask
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.message.Comment
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

class SocialMessageDetailViewModel : ViewModel() {

    val selectedMessage = mutableStateOf<Message?>(null)
    val comment: MutableState<String> = mutableStateOf("")
    val textError: MutableState<Boolean> = mutableStateOf(false)

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

}