package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun FarmerButtonWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.caption,
    textColor: Color = Color.White,
    @DrawableRes iconId: Int,
    iconSize: Dp = 24.dp,
    iconTint: Color = Color.Unspecified,
    backgroundColor: Color = Color.Cameron,
    shape: CornerBasedShape = CircleShape,
    contentPadding: PaddingValues = PaddingValues(LocalSpacing.current.zero),
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        contentPadding = PaddingValues(spacing.tiny)
    ) {

        Row(
            modifier = Modifier.padding(contentPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(iconSize)
            )

            Text(
                text = text,
                style = textStyle,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(horizontal = spacing.extraSmall)
            )
        }
    }
}