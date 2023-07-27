package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CropChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.disease.Disease
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun DiseasesInCropsSection(
    selectedCrop:String?=null,
    homeViewModel: HomeViewModel,
    onNavigationItemClicked: (Screen) -> Unit,
    onDiseaseClicked: (Disease) -> Unit
) {
    val spacing = LocalSpacing.current
    if (!selectedCrop.isNullOrEmpty()){
       val l= homeViewModel.crops.filter {  it.cropName.lowercase(Locale.ROOT)
           .contains(selectedCrop.lowercase(Locale.ROOT)) }
        if (l.isNotEmpty()) {
            homeViewModel.selectedDiseaseCrop = l[0]
        }
    }
    val diseases = if (homeViewModel.selectedDiseaseCrop == null)
        homeViewModel.diseases
    else
        homeViewModel.diseases.filter { it.crops?.contains(homeViewModel.selectedDiseaseCrop?.uuid) ?: false }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = str(id = R.string.diseases_and_insects_in_crops),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onNavigationItemClicked(Screen.Diseases) }
            )
        }
        val scrollState = rememberLazyListState()
        val crops by rememberUpdatedState(homeViewModel.crops)
        LaunchedEffect(homeViewModel.selectedDiseaseCrop) {
            // Scroll to the selected crop once the layout is ready
            val index = crops.indexOfFirst { it.cropName == homeViewModel.selectedDiseaseCrop?.cropName }
            if (index >= 0) {
                scrollState.animateScrollToItem(index)
            }
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = scrollState
        ) {
            items(crops) { crop ->
                CropChip(
                    title = crop.cropName,
                    leadingIconId = R.drawable.ic_crop,
                    leadingIconColor = if (homeViewModel.selectedDiseaseCrop?.cropName == crop.cropName) Color.White else Color.Unspecified,
                    backgroundColor = if (homeViewModel.selectedDiseaseCrop?.cropName == crop.cropName) Color.Cameron else Color.LightGray.copy(
                        alpha = .3f
                    ),
                    contentColor = if (homeViewModel.selectedDiseaseCrop?.cropName == crop.cropName) Color.White else Color.Cameron,
                    onClick = { homeViewModel.selectedDiseaseCrop = crop }
                )
            }
        }



        if (diseases.isNotEmpty()) {
            LazyRow {
                items(diseases) { disease ->
                    DiseaseItem(
                        modifier = Modifier.size(180.dp, 200.dp),
                        pair = Pair(
                            disease.localName ?: "",
                            disease.photos?.firstOrNull()?.fileName ?: ""
                        ),
                        onDiseaseClicked = {
                            onDiseaseClicked.invoke(disease)
                        })
                }

            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = str(id = R.string.no_diseases_for_crop),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.medium)
                )
            }
        }

    }
}