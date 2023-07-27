package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.IPMFieldRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.cultivar.Cultivar
import com.taomish.app.android.farmsanta.farmer.utils.getMonth


@Composable
fun CultivarItem(cultivar: Cultivar) {
    val shape = LocalShapes.current.smallShape
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .border(width = .3.dp, color = Color.LightGray, shape = shape)
    ) {
        Spacer(modifier = Modifier.height(spacing.small))

        IPMFieldRow(
            metaData = str(id = R.string.cultivar_name),
            value = cultivar.cultivarName ?: "-"
        )

        CultivarRowMultipleValue(
            metaData = str(id = R.string.cultivar_groups),
            list = cultivar.cultivarGroups,
            getItem = { it }
        )

        CultivarRowMultipleValue(
            metaData = str(id = R.string.sowing_month),
            list = cultivar.sowingMonths,
            getItem = { "${getMonth(it.min)} - ${getMonth(it.max)}" }
        )

//        IPMFieldRow(
//            metaData = str(id = R.string.harvest_month),
//            value = cultivar.harvestMonth?.let { "${it.min ?: ""}-${it.max ?: ""}" } ?: "-"
//        )

        IPMFieldRow(
            metaData = str(id = R.string.cultivar_duration),
            value = cultivar.cropDuration?.let { "${it.min ?: ""}-${it.max ?: ""}" } ?: "-"
        )

        IPMFieldRow(
            metaData = str(id = R.string.yield_potential),
            value = cultivar.yieldPotential?.let { "${it.min ?: ""}-${it.max ?: ""}" } ?: "-"
        )

        CultivarRowMultipleValue(
            metaData = str(id = R.string.properties),
            list = cultivar.additionalProperties,
            getItem = { it }
        )

        Spacer(modifier = Modifier.height(spacing.small))
    }
}