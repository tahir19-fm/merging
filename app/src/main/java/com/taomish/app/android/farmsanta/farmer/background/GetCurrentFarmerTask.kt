package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer

class GetCurrentFarmerTask : FarmSantaBaseAsyncTask() {

    override fun onTaskSuccess(vararg obj: Any) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason ?: "Failed", errorReason)
    }

    override fun doInBackground(vararg strings: String): Farmer? {
        val farmer = ServiceController(context).currentFarmer
        farmer?.lands?.forEach {
            it?.coordinates = it?.coordinates?.sortedBy { c -> c.index }
         }
        return farmer
    }
}