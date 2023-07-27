package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.FertilizerSourceDetails


@Composable
fun FertilizerSourceSelectionField(
    @StringRes tagStrId: Int,
    dropDownExpanded: MutableState<Boolean>,
    selected: MutableState<FertilizerSourceDetails>,
    options: List<FertilizerSourceDetails>
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier.fillMaxWidth(.95f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FertilizerTag(modifier = Modifier.fillMaxWidth(.35f), text = str(id = tagStrId))
        FertilizerDropDownMenu(
            modifier = Modifier
                .padding(start = spacing.extraSmall)
                .fillMaxWidth(),
            expanded = dropDownExpanded,
            selected = selected,
            options = options
        )
    }
}