package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun ReplyQuestion(
    modifier: Modifier = Modifier,
    replies: Int,
    showText: Boolean = true,
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BadgedBox(
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onClick
            ),
            badge = {
                Badge(
                    modifier = Modifier.padding(top = spacing.small, end = spacing.small),
                    backgroundColor = Color.Red
                ) {
                    Text(
                        text = "$replies",
                        style = MaterialTheme.typography.overline,
                        color = Color.White
                    )
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chat_heart),
                contentDescription = null
            )
        }

        if (showText) {
            Text(
                text = str(id = R.string.reply_question),
                style = MaterialTheme.typography.overline,
                color = Color.Cameron,
                modifier = Modifier.padding(start = spacing.medium)
            )
        }
    }
}