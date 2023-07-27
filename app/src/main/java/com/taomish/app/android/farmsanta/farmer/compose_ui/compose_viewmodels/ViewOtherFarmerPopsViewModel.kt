package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.background.GetBookmarkedPopList
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

@SuppressLint("MutableCollectionMutableState")
class ViewOtherFarmerPopsViewModel : ViewModel() {

    val popsList = mutableStateOf<ArrayList<PopDto>?>(null)
    val searchText = mutableStateOf("")
    val searchTags = listOf(
        "All", "Apple", "Mango", "Orange", "Strawberry", "Grapes", "Pomegranate",
        "Pear", "Avocado", "Banana", "Blueberry", "Lemon", "Kiwi", "Pineapple",
    )

    val selected = mutableStateOf(searchTags[0])

    @Suppress("UNCHECKED_CAST")
    fun fetchPopList(context: Context) {
        val task = GetBookmarkedPopList()
        task.context = context
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    popsList.postValue(data as ArrayList<PopDto>)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                context.showToast("Reason--> $reason, \nError--> $errorMessage")
            }
        })
        task.execute()
    }

}