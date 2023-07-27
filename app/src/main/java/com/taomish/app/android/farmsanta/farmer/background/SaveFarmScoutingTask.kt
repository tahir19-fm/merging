package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting

class SaveFarmScoutingTask(private val farmScouting: FarmScouting) : FarmSantaBaseAsyncTask() {
    override fun doInBackground(vararg params: String?): FarmScouting? {
        return ServiceController(context).saveFarmScouting(farmScouting)
    }

    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason, errorReason)
    }
}