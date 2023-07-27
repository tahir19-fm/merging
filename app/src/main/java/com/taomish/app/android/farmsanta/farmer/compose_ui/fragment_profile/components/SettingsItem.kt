package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun SettingsItem(
    checked: MutableState<Boolean>,
    @StringRes strId: Int,
    @DrawableRes iconId: Int
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    var size by remember { mutableStateOf(Size.Zero) }
    val height: @Composable () -> Dp = { with(LocalDensity.current) { size.height.toDp() } }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacing.small)
            .background(color = Color.Cameron.copy(alpha = 0.1f), shape = shape)
            .onGloballyPositioned { size = it.size.toSize() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier.padding(start = spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(
                        start = spacing.small,
                        end = spacing.small.plus(spacing.extraSmall),
                        top = spacing.small.plus(spacing.extraSmall),
                        bottom = spacing.small.plus(spacing.extraSmall)
                    )
                    .size(24.dp)
            )

            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .height(height() - spacing.small)
                    .width(1.dp)
            )


            Text(
                text = stringResource(id = strId),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = spacing.small)
            )
        }

        Switch(
            checked = checked.value,
            onCheckedChange = { checked.postValue(it) },
            modifier = Modifier
                .padding(end = spacing.small)
                .height(spacing.medium),
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Cameron,
                checkedTrackColor = Color.LightGray,
                checkedTrackAlpha = 1f,
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = Color.Cameron,
                uncheckedTrackAlpha = 1f
            )
        )
    }
}