package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController

class GetUserGroupsTask : FarmSantaBaseAsyncTask() {

    override fun onTaskSuccess(vararg obj: Any) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason ?: "Failed", errorReason)
    }

    override fun doInBackground(vararg strings: String): MutableList<String>? {
        return ServiceController(context).userGroups
    }
}