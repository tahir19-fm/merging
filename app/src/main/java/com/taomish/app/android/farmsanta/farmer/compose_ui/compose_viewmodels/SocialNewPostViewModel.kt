package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.background.SaveFarmScoutImageTask
import com.taomish.app.android.farmsanta.farmer.background.SaveSocialMessageTask
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.master.Region
import com.taomish.app.android.farmsanta.farmer.models.api.message.Image
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.models.api.profile.UploadedFile
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import java.io.File

@SuppressLint("MutableCollectionMutableState")
class SocialNewPostViewModel : ViewModel() {

    val allRegionsList = mutableStateListOf<String>()
    val postTitleText: MutableState<String> = mutableStateOf("")
    val talkDetailText: MutableState<String> = mutableStateOf("")
    val tagText: MutableState<String> = mutableStateOf("")
    val selectedRegions = mutableStateListOf<String>()
    val tags = mutableStateListOf<String>()
    val images = mutableStateListOf<File>()
    var regionArrayList: List<Region> = emptyList()
    val uploadedImageFiles = mutableListOf<String>()


    fun addTag() {
        tags.add(tagText.value)
        tagText.postValue("")
    }

    fun deleteTag(tag: String) {
        tags.remove(tag)
    }

    fun deleteRegion(region: String) {
        selectedRegions.remove(region)
    }

    fun postMessage(context: Context, onTaskSuccess: () -> Unit) {
        if (images.isNotEmpty()) {
            uploadFiles(context, 0, onTaskSuccess)
        } else {
            uploadFarmTalkPost(context, onTaskSuccess)
        }
    }

    private fun uploadFarmTalkPost(context: Context, onTaskSuccess: () -> Unit) {
        val message = Message()
        message.title = postTitleText.value
        message.description = talkDetailText.value
        message.tags = tags
        message.firstName = DataHolder.getInstance().selectedFarmer.firstName
        message.images = uploadedImageFiles.map {
            Image().apply {
                fileName = it
            }
        }
        val task = SaveSocialMessageTask(message)
        task.context = context
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                onTaskSuccess()
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {

            }
        })
        task.setLoadingMessage("Saving your post")
        task.setShowLoading(true)
        task.execute()
    }

    private fun uploadFiles(context: Context, fileIndex: Int, onTaskSuccess: () -> Unit) {
        val task = SaveFarmScoutImageTask(images[fileIndex])
        task.context = context
        task.setLoadingMessage("Saving Images")
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                val savedFileName = (data as UploadedFile).fileName
                uploadedImageFiles.add(savedFileName)
                if (images.size > fileIndex.plus(1)) {
                    uploadFiles(context, fileIndex.plus(1), onTaskSuccess)
                } else {
                    uploadFarmTalkPost(context, onTaskSuccess)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {

            }
        })
        task.execute()
    }

}