package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.CultivationType
import java.util.*

class GetUnitOfMeasureTask : FarmSantaBaseAsyncTask(){
    override fun doInBackground(vararg params: String?): ArrayList<CultivationType> {
        return ArrayList(listOf(*ServiceController(context).unitOfMeasure))
    }

    override fun onTaskSuccess(vararg obj: Any?) {
        if (onTaskCompletion != null) {
            onTaskCompletion.onTaskSuccess(data)
        }
    }

    override fun onTaskFailure(reason: String?) {
        if (onTaskCompletion != null) {
            onTaskCompletion.onTaskFailure(reason, errorReason)
        }
    }
}