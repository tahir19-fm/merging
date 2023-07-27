package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.disease.Disease
import com.taomish.app.android.farmsanta.farmer.utils.asDate
import com.taomish.app.android.farmsanta.farmer.utils.toLocalDateTime

class GetAllCropDiseases : FarmSantaBaseAsyncTask() {

    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): Array<out Disease> {
        return ServiceController(context).allCropDiseases.apply {
            sortByDescending {
                (it.updatedTimestamp ?: it.createdTimestamp)?.toLocalDateTime()?.asDate()
            }

            forEach { disease ->
                disease.cultivarGroups =
                    disease.cultivarGroups?.map { it?.trim()?.trim()?.trimIndent() }
                disease.culturalMechanicalControl =
                    disease.culturalMechanicalControl?.trim()?.trimIndent()
                disease.favourableConditions = disease.favourableConditions?.trim()?.trimIndent()
                disease.localName = disease.localName?.trim()?.trimIndent()
                disease.preventiveMeasures = disease.preventiveMeasures?.trim()?.trimIndent()
                disease.scientificName = disease.scientificName?.trim()?.trimIndent()
                disease.symptomsOfAttack = disease.symptomsOfAttack?.trim()?.trimIndent()
            }
        }
    }
}