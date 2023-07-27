package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.user.BookMark

class GetAllBookMarksByType(val type: String): FarmSantaBaseAsyncTask() {

    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): ArrayList<BookMark> {
        return ServiceController(context).getBookMarksByType(type)
    }
}