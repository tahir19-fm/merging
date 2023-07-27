package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropAdvisoryViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components.SearchBarButtons
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components.AdvisoryItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components.SearchFilterBottomSheet
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.utils.expand
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CropAdvisoryInboxFragmentScreen(
    viewModel: CropAdvisoryViewModel,
    onSearch: () -> Unit,
    onClose: () -> Unit,
    onFilterApply: () -> Unit,
    goToAdvisoryDetails: (CropAdvisory) -> Unit,
) {
    val spacing = LocalSpacing.current
    val sheetShape = LocalShapes.current.topRoundedMediumShape
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val hasFocus = remember { mutableStateOf(false) }
    val closeSheet: () -> Unit = {
        scope.launch { sheetState.hide() }
    }
    val openSheet: () -> Unit =
        { scope.launch { sheetState.expand() } }
    BackHandler(sheetState.isVisible || hasFocus.value) {
        focusManager.clearFocus()
        closeSheet()
    }

    val onSearchAdvisory: () -> Unit = {
        if (viewModel.text.value.length > 2) {
            focusManager.clearFocus()
            onSearch()
        } else {
            context.showToast(R.string.search_crops_msg)
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            SearchFilterBottomSheet(
                cropAdvisoryViewModel = viewModel,
                onClickApply = {
                    onFilterApply()
                    closeSheet()
                },
                onClickCancel = closeSheet
            )
        },
        sheetShape = sheetShape,
        scrimColor = Color.Cameron.copy(alpha = .2f),
        sheetBackgroundColor = Color.White
    ) {
        Column {
            CommonTopBar(
                activity = context as AppCompatActivity,
                title = str(id = R.string.crop_advisory),
                isAddRequired = false,
                addClick = {}
            )
            LazyColumn {
                item {

                    FarmerTextField(
                        text = { viewModel.text.value },
                        onValueChange = {
                            viewModel.text.postValue(it)
                            if (it.isEmpty()) {
                                onClose()
                            }
                        },
                        modifier = Modifier
                            .padding(spacing.small)
                            .fillMaxWidth(),
                        backgroundColor = Color.LightGray.copy(alpha = .2f),
                        placeholderText = str(id = R.string.search),
                        textStyle = MaterialTheme.typography.caption,
                        shape = CircleShape,
                        hasFocus = hasFocus,
                        keyboardActions = KeyboardActions(onSearch = { onSearchAdvisory() }),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        trailingIcon = {
                            if (hasFocus.value) {
                                SearchBarButtons(
                                    onSearch = onSearchAdvisory,
                                    showClose = { hasFocus.value },
                                    onClose = {
                                        focusManager.clearFocus()
                                        viewModel.text.postValue("")
                                        onClose()
                                    }
                                )
                            } else {
                                FilterIcon(16.dp, isFiltered = viewModel.isFiltered) {
                                    if (viewModel.isFiltered) {
                                        viewModel.clearFilter()
                                    } else {
                                        openSheet()
                                    }
                                }
                            }
                        }
                    )

                }

                itemsIndexed(viewModel.cropAdvisories) { _, advisory ->
                    AdvisoryItem(
                        advisory = advisory,
                        cropName = viewModel.cropsMap.value[advisory.crop]?.cropName,
                        advisoryTag = advisory.advisoryTagName
                            ?: viewModel.advisoryTags.value[advisory.advisoryTag]?.name,
                        onAdvisoryClicked = { goToAdvisoryDetails(advisory) }
                    )
                }
            }

        }
    }

    if (viewModel.cropAdvisories.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyList(text = str(id = R.string.nothing_to_show))
        }
    }
}

@Composable
fun FilterIcon(
    iconSize: Dp = 12.dp,
    isFiltered: Boolean = false,
    onClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Icon(
        painter = painterResource(
            id = if (isFiltered) R.drawable.ic_close else R.drawable.ic_settings_filter
        ),
        contentDescription = null,
        tint = Color.Cameron,
        modifier = Modifier
            .padding(end = spacing.extraSmall)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .size(iconSize)
    )
}