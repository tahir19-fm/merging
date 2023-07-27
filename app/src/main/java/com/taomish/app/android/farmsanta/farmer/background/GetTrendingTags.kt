package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.DBController
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.tags.TrendingTags

class GetTrendingTags(private val dbController: DBController) : FarmSantaBaseAsyncTask() {

    override fun onTaskSuccess(vararg obj: Any) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason ?: "Failed", errorReason)
    }

    override fun doInBackground(vararg strings: String): Array<out String> {
        val tags = ServiceController(context).trendingTags?.map { tag ->
            tag?.replace("#", "")?.trim()?.trimIndent() ?: ""
        }?.filter { it.isNotEmpty() }?.toTypedArray() ?: arrayOf()
        dbController.postTagDao.insertAllTrendingTags(TrendingTags(tags = tags.toList()))
        return tags
    }
}