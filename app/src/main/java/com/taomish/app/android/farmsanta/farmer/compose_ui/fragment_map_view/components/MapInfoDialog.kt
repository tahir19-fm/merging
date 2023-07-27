package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SVG
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpanStyle


@Composable
fun MapInfoDialog(onDismiss: () -> Unit) {
    val spacing = LocalSpacing.current
    val style = LocalSpanStyle.current
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(spacing.small)
                    .weight(1F)
                    .background(Color.White),
            ) {
                Text(
                    text = str(id = R.string.mark_your_farm_area),
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(spacing.small)
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style.body2.copy(color = Color.Gray)) {
                            append(str(id = R.string.map_dialog_description_1))
                        }

                        withStyle(style.body2.copy(color = Color.Cameron)) {
                            append(str(id = R.string.big_circle))
                        }

                        withStyle(style.body2.copy(color = Color.Gray)) {
                            append(str(id = R.string.map_dialog_description_2))
                        }
                    },
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(spacing.small)
                )

                Text(
                    text = str(id = R.string.got_it),
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        color = Color.Cameron
                    ),
                    modifier = Modifier
                        .padding(spacing.small)
                        .clickable(
                            indication = null,
                            interactionSource = MutableInteractionSource()
                        ) { onDismiss() }
                )
            }

            SVG(
                modifier = Modifier.wrapContentSize(),
                fileName = R.raw.mapping
            )
        }
    }
}