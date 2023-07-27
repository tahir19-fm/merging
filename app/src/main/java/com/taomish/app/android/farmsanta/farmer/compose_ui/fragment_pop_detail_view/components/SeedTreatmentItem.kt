package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.pop.SeedTreatment
import com.taomish.app.android.farmsanta.farmer.utils.notNull


@Composable
fun SeedTreatmentItem(seedTreatment: SeedTreatment) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .border(width = .3.dp, color = Color.LightGray, shape = shape)
    ) {
        Spacer(modifier = Modifier.height(spacing.small))

        if (!seedTreatment.formulation.isNullOrEmpty()) {
            SeedMetaValueRow(
                metaData = str(id = R.string.formulation),
                value = seedTreatment.formulation.notNull()
            )
        }

        if (!seedTreatment.productType.isNullOrEmpty()) {
            SeedMetaValueRow(
                metaData = str(id = R.string.product_type),
                value = seedTreatment.productType.notNull()
            )
        }

        if (!seedTreatment.productName.isNullOrEmpty()) {
            SeedMetaValueRow(
                metaData = str(id = R.string.product_name),
                value = seedTreatment.productName?.joinToString() ?: "-"
            )
        }

        if (seedTreatment.dosage != null) {
            SeedMetaValueRow(
                metaData = str(id = R.string.dosage_per_kg_of_seed),
                value = seedTreatment.dosage?.let { "${it.unit ?: 0F}${it.uom.notNull()}" } ?: "-"
            )
        }

        if (!seedTreatment.applicationMethod.isNullOrEmpty()) {
            SeedMetaValueRow(
                metaData = str(id = R.string.application_method),
                value = seedTreatment.applicationMethod.notNull()
            )
        }

        if (!seedTreatment.modeOfAction.isNullOrEmpty()) {
            SeedMetaValueRow(
                metaData = str(id = R.string.mode_of_action),
                value = seedTreatment.modeOfAction.notNull()
            )
        }

        if (!seedTreatment.toxicityLevel.isNullOrEmpty()) {
            SeedMetaValueRow(
                metaData = str(id = R.string.toxicity_level),
                value = seedTreatment.toxicityLevel.notNull()
            )
        }

        if (!seedTreatment.waitingPeriod.isNullOrEmpty()) {
            SeedMetaValueRow(
                metaData = str(id = R.string.waiting_period),
                value = seedTreatment.waitingPeriod.notNull()
            )
        }

        if (seedTreatment.waterRequirement != null) {
            SeedMetaValueRow(
                metaData = str(id = R.string.water_requirement),
                value = seedTreatment.waterRequirement?.let { "${it.unit ?: 0F}${it.uom.notNull()}" }
                    ?: "-"
            )
        }

        Spacer(modifier = Modifier.height(spacing.small))
    }
}