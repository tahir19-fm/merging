package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerScrollableTabRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddCropsTabs(
    modifier: Modifier = Modifier,
    selected: () -> Int,
    setSelected: (Int) -> Unit,
    tabs: List<String>,
    state: PagerState,
    scope: CoroutineScope,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    selectedTabColor: Color = Color.Cameron,
    unselectedTabColor: Color = Color.LightGray,
) {
    val spacing = LocalSpacing.current
    FarmerScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = selected(),
        backgroundColor = Color.Transparent,
        indicator = { },
        divider = { },
        edgePadding = spacing.zero
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = selected() == index,
                onClick = {
                    setSelected(index)
                    scope.launch { state.scrollToPage(index) }
                },
                text = {
                    Text(
                        text = tab,
                        style = textStyle,
                        color = if (selected() == index) Color.White else Color.Cameron,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .border(width = .5.dp, color = Color.Cameron, shape = CircleShape)
                            .background(color = if (selected() == index) Color.Cameron else Color.White,
                                shape = CircleShape)
                            .padding(horizontal = spacing.medium, vertical = spacing.small)
                    )
                },
                selectedContentColor = selectedTabColor,
                unselectedContentColor = unselectedTabColor
            )
        }
    }
}