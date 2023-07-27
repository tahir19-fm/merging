package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import java.time.LocalDate


@SuppressLint("NewApi")
@Composable
fun MyComposeCalendar(
    startDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = LocalDate.MIN,
    maxDate: LocalDate = LocalDate.MAX,
    onDone: (millis: LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val selectedDate = remember { mutableStateOf(startDate) }
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = shape),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CalendarContent(
                    startDate = startDate,
                    minDate = minDate,
                    maxDate = maxDate,
                    onSelected = {
                        selectedDate.value = it
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .padding(spacing.small),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(id = R.string.cancel))
                    }

                    TextButton(onClick = {
                        onDone(selectedDate.value)
                    }) {
                        Text(stringResource(id = R.string.ok))
                    }
                }
            }
        }
    )
}