package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.pop.CroppingProcessDto


@Composable
fun CroppingProcessTabContent(
    cropName: String,
    croppingProcessDto: CroppingProcessDto?,
    states: List<MutableState<Boolean>>,
) {
    val spacing = LocalSpacing.current
    LazyColumn(modifier = Modifier.padding(bottom = spacing.medium)) {
        item {
            Text(
                text = cropName,
                modifier = Modifier.padding(spacing.small)
            )

            Text(
                text = croppingProcessDto?.description ?: "",
                modifier = Modifier.padding(spacing.small),
                textAlign = TextAlign.Justify
            )
        }

        itemsIndexed(croppingProcessDto?.processlist?.toList().orEmpty()) { index, process ->
            ExpandableRow(
                expanded = states[index],
                title = process.title,
                description = process.description
            )
        }
    }

    if (croppingProcessDto?.processlist.isNullOrEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyList(text = str(id = R.string.nothing_to_show))
        }
    }
}