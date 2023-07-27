package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_advisories

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropAdvisoryViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components.AdvisoryDetailBottomSheetContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components.AdvisoryItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_advisories.components.CustomDropDown
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_advisories.components.GridAdvisoryItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.utils.expand
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SavedAdvisoriesFragmentScreen(
    viewModel: CropAdvisoryViewModel,
    advisories: List<CropAdvisory>
) {
    val spacing = LocalSpacing.current
    val sheetShape = LocalShapes.current.topRoundedMediumShape
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var selectedAdvisory by remember { mutableStateOf<CropAdvisory?>(null) }
    val items = listOf(
        Pair(R.drawable.ic_list, str(id = R.string.list_view)),
        Pair(R.drawable.ic_grid, str(id = R.string.block_view))
    )
    val selected =
        remember { mutableStateOf(R.drawable.ic_list to context.getString(R.string.list_view)) }
    val openSheet: () -> Unit =
        { scope.launch { sheetState.expand() } }
    val focusManager = LocalFocusManager.current
    BackHandler(sheetState.isVisible) {
        focusManager.clearFocus()
        scope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            AdvisoryDetailBottomSheetContent(
                advisory = selectedAdvisory
            )
        },
        sheetShape = sheetShape,
        scrimColor = Color.Cameron.copy(alpha = .2f),
        sheetBackgroundColor = Color.White
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {

            CommonTopBar(
                activity = context as AppCompatActivity,
                title = str(id = R.string.saved_advisories),
                isAddRequired = false,
                addClick = {}
            )

            CustomDropDown(selected = selected, items = items)

            if (selected.value.second == items[0].second) {
                LazyColumn(contentPadding = PaddingValues(spacing.small)) {
                    items(advisories) { advisory ->
                        AdvisoryItem(
                            advisory = advisory,
                            cropName = viewModel.cropsMap.value[advisory.crop]?.cropName,
                            advisoryTag = advisory.advisoryTagName
                                ?: viewModel.advisoryTags.value[advisory.advisoryTag]?.name,
                            onAdvisoryClicked = {
                                selectedAdvisory = advisory
                                openSheet()
                            }
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(180.dp),
                    contentPadding = PaddingValues(spacing.small)
                ) {
                    items(advisories) { advisory ->
                        GridAdvisoryItem(
                            advisory = advisory,
                            cropName = viewModel.cropsMap.value[advisory.crop]?.cropName ?: "N/A",
                            advisoryTag = viewModel.advisoryTags.value[advisory.advisoryTag]?.name
                                ?: "N/A",
                            onAdvisoryClicked = {
                                selectedAdvisory = advisory
                                openSheet()
                            }
                        )
                    }
                }
            }
        }
    }
}