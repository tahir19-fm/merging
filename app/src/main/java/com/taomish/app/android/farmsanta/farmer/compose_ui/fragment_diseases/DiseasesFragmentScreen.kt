package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_diseases

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SelectableChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components.DiseaseItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel


@Composable
fun DiseasesFragmentScreen(homeViewModel: HomeViewModel, goToDiseaseDetails: () -> Unit) {

    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val disease = homeViewModel.diseases.find { it.crops?.contains(homeViewModel.selectedDiseaseCrop?.uuid) == true }

    Column(modifier = Modifier.fillMaxSize()) {

        CommonTopBar(
            activity = context as AppCompatActivity,
            isAddRequired = false,
            title = str(id = R.string.back),
            addClick = {}
        )

        LazyRow(
            modifier = Modifier.padding(spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(homeViewModel.crops) { crop ->
                SelectableChip(
                    text = crop.cropName,
                    isSelected = homeViewModel.selectedDiseaseCrop == crop,
                    selectedBackgroundColor = Color.Cameron,
                    onClick = {
                        homeViewModel.selectedDiseaseCrop = crop
                    }
                )
            }
        }

        if (disease != null) {
            LazyVerticalGrid(
                modifier = Modifier.padding(spacing.small),
                columns = GridCells.Adaptive(100.dp)
            ) {
                items(disease.photos.orEmpty()) { image ->
                    DiseaseItem(
                        modifier = Modifier.size(80.dp, 140.dp),
                        pair = Pair(disease.localName ?: "", image?.fileName ?: ""),
                        onDiseaseClicked = {
                            homeViewModel.selectedDisease = disease.apply {
                                photos?.forEach {
                                    it?.isSelected = false
                                }
                                image?.isSelected = true
                            }
                            goToDiseaseDetails.invoke()
                        }
                    )
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