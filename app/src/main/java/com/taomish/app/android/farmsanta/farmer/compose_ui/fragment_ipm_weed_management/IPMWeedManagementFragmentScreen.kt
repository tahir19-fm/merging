package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_ipm_weed_management

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.IPMNameTagsList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ImageCarousel
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_ipm_weed_management.components.IPMWeedDetails
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpanStyle
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun IPMWeedManagementFragmentScreen(viewModel: POPViewModel, onZoomImage: (String) -> Unit) {
    val spacing = LocalSpacing.current
    val style = LocalSpanStyle.current
    LazyColumn(
        modifier = Modifier.padding(bottom = spacing.large),
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        item {
            IPMNameTagsList(
                list = viewModel.popDetails.value?.weeds.orEmpty(),
                getName = { it.localName },
                selected = viewModel.selectedWeed.value,
                onSelect = { viewModel.selectedWeed.postValue(it) }
            )

            ImageCarousel(
                getImages = { viewModel.selectedWeed.value?.photos },
                onZoomImage = onZoomImage
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            val text = buildAnnotatedString {
                withStyle(
                    style.body1.copy(
                        color = Color.Cameron,
                        fontWeight = FontWeight.Bold
                    )
                ) { append("${viewModel.selectedWeed.value?.localName ?: ""} ") }

                withStyle(
                    style.body1.copy(
                        color = Color.Cameron,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                ) {
                    append(viewModel.selectedWeed.value?.scientificName.let { if (!it.isNullOrEmpty()) "($it)" else "" })
                }
            }

            Text(
                text = text,
                color = Color.Cameron,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(spacing.small)
            )

            viewModel.selectedWeed.value?.let { IPMWeedDetails(it) }
        }
    }
}