package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerDropDownContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun RoundedDropDownMenu(
    modifier: Modifier = Modifier,
    title: String,
    expanded: MutableState<Boolean>,
    selected: MutableState<String>,
    options: List<String>,
    backgroundColor: Color = Color.White,
    borderColor: Color
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) { expanded.postValue(true) }
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(start = spacing.small)
        )

        Row(
            modifier = Modifier
                .padding(top = spacing.small)
                .fillMaxWidth()
                .border(width = .5.dp, color = borderColor, shape = CircleShape)
                .background(color = backgroundColor, shape = CircleShape)
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = selected.value,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = spacing.small)
                    .fillMaxWidth(.9f)
            )

            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier.fillMaxWidth()
            )
        }

        FarmerDropDownContent(
            modifier = Modifier.wrapContentWidth(),
            expanded = expanded,
            selected = selected,
            items = options,
            onSelectOption = {_, _ -> }
        )
    }
}