package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun FarmerDropDownMenu(
    modifier: Modifier = Modifier,
    title: String? = null,
    expanded: MutableState<Boolean>,
    selected: MutableState<String>,
    selectedTextColor: Color = Color.Black,
    options: List<String>,
    titleTextColor: Color = Color.Black,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    titleFontWeight: FontWeight? = null,
    showAsterisk: Boolean = false,
    showTitle: Boolean = true,
    backgroundColor: Color = Color.White,
    arrowColor: Color = Color.Cameron,
    backgroundScale: Dp = LocalSpacing.current.medium,
    selectedItemColor: Color = Color.Cameron,
    unselectedItemColor: Color = Color.Gray,
    onSelectOption: ((Int, String) -> Unit)? = null
) {
    val spacing = LocalSpacing.current
    val shape = MaterialTheme.shapes.small
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) { expanded.postValue(true) },
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {

        if (showTitle) {
            Row {
                Text(
                    text = title ?: "",
                    color = titleTextColor,
                    fontWeight = titleFontWeight,
                    style = textStyle
                )

                if (showAsterisk) {
                    Text(
                        text = stringResource(id = R.string.mandatory),
                        color = Color.Red,
                        fontWeight = titleFontWeight,
                        style = textStyle
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor, shape = shape)
                .padding(backgroundScale),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selected.value.ifEmpty { title ?: "" },
                style = textStyle,
                color = selectedTextColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth(.9f)
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_dropdown_arrow),
                contentDescription = null,
                tint = arrowColor,
                modifier = Modifier
                    .size(16.dp)
            )
        }

        FarmerDropDownContent(
            modifier = Modifier
                .padding(spacing.medium)
                .fillMaxWidth(.9f),
            expanded = expanded,
            selected = selected,
            items = options,
            selectedItemColor = selectedItemColor,
            unselectedItemColor = unselectedItemColor,
            onSelectOption = { index, option ->
                focusManager.clearFocus()
                selected.postValue(option)
                onSelectOption?.invoke(index, option)
            }
        )
    }
}