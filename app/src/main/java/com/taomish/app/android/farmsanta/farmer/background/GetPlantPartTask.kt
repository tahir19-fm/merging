package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO

class GetPlantPartTask : FarmSantaBaseAsyncTask() {

    override fun onTaskSuccess(vararg obj: Any?) {
        if (onTaskCompletion != null)
            onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        if (onTaskCompletion != null)
            onTaskCompletion?.onTaskFailure(reason ?: "Error", errorReason)
    }

    override fun doInBackground(vararg string: String?): ArrayList<GlobalIndicatorDTO> {
        return ServiceController(context).plantPart
    }
}