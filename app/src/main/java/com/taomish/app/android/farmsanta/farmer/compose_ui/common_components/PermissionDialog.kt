package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.annotation.StringRes
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.DialogProperties
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes


@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    @StringRes titleId: Int,
    @StringRes textId: Int,
    onConfirm: () -> Unit
) {
    val shape = LocalShapes.current.mediumShape

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = str(id = R.string.ok),
                    color = Color.Cameron,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.caption
                )
            }
        },
        dismissButton = {},
        title = {
            Text(
                text = str(id = titleId),
                color = Color.Cameron,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption
            )
        },
        text = {
            Text(
                text = str(id = textId),
                color = Color.Cameron,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption
            )
        },
        shape = shape
    )
}