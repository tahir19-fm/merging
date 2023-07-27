package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpanStyle


@Composable
fun IPMFieldText(metadata: String, value: String) {
    val style = LocalSpanStyle.current
    Text(
        text = buildAnnotatedString {
            withStyle(style.body2.copy(color = Color.Cameron, fontWeight = FontWeight.Bold)) {
                append("$metadata ")
            }
            withStyle(style.body2.copy(color = Color.Gray)) {
                append(value)
            }
        },
        textAlign = TextAlign.Justify
    )
}