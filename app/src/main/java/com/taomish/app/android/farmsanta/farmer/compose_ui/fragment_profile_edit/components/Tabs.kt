package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.farmerTabIndicatorOffset
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(
    modifier: Modifier = Modifier,
    selected: MutableState<Int>,
    tabs: List<String>,
    state: PagerState,
    scope: CoroutineScope,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    selectedTabColor: Color = Color.Cameron,
    unselectedTabColor: Color = Color.LightGray
) {
    if (tabs.isEmpty()) return
    val spacing = LocalSpacing.current
    var size by remember { mutableStateOf(Size.Zero) }
    val width: @Composable () -> Dp = { with(LocalDensity.current) { size.width.toDp() } }
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = selected.value,
        backgroundColor = Color.Transparent,
        indicator = {
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .farmerTabIndicatorOffset(width(), it[selected.value]),
                color = selectedTabColor,
                height = 3.dp
            )
        },
        divider = { },
        edgePadding = spacing.zero
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
                        text = tab,
                        style = textStyle,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .onGloballyPositioned { size = it.size.toSize() }
                    )
                },
                selectedContentColor = selectedTabColor,
                unselectedContentColor = unselectedTabColor
            )
        }
    }
}