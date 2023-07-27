package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun RoundedShapeButton(
    modifier: Modifier = Modifier,
    text : String,
    textPadding: Dp = LocalSpacing.current.small,
    enabled : Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.caption,
    backgroundColor: Color = Color.Cameron,
    onClick : () -> Unit
) {

    Button(
        onClick = { onClick() },
        modifier = modifier,
        enabled = enabled,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Text(
            text = text,
            style = textStyle,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(textPadding)
        )
    }
}


@Composable
fun RoundedShapeButton(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    textPadding: Dp = LocalSpacing.current.small,
    enabled: Boolean = true,
    backgroundColor: Color = Color.Cameron,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        onClick = { onClick() },
        enabled = enabled,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(textPadding)
        )
    }
}