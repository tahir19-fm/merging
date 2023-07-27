package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO

class GetCropCalendarStagesTask : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion?.onTaskSuccess(data)
        onTaskCompletion?.onTaskComplete()
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason, errorReason)
        onTaskCompletion?.onTaskComplete()
    }

    override fun doInBackground(vararg string: String?): List<GlobalIndicatorDTO> {
        return ServiceController(context).cropCalendarStagesIndicators
    }
}