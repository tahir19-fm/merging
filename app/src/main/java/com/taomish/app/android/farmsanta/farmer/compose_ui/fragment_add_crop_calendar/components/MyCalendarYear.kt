package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components

import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable


@Composable
internal fun CalendarYear(
    year: Int,
    isSelectedYear: Boolean,
    isCurrentYear: Boolean,
    setSelectedYear: (Int) -> Unit
) {

    if (isSelectedYear) {
        Button(onClick = {
            setSelectedYear(year)
        }) {
            Text("$year", maxLines = 1)
        }
    } else if (isCurrentYear) {
        OutlinedButton(onClick = {
            setSelectedYear(year)
        }) {
            Text("$year", maxLines = 1)
        }
    } else {
        TextButton(onClick = { setSelectedYear(year) }) {
            Text("$year", maxLines = 1)
        }
    }
}