package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun FarmerDropDownContent(
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean>,
    selected: MutableState<String>,
    textStyle: TextStyle = MaterialTheme.typography.caption,
    items: List<String>,
    onSelectOption: (Int, String) -> Unit,
    selectedItemColor: Color = Color.Cameron,
    unselectedItemColor: Color = Color.Gray
) {
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.postValue(false) },
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            DropdownMenuItem(
                onClick = {
                    expanded.postValue(false)
                    selected.value = item
                    onSelectOption(index, item)
                },
                contentPadding = PaddingValues(8.dp)
            ) {
                Text(
                    text = item,
                    modifier = Modifier.fillMaxWidth(),
                    color = if (selected.value == item) selectedItemColor else unselectedItemColor,
                    fontWeight = FontWeight.Bold,
                    style = textStyle,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}