package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_ipm_insect_management.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.IPMFieldText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.IPMLifecycle
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.pop.Insect


@Composable
fun IPMInsectDetails(insect: Insect) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        IPMLifecycle(insectLifeCycles = insect.insectLifeCycles.orEmpty())

        Spacer(modifier = Modifier.height(spacing.small))

        if (!insect.symptomsOfAttack.isNullOrEmpty()) {
            IPMFieldText(
                metadata = str(id = R.string.symptom_of_attack),
                value = insect.symptomsOfAttack ?: ""
            )
        }

        if (!insect.favourableConditions.isNullOrEmpty()) {
            IPMFieldText(
                metadata = str(id = R.string.favourable_conditions),
                value = insect.favourableConditions ?: ""
            )
        }

        if (!insect.preventiveMeasures.isNullOrEmpty()) {
            IPMFieldText(
                metadata = "${str(id = R.string.preventive_measures)}: ",
                value = insect.preventiveMeasures ?: ""
            )
        }

        if (!insect.culturalMechanicalControl.isNullOrEmpty()) {
            IPMFieldText(
                metadata = str(id = R.string.cultural_mechanical_control),
                value = insect.culturalMechanicalControl ?: ""
            )
        }
    }
}