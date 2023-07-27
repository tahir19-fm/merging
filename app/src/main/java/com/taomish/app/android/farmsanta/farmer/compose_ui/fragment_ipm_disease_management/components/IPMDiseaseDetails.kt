package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_ipm_disease_management.components

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
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.pop.Disease


@Composable
fun IPMDiseaseDetails(disease: Disease) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        Spacer(modifier = Modifier.height(spacing.small))

        if (!disease.symptomsOfAttack.isNullOrEmpty()) {
            IPMFieldText(
                metadata = str(id = R.string.symptom_of_attack),
                value = disease.symptomsOfAttack ?: ""
            )
        }

        if (!disease.favourableConditions.isNullOrEmpty()) {
            IPMFieldText(
                metadata = str(id = R.string.favourable_conditions),
                value = disease.favourableConditions ?: ""
            )
        }

        if (!disease.preventiveMeasures.isNullOrEmpty()) {
            IPMFieldText(
                metadata = "${str(id = R.string.preventive_measures)}: ",
                value = disease.preventiveMeasures ?: ""
            )
        }

        if (!disease.culturalMechanicalControl.isNullOrEmpty()) {
            IPMFieldText(
                metadata = str(id = R.string.cultural_mechanical_control),
                value = disease.culturalMechanicalControl ?: ""
            )
        }
    }
}
