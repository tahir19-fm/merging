package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_select_language.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FlowRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.R


@Composable
fun <T : Any> MultipleSelectionDropDownContent(
    modifier: Modifier = Modifier,
    selectedItems: SnapshotStateList<T>,
    getName: (T) -> String?,
    getExpanded: () -> Boolean,
    onDismissRequest: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.caption,
    items: List<T?>,
    onSelectOption: (Int, T) -> Unit
) {
    val spacing = LocalSpacing.current
    DropdownMenu(
        expanded = getExpanded(),
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {

        FlowRow(modifier = Modifier.fillMaxWidth(), horizontalGap = spacing.extraSmall) {
            selectedItems.forEach {
                DropDownChip(text = getName(it) ?: "", onDelete = { selectedItems.remove(it) })
            }
        }

        Divider(
            thickness = .3.dp,
            color = Color.LightGray,
            modifier = Modifier.padding(vertical = spacing.small)
        )

        items.forEachIndexed { index, item ->
            item?.let {
                DropdownMenuItem(
                    onClick = {

                        onSelectOption(index, item)
                    },
                    contentPadding = PaddingValues(8.dp)
                ) {
                    Text(
                        text = getName(item) ?: "",
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = Color.Cameron,
                        fontWeight = FontWeight.Bold,
                        style = textStyle,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            RoundedShapeButton(
                text = str(id = R.string.done),
                textStyle = MaterialTheme.typography.overline,
                textPadding = spacing.zero,
                onClick = onDismissRequest
            )
        }
    }
}