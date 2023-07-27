package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.pop.CroppingProcessDto
import java.util.ArrayList

class GetCroppingProcessByIdTask : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): ArrayList<CroppingProcessDto>? {
        return ServiceController(context).getCroppingProcessById(string[0])?.apply {
            if (isNotEmpty()) {
                firstOrNull()?.apply {
                    processlist?.sortBy { it.sequenceId ?: 0 }
                }
            }
        }
    }
}