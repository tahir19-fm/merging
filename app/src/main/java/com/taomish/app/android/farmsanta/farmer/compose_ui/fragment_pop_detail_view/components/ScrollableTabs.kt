package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScrollableTabs(
    modifier: Modifier = Modifier,
    selected: MutableState<Int>,
    tabs: List<Pair<String, Int>>,
    state: PagerState,
    scope: CoroutineScope,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    selectedTabColor: Color = Color.Cameron,
    unselectedTabColor: Color = Color.LightGray,
) {
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = selected.value,
        backgroundColor = Color.Transparent,
        indicator = {
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .tabIndicatorOffset(it[selected.value]),
                color = selectedTabColor,
                height = 3.dp
            )
        },
        divider = { }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = selected.value == index,
                onClick = {
                    selected.postValue(index)
                    scope.launch { state.scrollToPage(index) }
                },
                text = {
                    Text(
                        text = tab.first,
                        style = textStyle,
                        fontWeight = FontWeight.Bold
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = tab.second),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                },
                selectedContentColor = selectedTabColor,
                unselectedContentColor = unselectedTabColor
            )
        }
    }
}