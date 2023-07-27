package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_image.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage


@Composable
fun SolvedTabContent(
    getScoutingList: () -> List<FarmScouting>,
    getCrop: (String) -> CropMaster?,
    getStage: (String) -> CropStage?,
    onClickFarmScouting: (FarmScouting) -> Unit,
    onAddClick: () -> Unit
) {
    val scoutingList = getScoutingList()
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(180.dp)
    ) {
        items(scoutingList) { farmScouting ->
            QueryItem(
                farmScouting = farmScouting,
                getCrop = { getCrop(farmScouting.crop) },
                getStage = { getStage(farmScouting.cropStage ?: "") },
                isQuerySolved = true,
                onViewSolutionClicked = { onClickFarmScouting(farmScouting) }
            )
        }
    }

    if (scoutingList.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyQueries(onClick = onAddClick)
        }
    }
}