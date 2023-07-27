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
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun <T : Any> MultipleSelectionDropDown(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes iconId: Int,
    getExpanded: () -> Boolean,
    setExpanded: (Boolean) -> Unit,
    selectedItems: SnapshotStateList<T>,
    options: List<T?>,
    getName: (T) -> String?,
    showExtras: Boolean = true,
    titleTextColor: Color = Color.Black,
    textStyle: TextStyle = MaterialTheme.typography.caption,
    titleFontWeight: FontWeight? = null,
    backgroundColor: Color = Color.White,
    backgroundScale: Dp = LocalSpacing.current.small,
    onSelectOption: ((Int, T) -> Unit)? = null
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
            ) { setExpanded(true) }
    ) {

        if (showExtras) {
            Text(
                text = title,
                color = titleTextColor,
                fontWeight = titleFontWeight,
                style = textStyle,
                modifier = Modifier
                    .padding(start = spacing.small)
            )
        }

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
                if (showExtras) {
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
                }

                Text(
                    text = selectedItems.joinToString { getName(it) ?: "" },
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

        MultipleSelectionDropDownContent(
            modifier = Modifier
                .padding(spacing.medium)
                .fillMaxWidth(.9f)
                .wrapContentHeight()
                .background(Color.White),
            getExpanded = getExpanded,
            onDismissRequest = { setExpanded(false) },
            selectedItems = selectedItems,
            items = options,
            getName = getName,
            onSelectOption = { index, option ->
                onSelectOption?.invoke(index, option)
            }
        )
    }
}