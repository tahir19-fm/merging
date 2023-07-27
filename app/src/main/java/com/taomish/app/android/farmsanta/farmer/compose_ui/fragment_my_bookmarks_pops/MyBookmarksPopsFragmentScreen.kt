package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_bookmarks_pops

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_bookmarks_pops.components.BookmarkPopItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.R

@Composable
fun MyBookmarksPopsFragmentScreen(
    viewModel: POPViewModel,
    goToViewPop: (String) -> Unit
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxSize()
    ) {

        CommonTopBar(
            activity = context as AppCompatActivity,
            title = str(id = R.string.bookmarked_pops),
            isAddRequired = false,
            addClick = {}
        )

        if (viewModel.bookmarkedPops.value.isEmpty()) {
            Box {
                EmptyList(text = str(id = R.string.no_pops))
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp),
            contentPadding = PaddingValues(spacing.small)
        ) {
            itemsIndexed(viewModel.bookmarkedPops.value) { index, pop ->
                BookmarkPopItem(
                    popDto = pop,
                    onClickPop = {
                        viewModel.pop.postValue(pop)
                        viewModel.selectedPopIndex = index
                        goToViewPop(pop.uuid)
                    }
                )
            }
        }
    }
}