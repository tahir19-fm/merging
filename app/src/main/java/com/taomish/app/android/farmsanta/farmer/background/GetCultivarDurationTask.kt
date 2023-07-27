package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO

class GetCultivarDurationTask : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason ?: "Error", errorReason)
    }

    override fun doInBackground(vararg strings: String): ArrayList<GlobalIndicatorDTO>? {
        return ServiceController(context).cultivarDuration
    }
}