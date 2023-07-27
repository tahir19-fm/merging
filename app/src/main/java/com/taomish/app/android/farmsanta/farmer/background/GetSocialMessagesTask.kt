package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message

class GetSocialMessagesTask : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String) {
        onTaskCompletion?.onTaskFailure("Service Error", errorReason)
    }

    override fun doInBackground(vararg strings: String): ArrayList<Message> {
        return ServiceController(context).getPosts(strings[0])?.onEach { message ->
            message?.tags = message?.tags?.map { it?.replace("#", "")?.trim()?.trimIndent() ?: "" }
                ?.filter { it.isNotEmpty() }
        } ?: ArrayList()
    }
}