package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_library.components.SortType
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.utils.toLocalDateTime

class GetPopListTask(private val sortType: SortType = SortType.By_Date) : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String) {
        onTaskCompletion?.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg strings: String): Array<out PopDto> {
        val pops = ServiceController(context).popList ?: arrayOf()
        if (sortType == SortType.By_Date) {
            pops.sortByDescending {
                (it.updatedTimestamp ?: it.createdTimestamp).toLocalDateTime()
            }
        } else {
            pops.sortBy { it.getCropNameById() }
        }
        return pops
    }
}