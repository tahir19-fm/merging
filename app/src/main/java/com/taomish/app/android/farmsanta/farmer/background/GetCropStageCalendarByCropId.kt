package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.crop_calendar.CropStageCalendar

class GetCropStageCalendarByCropId : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): ArrayList<CropStageCalendar> {
        return ServiceController(context).getCropsStageCalendarListById(string[0], string[1])
            .onEach { cropStageCalendar ->
                cropStageCalendar.weeks =
                    cropStageCalendar.weeks.sortedBy { it.weekInfo ?: Int.MIN_VALUE }
            }
    }
}