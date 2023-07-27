package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_disease_details

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.IPMFieldText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SelectableChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpanStyle
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OrangePeel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.disease.Disease
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel


@Composable
fun DiseaseDetailsFragmentScreen(
    homeViewModel: HomeViewModel,
    onAskQuery: (Disease) -> Unit,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val context = LocalContext.current
    val style = LocalSpanStyle.current
    val disease = homeViewModel.selectedDisease
    Column(modifier = Modifier.fillMaxSize()) {
        CommonTopBar(
            activity = context as AppCompatActivity,
            isAddRequired = false,
            title = str(id = R.string.back),
            addClick = {}
        )
        Column(
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .padding(spacing.small)
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                RemoteImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape),
                    imageLink = (URLConstants.S3_IMAGE_BASE_URL
                            + (homeViewModel.selectedDisease?.photos?.find { it?.isSelected == true }?.fileName
                        ?: homeViewModel.selectedDisease?.photos?.firstOrNull()?.fileName ?: "")),
                    contentScale = ContentScale.FillBounds,
                    error = R.mipmap.img_default_pop
                )

                SelectableChip(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = spacing.small, start = spacing.small),
                    text = homeViewModel.selectedDiseaseCrop?.cropName
                        ?: homeViewModel.crops.find {
                            it.uuid == homeViewModel.selectedDisease?.crops?.firstOrNull()
                        }?.cropName ?: "",
                    isSelected = false,
                    unselectedBackgroundColor = Color.OrangePeel,
                    unselectedContentColor = Color.White
                )
            }


            Row(
                modifier = Modifier
                    .padding(vertical = spacing.small)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                val text = buildAnnotatedString {
                    withStyle(
                        style.body1.copy(
                            color = Color.Cameron,
                            fontWeight = FontWeight.Bold
                        )
                    ) { append("${disease?.localName ?: ""} ") }

                    withStyle(
                        style.body1.copy(
                            color = Color.Cameron,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append(disease?.scientificName.let { if (!it.isNullOrEmpty()) "($it)" else "" })
                    }
                }


                Text(
                    text = text,
                    modifier = Modifier.padding(spacing.small)
                )
            }

            Row(
                modifier = Modifier
                    .padding(vertical = spacing.small)
                    .fillMaxWidth()
                    .background(color = Color.RiceFlower.copy(alpha = .6f)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = str(id = R.string.are_you_facing_same_issue),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(
                        vertical = spacing.small,
                        horizontal = spacing.extraSmall
                    )
                )

                SelectableChip(
                    text = str(id = R.string.ask_your_query),
                    isSelected = true,
                    selectedBackgroundColor = Color.Cameron,
                    onClick = { homeViewModel.selectedDisease?.let(onAskQuery) }
                )
            }

            if (!disease?.symptomsOfAttack.isNullOrEmpty()) {
                IPMFieldText(
                    metadata = str(id = R.string.symptom_of_attack),
                    value = disease?.symptomsOfAttack ?: ""
                )

                Spacer(modifier = Modifier.height(spacing.medium))
            }

            if (!disease?.favourableConditions.isNullOrEmpty()) {
                IPMFieldText(
                    metadata = str(id = R.string.favourable_conditions),
                    value = disease?.favourableConditions ?: ""
                )

                Spacer(modifier = Modifier.height(spacing.medium))
            }

            if (!disease?.preventiveMeasures.isNullOrEmpty()) {
                IPMFieldText(
                    metadata = "${str(id = R.string.preventive_measures)}: ",
                    value = disease?.preventiveMeasures ?: ""
                )

                Spacer(modifier = Modifier.height(spacing.medium))
            }

            if (!disease?.culturalMechanicalControl.isNullOrEmpty()) {
                IPMFieldText(
                    metadata = str(id = R.string.cultural_mechanical_control),
                    value = disease?.culturalMechanicalControl ?: ""
                )

                Spacer(modifier = Modifier.height(spacing.medium))
            }
        }
    }

}