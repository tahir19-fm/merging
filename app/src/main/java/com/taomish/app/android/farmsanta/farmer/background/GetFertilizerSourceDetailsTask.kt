package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.FertilizerSourceDetails

class GetFertilizerSourceDetailsTask : FarmSantaBaseAsyncTask() {

    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
       onTaskCompletion?.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): List<FertilizerSourceDetails>? {
        return ServiceController(context).fertilizerSourceDetails?.toMutableList()
    }
}