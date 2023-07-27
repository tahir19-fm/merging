package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage


class GetCropStageListByCropId(val uuid: String) : FarmSantaBaseAsyncTask() {
    override fun doInBackground(vararg params: String?): Array<out CropStage>? {
        return ServiceController(context).allCropStages
        /*val serviceList = ServiceController(context).allCropStages
        val arrayString = Gson().toJson(serviceList)
        val crops = Gson().fromJson(
            arrayString,
            Array<DB_CropStage>::class.java
        )
        val list = ArrayList<DB_CropStage>()
        Collections.addAll(list, *crops)
        DBController(context).saveAllCropStages(list)
        val dbCropStage = if (uuid.isEmpty()) {
            DBController(context).allCropStages
        } else {
            DBController(context).getCropStage(uuid)
        }
        val string = Gson().toJson(dbCropStage)
        return Gson().fromJson(string, Array<CropStage>::class.java)*/

    }

    override fun onTaskSuccess(vararg obj: Any?) {
        if (onTaskCompletion != null)
            onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        if (onTaskCompletion != null)
            onTaskCompletion?.onTaskFailure(reason ?: "Error", errorReason)
    }
}

