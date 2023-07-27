package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun ScoutDetailChatBottomSheetContent(
    text: MutableState<String>,
    textError: MutableState<Boolean>,
    focusManager: FocusManager,
    onSendMessageClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {

        LazyColumn {
            item {
                Text(
                    text = "Today",
                    style = MaterialTheme.typography.caption,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .padding(bottom = spacing.small)
                )

                ChatMessage(
                    imageUrl = "",
                    message = "Did you use any fertilizer on this crop ?",
                    dateTime = "12:00 AM"
                )

                ChatMessageReply(
                    imageUrl = "",
                    message = "Yes XYZ Fertilizer 4 weeks ago.",
                    dateTime = "01:01 PM"
                )

                ChatMessage(
                    imageUrl = "",
                    message = "How many time did you face this issue ?",
                    dateTime = "01:10 PM"
                )

                ChatMessageReply(
                    imageUrl = "",
                    message = "2 Weeks.",
                    dateTime = "01:30 PM"
                )
            }
        }

        SendMessageTextField(
            text = text,
            textError = textError,
            focusManager = focusManager,
            onSendMessageClicked = onSendMessageClicked
        )
    }
}