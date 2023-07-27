package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.R


@Composable
fun Query(
    caption: String,
    isSolved: Boolean,
    replies: Int,
    onClickMessages: () -> Unit
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .background(color = Color.LightGray.copy(alpha = .2f), shape = shape)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = str(id = R.string.query),
                style = MaterialTheme.typography.caption,
                color = Color.Cameron,
                modifier = Modifier.padding(horizontal = spacing.small, vertical = spacing.extraSmall)
            )

            if (isSolved) {
                ReplyQuestion(
                    modifier = Modifier
                        .padding(top = spacing.medium, end = spacing.large),
                    replies = replies,
                    showText = false,
                    onClick = onClickMessages
                )
            }
        }

        Text(
            text = caption.ifEmpty { str(id = R.string.description_not_available) },
            style = MaterialTheme.typography.caption,
            color = Color.Gray,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = spacing.small, vertical = spacing.extraSmall)
        )
    }
}