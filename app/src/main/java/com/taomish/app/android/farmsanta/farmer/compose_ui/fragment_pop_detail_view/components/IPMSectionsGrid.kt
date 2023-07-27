package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun <T : Any> IPMSectionsGrid(
    list: List<T?>,
    getName: (T) -> AnnotatedString,
    getImageUrl: (T) -> String,
    onSectionItemClicked: (Int) -> Unit
) {
    val spacing = LocalSpacing.current
    LazyVerticalGrid(
        modifier = Modifier.padding(bottom = spacing.large),
        columns = GridCells.Adaptive(180.dp)
    ) {
        itemsIndexed(list) { index, item ->
            item?.let {
                IPMSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.extraSmall),
                    title = getName(it),
                    imageLink = getImageUrl(it),
                    onClick = { onSectionItemClicked(index) },
                )
            }
        }
    }

    if (list.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyList(text = str(id = R.string.nothing_to_show))
        }
    }
}