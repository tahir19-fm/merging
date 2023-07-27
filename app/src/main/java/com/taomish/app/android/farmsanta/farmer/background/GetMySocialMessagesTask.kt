package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.utils.asDate
import com.taomish.app.android.farmsanta.farmer.utils.toLocalDateTime

class GetMySocialMessagesTask : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any) {
        if (onTaskCompletion != null) {
            onTaskCompletion.onTaskSuccess(data)
        }
    }

    override fun onTaskFailure(reason: String) {
        if (onTaskCompletion != null) {
            onTaskCompletion.onTaskFailure("Service Error", errorReason)
        }
    }

    override fun doInBackground(vararg strings: String): ArrayList<Message> {
        return ServiceController(context).myPosts?.apply {
            sortByDescending {
                (it.updatedTimestamp ?: it.createdTimestamp)?.toLocalDateTime()?.asDate()
            }

            forEach { message ->
                message?.tags = message?.tags?.map { it?.replace("#", "")?.trim()?.trimIndent() ?: "" }
                    ?.filter { it.isNotEmpty() }
            }
        } ?: ArrayList()
    }
}