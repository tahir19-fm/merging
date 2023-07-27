package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController

class GetResetOtpTask: FarmSantaBaseAsyncTask() {
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

    override fun doInBackground(vararg strings: String?): Boolean {
        val controller = ServiceController(context)
        return controller.getResetOTP(strings[0])
    }
}