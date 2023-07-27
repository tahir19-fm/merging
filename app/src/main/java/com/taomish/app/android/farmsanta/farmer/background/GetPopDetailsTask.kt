package com.taomish.app.android.farmsanta.farmer.background

import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDetailsDTO

class GetPopDetailsTask : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any?) {
        onTaskCompletion.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion.onTaskFailure(reason, errorReason)
    }

    override fun doInBackground(vararg string: String?): PopDetailsDTO? {
        return ServiceController(context).getPopDetailsResponse(string[0]).apply {
            deficiencyDto?.forEach { deficiencyDto ->
                deficiencyDto.symptomsOfDeficiency = deficiencyDto.symptomsOfDeficiency?.trim()?.trimIndent()
                deficiencyDto.biologicalControl = deficiencyDto.biologicalControl?.trim()?.trimIndent()
                deficiencyDto.chemicalControl = deficiencyDto.chemicalControl?.trim()?.trimIndent()
                deficiencyDto.preventiveMeasures = deficiencyDto.preventiveMeasures?.trim()?.trimIndent()
                deficiencyDto.nutrient = deficiencyDto.nutrient?.trim()?.trimIndent()
                deficiencyDto.nutrientId = deficiencyDto.nutrientId?.trim()?.trimIndent()
            }

            diseases?.forEach { disease ->
                disease.cultivarGroups =
                    disease.cultivarGroups?.map { it.trim().trim().trimIndent() }
                disease.culturalMechanicalControl =
                    disease.culturalMechanicalControl?.trim()?.trimIndent()
                disease.favourableConditions = disease.favourableConditions?.trim()?.trimIndent()
                disease.localName = disease.localName?.trim()?.trimIndent()
                disease.preventiveMeasures = disease.preventiveMeasures?.trim()?.trimIndent()
                disease.scientificName = disease.scientificName?.trim()?.trimIndent()
                disease.symptomsOfAttack = disease.symptomsOfAttack?.trim()?.trimIndent()
            }

            insects?.forEach { insect ->
                insect.cultivarGroups = insect.cultivarGroups?.map { it.trim().trimIndent() }
                insect.insectLifeCycles = insect.insectLifeCycles?.map {
                    it.apply {
                        it.lifecycleStage = it.lifecycleStage?.trim()?.trimIndent()
                        it.characteristics = it.characteristics?.trim()?.trimIndent()
                        it.symptomsOfAttack = it.symptomsOfAttack?.trim()?.trimIndent()
                    }
                }
                insect.culturalMechanicalControl =
                    insect.culturalMechanicalControl?.trim()?.trimIndent()
                insect.favourableConditions = insect.favourableConditions?.trim()?.trimIndent()
                insect.localName = insect.localName?.trim()?.trimIndent()
                insect.preventiveMeasures = insect.preventiveMeasures?.trim()?.trimIndent()
                insect.scientificName = insect.scientificName?.trim()?.trimIndent()
                insect.symptomsOfAttack = insect.symptomsOfAttack?.trim()?.trimIndent()
            }

            weeds?.forEach { weed ->
                weed.cultivarGroups = weed.cultivarGroups?.map { it.trim().trimIndent() }
                weed.culturalControl = weed.culturalControl?.trim()?.trimIndent()
                weed.favourableConditions = weed.favourableConditions?.trim()?.trimIndent()
                weed.localName = weed.localName?.trim()?.trimIndent()
                weed.description = weed.description?.trim()?.trimIndent()
                weed.scientificName = weed.scientificName?.trim()?.trimIndent()
            }
        }
    }
}