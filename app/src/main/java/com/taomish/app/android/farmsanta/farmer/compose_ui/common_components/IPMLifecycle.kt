package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.pop.InsectLifeCycle


@Composable
fun IPMLifecycle(insectLifeCycles: List<InsectLifeCycle>) {
    val spacing = LocalSpacing.current
    Column(verticalArrangement = Arrangement.spacedBy(spacing.small)) {
        insectLifeCycles.forEach {

            if (!it.characteristics.isNullOrEmpty()) {
                IPMFieldText(
                    metadata = if (!it.lifecycleStage.isNullOrEmpty()) "${it.lifecycleStage} : " else "",
                    value = "${it.characteristics}"
                )
            }

            if (!it.symptomsOfAttack.isNullOrEmpty()) {
                IPMFieldText(
                    metadata = str(id = R.string.symptom_of_attack),
                    value = it.symptomsOfAttack ?: ""
                )
            }
        }
    }
}