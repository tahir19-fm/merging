package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FarmerPager(
    modifier: Modifier = Modifier,
    state: PagerState,
    tabs: () -> Int,
    userScrollEnabled: Boolean = true,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    onPageChange: @Composable (Int) -> Unit,
) {
    HorizontalPager(
        modifier = modifier,
        state = state,
        count = tabs(),
        userScrollEnabled = userScrollEnabled,
        verticalAlignment = verticalAlignment
    ) { page ->
        onPageChange(page)
    }
}