package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.OffsetTime
import java.time.format.TextStyle
import java.util.*
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.floor

@SuppressLint("NewApi")
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
internal fun CalendarContent(
    startDate: LocalDate,
    minDate: LocalDate,
    maxDate: LocalDate,
    onSelected: (LocalDate) -> Unit,
) {

    val spacing = LocalSpacing.current
    val dateRange = getDateRange(minDate, maxDate)
    val totalPageCount = dateRange.count()
    val initialPage = getStartPage(startDate, dateRange, totalPageCount)

    val isPickingYear = remember { mutableStateOf(false) }
    val currentPagerDate = remember { mutableStateOf(startDate.withDayOfMonth(1)) }
    val selectedDate = remember { mutableStateOf(startDate) }

    val pagerState = rememberPagerState(initialPage)
    val coroutineScope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    val pagerMonthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    val setSelectedDate: (LocalDate) -> Unit = {
        onSelected(it)
        selectedDate.value = it
    }

    if (!LocalInspectionMode.current) {
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                val pageDiff = page.minus(initialPage).absoluteValue.toLong()

                val date = if (page > initialPage) {
                    startDate.plusMonths(pageDiff)
                } else if (page < initialPage) {
                    startDate.minusMonths(pageDiff)
                } else {
                    startDate
                }

                currentPagerDate.value = date
            }
        }
    }

    Column(
        modifier = Modifier
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CalendarTopBar(
            selectedDate.value,
            coroutineScope,
            pagerState,
//            currentPagerDate.value
        ) {
            isPickingYear.value = !isPickingYear.value
        }

        FilterChip(
            modifier = Modifier.padding(start = spacing.small),
            content = {
                Text(
                    pagerMonthFormat.format(
                        Date.from(currentPagerDate.value.atTime(OffsetTime.now()).toInstant())
                    )
                )
            },
            selected = false,
            border = null,
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, "ArrowDropDown")
            },
            onClick = { isPickingYear.value = !isPickingYear.value },
            colors = ChipDefaults.filterChipColors(backgroundColor = Color.RiceFlower)
        )

//        CalendarMonthYearSelector(
//            coroutineScope,
//            pagerState,
//            currentPagerDate.value
//        ) {
//            isPickingYear.value = !isPickingYear.value
//        }

        if (!isPickingYear.value) {

            Row(
                modifier = Modifier.padding(spacing.extraSmall),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DayOfWeek.values().forEach {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = it.getDisplayName(
                            TextStyle.NARROW,
                            Locale.getDefault()
                        ),
                        color = Color.Cameron,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalPager(
                modifier = Modifier.padding(spacing.extraSmall),
                count = totalPageCount,
                state = pagerState
            ) { page ->
                val pageDiff = page.minus(initialPage).absoluteValue.toLong()

                val date = if (page > initialPage) {
                    startDate.plusMonths(pageDiff)
                } else if (page < initialPage) {
                    startDate.minusMonths(pageDiff)
                } else {
                    startDate
                }

                // grid
                CalendarGrid(
                    date.withDayOfMonth(1),
                    dateRange,
                    selectedDate.value,
                    setSelectedDate,
                    true
                )
            }

        } else {

            LazyVerticalGrid(
                modifier = Modifier.padding(spacing.extraSmall),
                columns = GridCells.Fixed(3),
                state = gridState,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 4.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                dateRange.step(DateRangeStep.Year(1)).forEach {
                    item {
                        CalendarYear(
                            year = it.year,
                            isSelectedYear = it.year == selectedDate.value.year,
                            isCurrentYear = it.year == startDate.year,
                            setSelectedYear = { year ->
                                coroutineScope.launch {
                                    val newPage = dateRange.indexOfFirst {
                                        it.year == year && it.month == selectedDate.value.month
                                    }
                                    pagerState.scrollToPage(newPage)
                                }
                                selectedDate.value = selectedDate.value.withYear(year)
                                currentPagerDate.value = currentPagerDate.value.withYear(year)
                                isPickingYear.value = false
                            }
                        )
                    }
                }
            }

        }
    }
}

@SuppressLint("NewApi")
private fun getStartPage(
    startDate: LocalDate,
    dateRange: DateRange,
    pageCount: Int
): Int {
    if (startDate <= dateRange.start) {
        return 0
    }
    if (startDate >= dateRange.endInclusive) {
        return pageCount
    }
    val indexOfRange = dateRange.indexOf(startDate.withDayOfMonth(1))
    return if (indexOfRange != -1) indexOfRange else pageCount / 2
}

@SuppressLint("NewApi")
private fun getDateRange(min: LocalDate, max: LocalDate): DateRange {
    val lowerBound = with(min) {
        val year = with(LocalDate.now().minusYears(100).year) {
            100.0 * (floor(abs(this / 100.0)))
        }
        coerceAtLeast(
            LocalDate.now().withYear(year.toInt()).withDayOfYear(1)
        )
    }
    val upperBound = with(max) {
        val year = with(LocalDate.now().year) {
            100.0 * (ceil(abs(this / 100.0)))
        }
        coerceAtMost(LocalDate.now().withYear(year.toInt())).apply {
            withDayOfYear(this.lengthOfYear())
        }
    }
    return lowerBound.rangeTo(upperBound) step DateRangeStep.Month()
}