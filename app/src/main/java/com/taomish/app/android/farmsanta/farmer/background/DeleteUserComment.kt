package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController

class DeleteUserComment(private val uuid: String): FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        if (onTaskCompletion != null) {
            onTaskCompletion.onTaskSuccess(data)
        }
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(
            reason,
            "${context.getString(R.string.unable_to)} ${context.getString(R.string.delete)}"
        )
    }

    override fun doInBackground(vararg string: String?): Any {
        return ServiceController(context).deleteUserComment(uuid)
    }
}