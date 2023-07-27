package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer

class SaveFarmerTask(private val farmer: Farmer): FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): Farmer? {
        val result = ServiceController(context).saveFarmer(farmer)
        result?.lands?.forEach {
            it?.coordinates = it?.coordinates?.sortedBy { c -> c.index }
        }
        DataHolder.getInstance().selectedFarmer = result
        return result
    }
}