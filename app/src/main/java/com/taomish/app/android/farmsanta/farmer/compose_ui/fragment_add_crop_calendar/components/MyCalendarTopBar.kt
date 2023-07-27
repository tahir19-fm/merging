package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*
import com.taomish.app.android.farmsanta.farmer.utils.asString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalPagerApi::class)
@SuppressLint("NewApi")
@Composable
internal fun CalendarTopBar(
    selectedDate: LocalDate,
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
//    pagerDate: LocalDate,
    onChipClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
    ) {

        RemoteImage(
            modifier = Modifier.fillMaxSize(),
            imageLink = "",
            contentScale = ContentScale.FillBounds,
            error = R.drawable.ic_calendar_backgroud
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = str(id = R.string.select_crop_sowing_date),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = spacing.medium)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(spacing.small)
                    .background(Color.White, shape = shape),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

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
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "ChevronLeft",
                        tint = Color.Cameron
                    )
                }


                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Divider(
                        modifier = Modifier
                            .padding(spacing.small)
                            .height(16.dp)
                            .width(1.5.dp)
                    )

                    Text(
                        text = selectedDate.asString("dd"),
                        style = MaterialTheme.typography.caption,
                        color = Color.Cameron,
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource(),
                                onClick = onChipClicked
                            )
                    )

                    Divider(
                        modifier = Modifier
                            .padding(spacing.small)
                            .height(16.dp)
                            .width(1.5.dp)
                    )

                    Text(
                        text = selectedDate.asString("MM"),
                        style = MaterialTheme.typography.caption,
                        color = Color.Citron,
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource(),
                                onClick = onChipClicked
                            )
                    )

                    Divider(
                        modifier = Modifier
                            .padding(spacing.small)
                            .height(16.dp)
                            .width(1.dp)
                    )

                    Text(
                        text = selectedDate.asString("yyyy"),
                        style = MaterialTheme.typography.caption,
                        color = Color.Sunshade,
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource(),
                                onClick = onChipClicked
                            )
                    )

                    Divider(
                        modifier = Modifier
                            .padding(spacing.small)
                            .height(16.dp)
                            .width(1.5.dp)
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
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "ChevronRight",
                        tint = Color.Cameron
                    )
                }
            }
        }

//        val dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT)
//        Text(
//            text = dateFormatter.format(
//                Date.from(selectedDate.atTime(OffsetTime.now()).toInstant())
//            ),
//            style = MaterialTheme.typography.h6,
//            color = Color.Cameron
//        )
    }
}