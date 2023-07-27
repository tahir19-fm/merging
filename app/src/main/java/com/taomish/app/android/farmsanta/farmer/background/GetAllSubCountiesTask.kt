package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.master.SubCounty

class GetAllSubCountiesTask : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): SubCounty? {
        val result = ServiceController(context).allSubCounties
        DataHolder.getInstance().setAllSubCounties(result)
        if (string.isNotEmpty()) {
            string[0]?.let { name ->
                result.forEach {
                    if (it.subcountyName == name) return it
                }
            }
        }
        return null
    }
}