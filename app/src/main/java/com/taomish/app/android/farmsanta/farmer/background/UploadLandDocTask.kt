package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import java.io.File
import java.util.ArrayList

class UploadLandDocTask(private var file: ArrayList<File>) : FarmSantaBaseAsyncTask() {


    override fun onTaskSuccess(vararg obj: Any?) {
        if (onTaskCompletion != null) {
            onTaskCompletion.onTaskSuccess(data)
        }
    }

    override fun onTaskFailure(reason: String?) {
        if (onTaskCompletion != null) {
            onTaskCompletion.onTaskFailure(reason, errorReason)
        }
    }

    override fun doInBackground(vararg strings: String?): ArrayList<String>? {
        val controller = ServiceController(context)
        return controller.uploadLandDoc(file)
    }
}