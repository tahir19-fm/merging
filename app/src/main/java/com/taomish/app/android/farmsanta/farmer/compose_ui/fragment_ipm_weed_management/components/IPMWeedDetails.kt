package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_ipm_weed_management.components

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
import com.taomish.app.android.farmsanta.farmer.models.api.pop.Weed


@Composable
fun IPMWeedDetails(weed: Weed) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        Spacer(modifier = Modifier.height(spacing.small))

        if (!weed.weedType.isNullOrEmpty()) {
            IPMFieldText(
                metadata = str(id = R.string.weed_type),
                value = weed.weedType ?: ""
            )
        }

        if (!weed.description.isNullOrEmpty()) {
            IPMFieldText(
                metadata = str(id = R.string.description),
                value = weed.description ?: ""
            )
        }

        if (!weed.favourableConditions.isNullOrEmpty()) {
            IPMFieldText(
                metadata = str(id = R.string.favourable_conditions),
                value = weed.favourableConditions ?: ""
            )
        }

        if (!weed.culturalControl.isNullOrEmpty()) {
            IPMFieldText(
                metadata = str(id = R.string.cultural_control),
                value = weed.culturalControl ?: ""
            )
        }
    }
}