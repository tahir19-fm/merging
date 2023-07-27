package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_advisories.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun CustomDropDown(
    selected: MutableState<Pair<Int, String>>,
    items: List<Pair<Int, String>>
) {
    val expanded = remember { mutableStateOf(false) }
    val spacing = LocalSpacing.current

    Column(modifier = Modifier.padding(spacing.small).background(Color.White)) {

        Row(
            modifier = Modifier
                .padding(spacing.small)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    onClick = { expanded.postValue(true) }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = selected.value.first),
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = selected.value.second,
                color = Color.Cameron,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = spacing.extraSmall)
            )

            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier
                    .padding(start = spacing.extraSmall)
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.postValue(false) },
            modifier = Modifier
                .wrapContentSize()
                .background(Color.White)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        expanded.postValue(false)
                        selected.postValue(item)
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .border(
                                width = .6.dp,
                                color = if (selected.value.second == item.second) Color.Transparent else Color.Cameron,
                                shape = CircleShape
                            )
                            .background(
                                color = if (selected.value.second == item.second) Color.Cameron else Color.White,
                                shape = CircleShape
                            )
                            .padding(spacing.small)
                    ) {
                        Icon(
                            painter = painterResource(id = item.first),
                            contentDescription = null,
                            tint = if (selected.value.second == item.second) Color.White else Color.Cameron,
                            modifier = Modifier
                                .padding(start = spacing.extraSmall)
                                .size(16.dp)
                        )

                        Text(
                            text = item.second,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = spacing.small),
                            color = if (selected.value.second == item.second) Color.White else Color.Cameron,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
        }
    }
}