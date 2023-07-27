package com.taomish.app.android.farmsanta.farmer.models.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.models.api.message.Comment
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message

class CommentViewModel: ViewModel() {
    val buttonAction = MutableLiveData<Int>()
    val apiStatus = MutableLiveData<Int>()
    var commentText: String? = null
    var comment: Comment? = null
    var position: Int = -1

    fun resetData() {
        apiStatus.postValue(-1)
        buttonAction.postValue(-1)
    }
}