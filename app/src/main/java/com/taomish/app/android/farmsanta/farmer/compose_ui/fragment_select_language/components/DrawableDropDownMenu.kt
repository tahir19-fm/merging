package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_select_language.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerDropDownContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun DrawableDropDownMenu(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes iconId: Int,
    expanded: MutableState<Boolean>,
    selected: MutableState<String>,
    options: List<String>,
    titleTextColor: Color = Color.Black,
    textStyle: TextStyle = MaterialTheme.typography.caption,
    titleFontWeight: FontWeight? = null,
    backgroundColor: Color = Color.White,
    backgroundScale: Dp = LocalSpacing.current.medium,
    onSelectOption: ((Int, String) -> Unit)? = null
) {
    val spacing = LocalSpacing.current
    val shape = MaterialTheme.shapes.small
    var size by remember { mutableStateOf(Size.Zero) }
    val height: @Composable () -> Dp = { with(LocalDensity.current) { size.height.toDp() } }
    Column(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) { expanded.postValue(true) }
    ) {

        Text(
            text = title,
            color = titleTextColor,
            fontWeight = titleFontWeight,
            style = textStyle,
            modifier = Modifier
                .padding(start = spacing.small)
        )

        Row(
            modifier = Modifier
                .padding(top = spacing.small)
                .fillMaxWidth()
                .background(color = backgroundColor, shape = shape)
                .padding(backgroundScale)
                .onGloballyPositioned { size = it.size.toSize() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(horizontal = spacing.small)
                        .size(24.dp)
                )

                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .height(height())
                        .width(1.dp)
                )

                Text(
                    text = selected.value,
                    style = textStyle,
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
                tint = Color.Cameron,
                modifier = Modifier.fillMaxWidth()
            )
        }

        FarmerDropDownContent(
            modifier = Modifier
                .padding(spacing.medium)
                .fillMaxWidth(.9f)
                .wrapContentHeight(),
            expanded = expanded,
            selected = selected,
            items = options,
            onSelectOption = { index, option ->
                selected.postValue(option)
                onSelectOption?.invoke(index, option)
            }
        )
    }
}