package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.models.api.user.BookMark


class GetBookmarkedPopList : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any) {
        onTaskCompletion?.onTaskSuccess(data)

    }

    override fun onTaskFailure(reason: String) {
        onTaskCompletion?.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg strings: String): ArrayList<PopDto> {
        val bookmarks = ServiceController(context).bookmarkedPopList
        val allPops = DataHolder.getInstance().popDtoArrayList.associateBy { it.uuid }
        val bookmarkedPops = ArrayList<PopDto>()
        bookmarks.forEach {
            allPops[it.referenceUUID]?.let { pop ->
                pop.bookmarkedUUID = it.uuid
                bookmarkedPops.add(pop)
            }
        }
        return bookmarkedPops
    }
}