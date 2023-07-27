package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.DBController
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.master.Region

class GetRegionsTask(private val dbController: DBController? = null) : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any) {
        if (onTaskCompletion != null) {
            onTaskCompletion.onTaskSuccess(data)
        }
    }

    override fun onTaskFailure(reason: String) {
        if (onTaskCompletion != null)
            onTaskCompletion?.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg strings: String): Array<out Region>? {
        val data = ServiceController(context).regions
        data?.let { DataHolder.getInstance().setRegions(it) }
        if (data.isNotEmpty()) {
            dbController?.masterDao?.insertAllRegion(data.toList())
        }
        return data
    }
}