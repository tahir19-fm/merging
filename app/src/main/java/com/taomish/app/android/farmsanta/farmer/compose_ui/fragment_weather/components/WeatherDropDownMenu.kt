package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather.components

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerDropDownContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Sunshade
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun WeatherDropDownMenu(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    tint: Color = Color.Sunshade,
    expanded: MutableState<Boolean>,
    selected: MutableState<String>,
    options: List<String>,
    textStyle: TextStyle = MaterialTheme.typography.caption,
    onSelectOption: ((String) -> Unit)? = null
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) { expanded.postValue(true) }
    ) {

        Row(
            modifier = modifier
                .padding(spacing.extraSmall)
                .border(width = .5.dp, color = tint, shape = CircleShape)
                .padding(spacing.extraSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .background(color = tint, shape = CircleShape)
                        .padding(spacing.tiny)
                        .size(16.dp)
                )

                Text(
                    text = selected.value,
                    style = textStyle,
                    color = Color.LightGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = spacing.small)
                        .fillMaxWidth(.9f)
                )
            }

            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                tint = tint,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(16.dp)
            )
        }

        FarmerDropDownContent(
            modifier = Modifier.wrapContentWidth(),
            expanded = expanded,
            selected = selected,
            items = options,
            onSelectOption = { _, option ->
                selected.postValue(option)
                onSelectOption?.invoke(option)
            }
        )
    }
}