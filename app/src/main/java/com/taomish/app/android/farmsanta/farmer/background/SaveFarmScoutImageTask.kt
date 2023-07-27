package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.profile.UploadedFile
import java.io.File

class SaveFarmScoutImageTask(val file: File) : FarmSantaBaseAsyncTask() {

    override fun doInBackground(vararg params: String?): UploadedFile? {
        val controller = ServiceController(context)
        return controller.uploadFarmScoutImage(file)
    }

    override fun onTaskSuccess(vararg obj: Any?) {
        if (onTaskCompletion != null) {
            onTaskCompletion.onTaskSuccess(data)
        }
    }

    override fun onTaskFailure(reason: String?) {
        if (onTaskCompletion != null) {
            onTaskCompletion.onTaskFailure(reason ?: "Error", errorReason)
        }
    }
}