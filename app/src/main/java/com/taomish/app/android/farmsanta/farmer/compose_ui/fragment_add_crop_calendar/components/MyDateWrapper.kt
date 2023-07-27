package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components

import java.time.LocalDate


internal data class DateWrapper(
    val localDate: LocalDate,
    val isSelectedDay: Boolean,
    val isCurrentDay: Boolean,
    val isCurrentMonth: Boolean,
    val isInDateRange: Boolean,
    val showCurrentMonthOnly: Boolean
)