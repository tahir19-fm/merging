package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_library

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components.SearchBarButtons
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_library.components.AllPopModalSheetContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_library.components.PopCardItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_library.components.SortType
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.SearchBarIconButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun POPLibraryFragmentScreen(
    viewModel: POPViewModel,
    onRefresh: (SortType) -> Unit,
    onSearch: () -> Unit,
    onClose: () -> Unit,
    onBookmarkPop: (PopDto, Int) -> Unit,
    onSharePop: (Int) -> Unit,
    onPopItemClicked: (String) -> Unit,
    onBookmarksClicked: () -> Unit,
) {

    val spacing = LocalSpacing.current
    val sheetShape = LocalShapes.current.topRoundedMediumShape
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val swipeState = rememberSwipeRefreshState(viewModel.isRefreshing.value)
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val closeSheet: () -> Unit = { scope.launch { sheetState.hide() } }
    val expanded = remember { mutableStateOf(false) }
    val filterSelected = remember { mutableStateOf("") }
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
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            AllPopModalSheetContent(
                pop = viewModel.pop.value,
                onViewPopClicked = {
                    onPopItemClicked(viewModel.pop.value?.uuid ?: "")
                    closeSheet()
                },
                onSaveClicked = { onBookmarkPop(it, viewModel.selectedPopIndex) }
            ) { onSharePop(viewModel.selectedPopIndex) }
        },
        sheetShape = sheetShape,
        scrimColor = Color.Cameron.copy(alpha = .2f),
        sheetBackgroundColor = Color.White
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CommonTopBar(
                title = stringResource(id = R.string.pop),
                activity = context as AppCompatActivity,
                isAddRequired = false,
                addClick = {}
            )

            SwipeRefresh(
                state = swipeState,
                onRefresh = { onRefresh(viewModel.lastSortType) },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(state, trigger, contentColor = Color.Cameron)
                }
            ) {
                LazyColumn {
                    item {
                        Row(
                            modifier = Modifier
                                .padding(spacing.small)
                                .height(48.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FarmerTextField(
                                text = { viewModel.searchText.value },
                                onValueChange = { viewModel.searchText.postValue(it) },
                                modifier = Modifier
                                    .fillMaxWidth(.7f)
                                    .fillMaxHeight(),
                                backgroundColor = Color.LightGray.copy(alpha = .2f),
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

                            SearchBarIconButton(
                                iconId = R.drawable.ic_bookmark,
                                padding = spacing.extraSmall,
                                onClick = { onBookmarksClicked() }
                            )

                            Column(modifier = Modifier.padding(end = spacing.small)) {
                                SearchBarIconButton(
                                    iconId = R.drawable.ic_settings_filter,
                                    padding = spacing.extraSmall,
                                    onClick = { expanded.value = true }
                                )

                                FarmerDropDownContent(
                                    modifier = Modifier.padding(end = spacing.extraSmall),
                                    expanded = expanded,
                                    selected = filterSelected,
                                    items = SortType.getNames(),
                                    onSelectOption = { index, _ ->
                                        val sortType = if (index == 0) SortType.Alphabetically
                                        else SortType.By_Date
                                        viewModel.lastSortType = sortType
                                        onRefresh(sortType)
                                    }
                                )
                            }
                        }
                    }
                    itemsIndexed(
                        viewModel.pops.value,
                        key = { _, pop -> pop.uuid }
                    ) { index, pop ->
                        PopCardItem(
                            pop = pop,
                            getCropName = { viewModel.crops[pop.crop]?.cropName ?: "No Name" },
                            onMoreOptionClicked = {
                                viewModel.pop.postValue(it)
                                viewModel.selectedPopIndex = index
                                scope.launch { sheetState.show() }
                            },
                            onPopItemClicked = {
                                viewModel.pop.postValue(pop)
                                onPopItemClicked(pop.uuid)
                                viewModel.selectedPopIndex = index
                            },
                            onBookmarkClicked = { onBookmarkPop(pop, index) },
                            onShareClicked = { index.apply(onSharePop) }
                        )
                    }
                }
            }
        }
    }

    if (viewModel.pops.value.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyList(text = str(id = R.string.nothing_to_show))
        }
    }
}