package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.IPMFieldRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.pop.CropNutrition
import com.taomish.app.android.farmsanta.farmer.utils.ifNull
import com.taomish.app.android.farmsanta.farmer.utils.notNull


@Composable
fun NutritionItem(getCropName: (String?) -> String?, cropNutrition: CropNutrition) {
    val shape = LocalShapes.current.mediumShape
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .border(width = .3.dp, color = Color.LightGray, shape = shape)
    ) {
        Spacer(modifier = Modifier.padding(spacing.extraSmall))
        IPMFieldRow(
            metaData = str(id = R.string.crop),
            value = getCropName(cropNutrition.crop).notNull()
        )
        Spacer(modifier = Modifier.padding(spacing.extraSmall))

        Divider(thickness = .3.dp, color = Color.LightGray)

        cropNutrition.dosages?.forEachIndexed { index, dosage ->
            IPMFieldRow(
                metaData = str(id = R.string.growth_stage),
                value = dosage.growthStage.notNull()
            )

            IPMFieldRow(
                metaData = str(id = R.string.nitrogen),
                value = "${dosage.nitrogen.ifNull { 0.0 }}"
            )
            IPMFieldRow(
                metaData = str(id = R.string.phosphorus),
                value = "${dosage.phosphorus.ifNull { 0.0 }}"
            )

            IPMFieldRow(
                metaData = str(id = R.string.potassium),
                value = "${dosage.potassium.ifNull { 0.0 }}"
            )

            IPMFieldRow(
                metaData = str(id = R.string.zinc),
                value = "${dosage.zinc.ifNull { 0.0 }}"
            )

            IPMFieldRow(
                metaData = str(id = R.string.sulphur),
                value = "${dosage.sulphur.ifNull { 0.0 }}"
            )

            if (index != cropNutrition.dosages?.lastIndex) {
                Divider(thickness = .3.dp, color = Color.LightGray)
            }
        }
    }
}