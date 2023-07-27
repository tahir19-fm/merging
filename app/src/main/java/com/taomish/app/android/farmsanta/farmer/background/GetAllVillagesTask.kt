package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.master.Village

class GetAllVillagesTask : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): Village? {
        val result = ServiceController(context).allVillages
        DataHolder.getInstance().setAllVillages(result)
        if (string.isNotEmpty()) {
            string[0]?.let { name ->
                result.forEach {
                    if (it.villageName == name) return it
                }
            }
        }
        return null
    }
}