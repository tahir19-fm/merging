package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FlowRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun <T : Any> CultivarRowMultipleValue(metaData: String, list: List<T>?, getItem: (T) -> String?) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .padding(horizontal = spacing.small)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = metaData,
            color = Color.Cameron,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(.4f)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = ":",
                color = Color.Cameron,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )

            if (list.isNullOrEmpty()) {
                CultivarTag(text = "N/A")
            } else {
                FlowRow(modifier = Modifier.fillMaxWidth(), horizontalGap = spacing.medium) {
                    list.forEach {
                        CultivarTag(text = getItem(it) ?: "N/A")
                    }
                }
            }
        }
    }
}