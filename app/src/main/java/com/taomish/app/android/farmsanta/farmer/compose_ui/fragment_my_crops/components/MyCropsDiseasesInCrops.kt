package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_crops.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components.DiseaseItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel


@Composable
fun MyCropsDiseasesInCrops(
    homeViewModel: HomeViewModel,
    selectedCrop: MutableState<CropMaster?>,
    onViewMoreClicked: () -> Unit,
    onDiseaseClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    val diseases =
        homeViewModel.diseases.filter { it.crops?.contains(selectedCrop.value?.uuid) == true }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.diseases_and_insects_in_crops),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier
                    .size(16.dp)
                    .clickable(onClick = onViewMoreClicked)
            )
        }
        if (diseases.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .padding(spacing.small)
                    .fillMaxWidth()
            ) {
                items(diseases) { disease ->
                    DiseaseItem(
                        modifier = Modifier.size(180.dp, 200.dp),
                        pair = Pair(
                            disease.localName ?: "",
                            disease.photos?.firstOrNull()?.fileName ?: ""
                        ),
                        onDiseaseClicked = {
                            onDiseaseClicked.invoke()
                            homeViewModel.selectedDisease = disease
                        }
                    )
                }
            }
        } else {
            Text(
                text = str(id = R.string.no_diseases_for_crop),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.medium)
            )
        }
    }
}