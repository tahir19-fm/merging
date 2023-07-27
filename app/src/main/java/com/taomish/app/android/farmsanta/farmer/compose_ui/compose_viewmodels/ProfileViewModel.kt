package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.background.GetCropListTask
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster

@Suppress("UNCHECKED_CAST")
class ProfileViewModel : ViewModel() {

    val farmer: MutableState<Farmer?> = mutableStateOf(null)
    val allNotificationChecked = mutableStateOf(false)
    val talksActivityNotificationChecked = mutableStateOf(false)
    val farmTalksNotificationChecked = mutableStateOf(true)
    val alertAdvisoryNotificationChecked = mutableStateOf(false)
    val alertPopNotificationChecked = mutableStateOf(true)
    val newsAlertChecked = mutableStateOf(false)
    var myCrops = mutableStateListOf<CropMaster>()
    var profileBitmap by mutableStateOf<Bitmap?>(null)
    var isImageSelected by mutableStateOf(false)
    var region by mutableStateOf("")

    fun fetchMyCrops(context: Context) {
        myCrops.clear()
        if (DataHolder.getInstance().cropMasterMap.isNotEmpty()) {
            DataHolder.getInstance().cropMasterMap?.let { map ->
                farmer.value?.crop1?.let { map[it]?.let { crop -> myCrops.add(crop) } }
                farmer.value?.crop2?.let { map[it]?.let { crop -> myCrops.add(crop) } }
                farmer.value?.crop3?.let { map[it]?.let { crop -> myCrops.add(crop) } }
            }
            DataHolder.getInstance().setMyCrops(myCrops.toTypedArray())
            return
        }
        val task = GetCropListTask()
        task.context = context
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is ArrayList<*>) {
                    if (data.isNotEmpty()) {
                        DataHolder.getInstance().cropMasterMap?.let { map ->
                            farmer.value?.crop1?.let { map[it]?.let { crop -> myCrops.add(crop) } }
                            farmer.value?.crop2?.let { map[it]?.let { crop -> myCrops.add(crop) } }
                            farmer.value?.crop3?.let { map[it]?.let { crop -> myCrops.add(crop) } }
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {

            }
        })
        task.execute()
    }
}