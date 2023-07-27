package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.DBController
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.CategoryItem

class GetCropCategoryTask: FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        if (onTaskCompletion != null)
            onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        if (onTaskCompletion != null)
            onTaskCompletion?.onTaskFailure(reason ?: "Error", errorReason)
    }

    override fun doInBackground(vararg string: String?): List<CategoryItem?> {
        val db = DBController(context)
        val dbList = db.allCategories
        return if (dbList.isNullOrEmpty().not()) {
            dbList
        } else {
            val serverResponse = ServiceController(context).scoutingCategory.toMutableList()
            db.saveAllCat(serverResponse)
            serverResponse
        }
    }
}