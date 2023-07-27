package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_nutri_source.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.NutriSourceViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun Categories(viewModel: NutriSourceViewModel) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        viewModel.categories.forEachIndexed { index, category ->
            CategoryItem(
                text = category,
                selected = viewModel.selectedCategory.value == category,
                onClick = {
                    viewModel.selectedCategory.postValue(category)
                    viewModel.selectedProducts.postValue(viewModel.products[index])
                },
                onCloseClicked = { viewModel.selectedCategory.postValue(null) }
            )
        }
    }
}