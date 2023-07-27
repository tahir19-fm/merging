package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components.AddCropChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components.AddCropsTabs
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components.AllTabsContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components.SearchBarButtons
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components.FarmerPager
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddCropsFragmentScreen(
    viewModel: SignUpAndEditProfileViewModel,
    onSearch: () -> Unit,
    onClose: () -> Unit,
    onDone: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    val context = LocalContext.current
    val state = rememberPagerState()
    val scope = rememberCoroutineScope()
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val selected = remember { mutableStateOf(0) }
    selected.postValue(state.currentPage)
    BackHandler(hasFocus.value || viewModel.searchText.value.isNotEmpty()) {
        focusManager.clearFocus()
        if (viewModel.searchText.value.isNotEmpty()) {
            viewModel.searchText.postValue("")
            onClose()
        }
    }
    val onSearchPop: () -> Unit = {
        if (viewModel.searchText.value.length > 2) {
            focusManager.clearFocus()
            onSearch()
        } else {
            context.showToast(R.string.search_crops_msg)
        }
    }

    val getAllSelectedCrops: () -> List<CropMaster> = { viewModel.allSelectedCrops }

    val onSelect: (CropMaster) -> Unit = {
        if (viewModel.allSelectedCrops.size < 3) {
            if (viewModel.allSelectedCrops.find { crop -> it.uuid == crop.uuid } == null) {
                viewModel.allSelectedCrops.add(it)
            } else {
                context.showToast(R.string.already_added_crop)
            }
        } else {
            context.showToast(R.string.maximum_crops_msg)
        }
    }

    val onDelete: (CropMaster) -> Unit = {
        viewModel.allSelectedCrops.remove(it)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(216.dp)
            ) {
                RemoteImage(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .height(144.dp),
                    imageLink = "",
                    contentScale = ContentScale.FillBounds,
                    error = R.drawable.ic_sign_up_select_crops_background
                )

                RemoteImage(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = spacing.medium, vertical = spacing.large)
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(shape),
                    imageLink = "",
                    contentScale = ContentScale.FillBounds,
                    error = R.drawable.ic_sign_up_select_crops_foreground
                )

                Column(
                    modifier = Modifier
                        .padding(spacing.medium)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {

                    Text(
                        text = str(id = R.string.tell_us_about_your_crops),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            top = spacing.medium,
                            start = spacing.medium,
                            end = spacing.medium
                        )
                    )

                    FarmerTextField(
                        text = { viewModel.searchText.value },
                        onValueChange = {
                            viewModel.searchText.postValue(it)
                            if (it.isEmpty()) {
                                onClose()
                            }
                        },
                        modifier = Modifier
                            .padding(spacing.small)
                            .background(color = Color.White, shape = CircleShape)
                            .fillMaxWidth(),
                        placeholderText = str(id = R.string.search),
                        textStyle = MaterialTheme.typography.caption,
                        shape = CircleShape,
                        hasFocus = hasFocus,
                        keyboardActions = KeyboardActions(onSearch = { onSearchPop() }),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        trailingIcon = {
                            SearchBarButtons(
                                onSearch = onSearchPop,
                                showClose = { hasFocus.value },
                                onClose = {
                                    focusManager.clearFocus()
                                    viewModel.searchText.postValue("")
                                    onClose()
                                }
                            )
                        }
                    )
                }

                AddCropsTabs(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    selected = { selected.value },
                    setSelected = { selected.postValue(it) },
                    tabs = viewModel.tabs,
                    state = state,
                    scope = scope
                )
            }

            Divider(
                thickness = .3.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(spacing.medium)
            )

            if (viewModel.allSelectedCrops.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .padding(spacing.small)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    items(getAllSelectedCrops()) { item ->
                        AddCropChip(crop = item, onDelete = onDelete)
                    }
                }
            } else {
                Text(
                    text = str(id = R.string.select_your_crops),
                    color = Color.Cameron,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(spacing.small)
                )
            }

            FarmerPager(
                modifier = Modifier.fillMaxSize(),
                state = state,
                tabs = { viewModel.tabs.size },
                verticalAlignment = Alignment.Top
            ) { page ->
                if (viewModel.allCropDivisions.isNotEmpty()) {
                    AllTabsContent(
                        crops = viewModel.allCrops.value[viewModel.allCropDivisions[page].uuid].orEmpty(),
                        onSelect = onSelect
                    )
                }
            }
        }

        RoundedShapeButton(
            modifier = Modifier
                .padding(bottom = spacing.small)
                .align(Alignment.BottomCenter)
                .fillMaxWidth(.5f),
            text = str(id = R.string.done),
            textStyle = MaterialTheme.typography.body2,
            textPadding = spacing.tiny,
            onClick = onDone
        )
    }
}