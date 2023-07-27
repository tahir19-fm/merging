package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SocialWallViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_activities.components.CommentsTab
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_activities.components.LikesTab
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components.FarmerPager
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components.Tabs
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OutrageousOrange
import com.taomish.app.android.farmsanta.farmer.utils.postValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyActivitiesFragmentScreen(viewModel: SocialWallViewModel) {
    val spacing = LocalSpacing.current
    val tabs = listOf(str(id = R.string.liked), str(id = R.string.commented))
    val selected = remember { mutableStateOf(0) }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    selected.postValue(pagerState.currentPage)


    Column(modifier = Modifier.fillMaxWidth()) {
        Tabs(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .padding(spacing.small),
            selected = selected,
            tabs = tabs,
            state = pagerState,
            scope = scope,
            selectedTabColor = Color.OutrageousOrange,
            unselectedTabColor = Color.Black
        )


        FarmerPager(state = pagerState, tabs = { tabs.size }) { page ->
            when (page) {
                0 -> LikesTab(viewModel.mySocialMessages)
                1 -> CommentsTab(viewModel.mySocialMessages)
            }
        }
    }

}