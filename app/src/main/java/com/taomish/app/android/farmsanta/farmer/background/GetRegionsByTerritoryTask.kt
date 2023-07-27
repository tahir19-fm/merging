package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController

class GetRegionsByTerritoryTask : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): Any {
        return ServiceController(context).getRegionsByTerritory(string[0])
    }
}