package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SelectableChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components.IPMType.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpanStyle
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun IPMTabContent(viewModel: POPViewModel, onSectionItemClicked: (IPMType) -> Unit) {

    val style = LocalSpanStyle.current
    val popDetailsDTO = viewModel.popDetails.value

    val spacing = LocalSpacing.current
    var selected by remember { mutableStateOf(InsectManagement) }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(spacing.small)
                .horizontalScroll(rememberScrollState())
        ) {
            SelectableChip(
                text = stringResource(id = R.string.ipm_insect_management),
                isSelected = selected == InsectManagement
            ) { selected = InsectManagement }

            SelectableChip(
                text = stringResource(id = R.string.ipm_disease_management),
                isSelected = selected == DiseaseManagement
            ) { selected = DiseaseManagement }

            SelectableChip(
                text = stringResource(id = R.string.ipm_Weed_management),
                isSelected = selected == WeedManagement
            ) { selected = WeedManagement }
        }

        when (selected) {
            InsectManagement -> {
                IPMSectionsGrid(
                    list = popDetailsDTO?.insects.orEmpty(),
                    getName = {
                        getName(
                            style = style.caption,
                            localName = it.localName,
                            scientificName = it.scientificName
                        )
                    },
                    getImageUrl = {
                        URLConstants.S3_IMAGE_BASE_URL +
                                it.photos?.firstNotNullOfOrNull { photo -> photo }?.fileName.toString()
                    },
                    onSectionItemClicked = { index ->
                        viewModel.selectedInsect.postValue(popDetailsDTO?.insects?.get(index))
                        viewModel.selectedIPMItemIndex = index
                        onSectionItemClicked(InsectManagement)
                    }
                )
            }
            DiseaseManagement -> {
                IPMSectionsGrid(
                    list = popDetailsDTO?.diseases.orEmpty(),
                    getName = {
                        getName(
                            style = style.caption,
                            localName = it.localName,
                            scientificName = it.scientificName
                        )
                    },
                    getImageUrl = {
                        URLConstants.S3_IMAGE_BASE_URL +
                                it.photos?.firstNotNullOfOrNull { photo -> photo }
                                    ?.fileName.toString()
                    },
                    onSectionItemClicked = { index ->
                        viewModel.selectedDisease.postValue(popDetailsDTO?.diseases?.get(index))
                        viewModel.selectedIPMItemIndex = index
                        onSectionItemClicked(DiseaseManagement)
                    }
                )
            }
            WeedManagement -> {
                IPMSectionsGrid(
                    list = popDetailsDTO?.weeds.orEmpty(),
                    getName = {
                        getName(
                            style = style.caption,
                            localName = it.localName,
                            scientificName = it.scientificName
                        )
                    },
                    getImageUrl = {
                        URLConstants.S3_IMAGE_BASE_URL +
                                it.photos?.firstNotNullOfOrNull { photo -> photo }
                                    ?.fileName.toString()
                    },
                    onSectionItemClicked = { index ->
                        viewModel.selectedWeed.postValue(popDetailsDTO?.weeds?.get(index))
                        viewModel.selectedIPMItemIndex = index
                        onSectionItemClicked(WeedManagement)
                    }
                )
            }
        }
    }
}


fun getName(style: SpanStyle, localName: String?, scientificName: String?): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            style.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        ) { append("${localName ?: ""} ") }
        withStyle(
            style.copy(
                color = Color.White,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        ) { append(scientificName.let { if (!it.isNullOrEmpty()) "($it)" else "" }) }
    }
}