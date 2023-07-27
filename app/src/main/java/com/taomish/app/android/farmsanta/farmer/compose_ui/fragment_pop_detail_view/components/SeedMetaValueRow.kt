package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpanStyle


@Composable
fun SeedMetaValueRow(metaData: String, value: String) {
    val spacing = LocalSpacing.current
    val style = LocalSpanStyle.current
    Row(
        modifier = Modifier
            .padding(horizontal = spacing.small)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = metaData,
            color = Color.Cameron,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.fillMaxWidth(.45f)
        )


        Text(
            text = buildAnnotatedString {
                withStyle(style.body2.copy(color = Color.Cameron)) { append(":") }
                withStyle(style.body2.copy(color = Color.Gray, fontWeight = FontWeight.Bold)) {
                    append(value)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}