package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.FarmSantaTag
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.AdditionalField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FarmScoutingViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details.components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.expand
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FarmScoutDetailsFragmentScreen(viewModel: FarmScoutingViewModel) {

    val farmScouting = viewModel.selectedScouting.value
    if (farmScouting == null) {
        Box(modifier = Modifier.padding(1.dp))
        return
    }

    val spacing = LocalSpacing.current
    val sheetShape = LocalShapes.current.topRoundedMediumShape
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    BackHandler(sheetState.isVisible) {
        focusManager.clearFocus()
        scope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            ScoutDetailChatBottomSheetContent(
                text = viewModel.chatTextField,
                textError = viewModel.textError,
                focusManager = focusManager,
                onSendMessageClicked = {}
            )
        },
        sheetShape = sheetShape,
        scrimColor = Color.Cameron.copy(alpha = .2f),
        sheetBackgroundColor = Color.White
    ) {

        Column {
            CommonTopBar(
                activity = context as AppCompatActivity,
                isAddRequired = false,
                title = "${viewModel.crops.value[farmScouting.crop]?.cropName ?: "-"} " + str(id = R.string.query),
                addClick = {}
            )
            LazyColumn(modifier = Modifier.padding(spacing.small)) {
                item {

                    ScoutDetailImageCard(farmScouting = farmScouting)

                    Text(
                        text = "${str(id = R.string.plant)}: " +
                                (viewModel.crops.value[farmScouting.crop]?.cropName ?: "-"),
                        style = MaterialTheme.typography.caption,
                        color = Color.Cameron,
                        modifier = Modifier
                            .padding(horizontal = spacing.small, vertical = spacing.tiny)
                            .fillMaxWidth()
                    )


                    Text(
                        text = "${str(id = R.string.location)}: ${viewModel.locationName.value}",
                        style = MaterialTheme.typography.caption,
                        color = Color.Cameron,
                        modifier = Modifier
                            .padding(horizontal = spacing.small, vertical = spacing.tiny)
                            .fillMaxWidth()
                    )


                    Text(
                        text = "${str(id = R.string.growth_stage)}: " +
                                (viewModel.stages.value[farmScouting.cropStage]?.name ?: "N/A"),
                        style = MaterialTheme.typography.caption,
                        color = Color.Cameron,
                        modifier = Modifier
                            .padding(horizontal = spacing.small, vertical = spacing.tiny)
                            .fillMaxWidth()
                    )


                    Text(
                        text = "${str(id = R.string.plant_part_issue)}: ${viewModel.plantPartsIssue.value}",
                        style = MaterialTheme.typography.caption,
                        color = Color.Cameron,
                        modifier = Modifier
                            .padding(horizontal = spacing.small, vertical = spacing.tiny)
                            .fillMaxWidth()
                    )


                    Query(
                        caption = farmScouting.caption,
                        isSolved = false,
                        replies = 20,
                        onClickMessages = { scope.launch { sheetState.expand() } }
                    )
                    Log.d("SCOUTBUG", "FarmScoutDetailsFragmentScreen: crop data is ${viewModel.advisory.value?.advisoryDetails}")
                    QuerySolutionSection(advisory = viewModel.advisory.value)

                    FarmSantaTag(title = str(id = R.string.personalized_notes))

                    AdditionalField(farmScouting.images.joinToString(separator = "\n") {
                        it.comment ?: ""
                    })

                    Spacer(modifier = Modifier.height(spacing.medium))
                }
            }

        }

    }
}
