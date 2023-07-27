package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CircleIconWithText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun IconsRow(
    @DrawableRes firstIconId: Int,
    @StringRes firstTextId: Int,
    firstIconColor: Color,
    firstIconOnClick: () -> Unit,
    @DrawableRes secondIconId: Int,
    @StringRes secondTextId: Int,
    secondIconColor: Color,
    secondIconOnClick: () -> Unit,
    @DrawableRes thirdIconId: Int,
    @StringRes thirdTextId: Int,
    thirdIconColor: Color,
    thirdIconOnClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        /*val firstText = str(id = firstTextId).getIconTitle()
        val secondText = str(id = secondTextId).getIconTitle()
        val thirdText = str(id = thirdTextId).getIconTitle()*/

        CircleIconWithText(
            containerModifier = Modifier.fillMaxWidth(1F / 3F),
            iconId = firstIconId,
            text = str(id = firstTextId),
            textPadding = spacing.zero,
            textStyle = MaterialTheme.typography.caption,
            tint = firstIconColor,
            onClick = firstIconOnClick,
            borderColor = null
        )

        CircleIconWithText(
            containerModifier = Modifier.fillMaxWidth(1F / 2F),
            iconId = secondIconId,
            text = str(id = secondTextId),
            textPadding = spacing.zero,
            textStyle = MaterialTheme.typography.caption,
            tint = secondIconColor,
            onClick = secondIconOnClick,
            borderColor = null
        )

        CircleIconWithText(
            containerModifier = Modifier.fillMaxWidth(),
            iconId = thirdIconId,
            text = str(id = thirdTextId),
            textPadding = spacing.zero,
            textStyle = MaterialTheme.typography.caption,
            tint = thirdIconColor,
            textColor = Color.Cameron,
            onClick = thirdIconOnClick,
            borderColor = null
        )
    }
}