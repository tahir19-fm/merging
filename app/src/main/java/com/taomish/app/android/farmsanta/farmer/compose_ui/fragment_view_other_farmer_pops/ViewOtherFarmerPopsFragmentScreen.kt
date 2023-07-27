package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_other_farmer_pops

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.shimmerEffect
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.ViewOtherFarmerPopsViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_other_farmer_pops.components.OtherFarmerPopItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.R


@Composable
fun ViewOtherFarmerPopsFragmentScreen(
    otherFarmerPopsViewModel: ViewOtherFarmerPopsViewModel
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.small)
    ) {

        /*FarmerTextField(
            text = { otherFarmerPopsViewModel.searchText.value },
            onValueChange = { otherFarmerPopsViewModel.searchText.postValue(it) },
            modifier = Modifier
                .padding(vertical = spacing.small)
                .fillMaxWidth(),
            backgroundColor = Color.LightGray.copy(.4f),
            leadingIcon = { SearchLeadingIcon() },
            placeholderText = str(id = R.string.search),
            textStyle = MaterialTheme.typography.body2,
            shape = CircleShape,
            hasFocus = hasFocus
        )

        LazyRow(contentPadding = PaddingValues(spacing.small)) {
            items(otherFarmerPopsViewModel.searchTags) { tag ->
                SearchChip(
                    tag = tag,
                    isSelected = otherFarmerPopsViewModel.selected.value == tag,
                    onSelect = { otherFarmerPopsViewModel.selected.postValue(tag) }
                )
            }
        }*/

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            contentPadding = PaddingValues(spacing.small)
        ) {
            if (otherFarmerPopsViewModel.popsList.value != null) {
                items(otherFarmerPopsViewModel.popsList.value!!) { pop ->
                    OtherFarmerPopItem(popDto = pop)
                }
            } else {
                if (otherFarmerPopsViewModel.popsList.value?.isNotEmpty() == true) {
                    items(6) {
                        Spacer(
                            modifier = Modifier
                                .padding(spacing.extraSmall)
                                .height(200.dp)
                                .shimmerEffect()
                        )
                    }
                } else {
                    item {
                        Box(modifier = Modifier.fillMaxSize()) {
                            EmptyList(text = str(id = R.string.no_pops))
                        }
                    }
                }
            }
        }
    }
}