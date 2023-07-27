package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.OffsetTime
import java.util.*


@SuppressLint("NewApi")
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
internal fun CalendarMonthYearSelector(
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    pagerDate: LocalDate,
    onChipClicked: () -> Unit
) {

    val pagerMonthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterChip(
            content = {
                Text(
                    pagerMonthFormat.format(
                        Date.from(pagerDate.atTime(OffsetTime.now()).toInstant())
                    )
                )
            },
            selected = false,
            border = null,
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, "ArrowDropDown")
            },
            onClick = onChipClicked,
        )
        Spacer(modifier = Modifier.weight(1F))
        IconButton(
            onClick = {
                coroutineScope.launch {
                    with(pagerState) {
                        try {
                            animateScrollToPage(currentPage - 1)
                        } catch (e: Exception) {
                            // avoid IOOB and animation crashes
                        }
                    }
                }
            }
        ) {
            Icon(
                Icons.Default.ChevronLeft, "ChevronLeft"
            )
        }
        IconButton(
            onClick = {
                coroutineScope.launch {
                    with(pagerState) {
                        try {
                            animateScrollToPage(currentPage + 1)
                        } catch (e: Exception) {
                            // avoid IOOB and animation crashes
                        }
                    }
                }
            }
        ) {
            Icon(
                Icons.Default.ChevronRight, "ChevronRight",
            )
        }
    }
}
