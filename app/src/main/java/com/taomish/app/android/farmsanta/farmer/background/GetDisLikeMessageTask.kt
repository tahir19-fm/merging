package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController

class GetDisLikeMessageTask(val uuid: String): FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        if(onTaskCompletion!=null)
            onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        if(onTaskCompletion!=null)
            onTaskCompletion?.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): Any {
        return ServiceController(context).saveMessageDisLike(uuid, string[0])
    }
}