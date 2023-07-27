package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.user.BookMark

class AddToBookMarkTask(private val bookMark: BookMark) : FarmSantaBaseAsyncTask() {

    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(
            reason,
            "${context.getString(R.string.unable_to)} ${context.getString(R.string.bookmark)}"
        )
    }

    override fun doInBackground(vararg strings: String?): BookMark {
        return ServiceController(context).saveToBookMark(bookMark)
    }
}