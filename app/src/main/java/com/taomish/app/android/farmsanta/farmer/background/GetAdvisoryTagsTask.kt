package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.notification.AdvisoryTag

class GetAdvisoryTagsTask() : FarmSantaBaseAsyncTask() {

    override fun onTaskSuccess(vararg obj: Any) {
        if (onTaskCompletion != null) {
            onTaskCompletion.onTaskSuccess(data)
        }
    }

    override fun onTaskFailure(reason: String?) {
        if (onTaskCompletion != null)
            onTaskCompletion?.onTaskFailure(reason ?: "Failed", errorReason)
    }

    override fun doInBackground(vararg strings: String): Array<out AdvisoryTag>? {
        return ServiceController(context).advisoryTags
    }
}