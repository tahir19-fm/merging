package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.models.api.notification.AdvisoryTag
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.utils.asDate
import com.taomish.app.android.farmsanta.farmer.utils.toLocalDateTime

class GetFilteredAdvisory(
    private val crops: List<CropMaster>,
    private val growthStages: List<GlobalIndicatorDTO>,
    private val advisoryTags: List<AdvisoryTag>,
) : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): List<CropAdvisory> {
        val filteredAdvisories = ArrayList<CropAdvisory>()
        var advisories = DataHolder.getInstance().cropAdvisoryArrayList.associateBy { it.crop }
        crops.forEach { crop -> advisories[crop.uuid]?.let { filteredAdvisories.add(it) } }
        advisories = DataHolder.getInstance().cropAdvisoryArrayList.associateBy { it.growthStage }
        growthStages.forEach { stage -> advisories[stage.uuid]?.let { filteredAdvisories.add(it) } }
        advisories = DataHolder.getInstance().cropAdvisoryArrayList.associateBy { it.advisoryTag }
        advisoryTags.forEach { tag -> advisories[tag.uuid]?.let { filteredAdvisories.add(it) } }
        return filteredAdvisories.distinctBy { it.uuid }
            .sortedByDescending {
                (it.updatedTimestamp ?: it.createdTimestamp ?: "").toLocalDateTime().asDate()
            }
    }
}