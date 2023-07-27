package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster

class GetCropListTask : FarmSantaBaseAsyncTask() {
    override fun doInBackground(vararg params: String?): ArrayList<CropMaster> {
        val list = ServiceController(context).allCrops?.apply {
            sortBy { it.cropName ?: "" }
        } ?: emptyList<CropMaster>()
        DataHolder.getInstance().cropArrayList = ArrayList(list)
        return ArrayList(list.filter { it.cropName?.trim()?.isNotEmpty() == true })
    }


    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason, errorReason)
    }
}