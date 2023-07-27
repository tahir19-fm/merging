package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_nutri_source

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SearchLeadingIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.NutriSourceViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_nutri_source.components.SavedNutriSourceItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun SavedNutriSourceFragmentScreen(
    nutriSourceViewModel: NutriSourceViewModel,
    goToNutriSourceDetails: () -> Unit
) {
    val spacing = LocalSpacing.current
    val hasFocus = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = .2f))
    ) {

        FarmerTextField(
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth(),
            text = { nutriSourceViewModel.searchText.value },
            onValueChange = { nutriSourceViewModel.searchText.postValue(it) },
            placeholderText = str(id = R.string.search),
            shape = CircleShape,
            leadingIcon = { SearchLeadingIcon() },
            backgroundColor = Color.White,
            hasFocus = hasFocus
        )

        LazyVerticalGrid(
            modifier = Modifier.padding(
                horizontal = spacing.extraSmall,
                vertical = spacing.medium
            ),
            columns = GridCells.Adaptive(160.dp)
        ) {
            items(10) {
                SavedNutriSourceItem(onNutriSourceClicked = goToNutriSourceDetails)
            }
        }
    }
}