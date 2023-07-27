package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.pop.SeedTreatment


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SeedTreatmentTabContent(seedTreatments: List<SeedTreatment?>, onZoomImage: (String) -> Unit) {
    val spacing = LocalSpacing.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.large)
    ) {
//        item {
//            ImageCarousel(getImages =  {
//                var images = emptyList<Photo>()
//                seedTreatments.forEach { seedTreatment ->
//                    seedTreatment?.photos?.let { images = images + it }
//                }
//                images
//            }, onZoomImage = onZoomImage)
//        }

        items(seedTreatments) { seedTreatment ->
            seedTreatment?.let {
                SeedTreatmentItem(seedTreatment = it)
            }
        }
    }

    if (seedTreatments.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyList(text = str(id = R.string.nothing_to_show))
        }
    }
}