package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message

class GetSearchedTalks : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): ArrayList<Message> {
        return ServiceController(context).getSearchedTalks(string[0])?.onEach { message ->
            message?.tags = message?.tags?.map { it?.replace("#", "")?.trim()?.trimIndent() ?: "" }
                ?.filter { it.isNotEmpty() }
        } ?: ArrayList()
    }
}