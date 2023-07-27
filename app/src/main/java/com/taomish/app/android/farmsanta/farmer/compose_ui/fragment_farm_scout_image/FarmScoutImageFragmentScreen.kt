package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_image

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.findNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FarmScoutingViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components.SearchBarButtons
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_image.components.PendingTabContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_image.components.SolvedTabContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.BlackOlive
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FarmScoutImageFragmentScreen(
    viewModel: FarmScoutingViewModel,
    onSearch: () -> Unit,
    onClose: () -> Unit,
    goToFarmScouting: () -> Unit,
    onAddClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val tabs = listOf(context.getString(R.string.solved), context.getString(R.string.pending))
    val state = rememberPagerState()
    var selected by remember { mutableStateOf(0) }
    var size by remember { mutableStateOf(Size.Zero) }
    val width: @Composable () -> Dp = { with(LocalDensity.current) { size.width.toDp() } }
    val scope = rememberCoroutineScope()
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    BackHandler(hasFocus.value || viewModel.searchText.value.isNotEmpty()) {
        focusManager.clearFocus()
        if (viewModel.searchText.value.isNotEmpty()) {
            viewModel.searchText.postValue("")
            onClose()
        }
    }
    selected = state.currentPage
    val onClickFarmScouting: (FarmScouting) -> Unit = {
        viewModel.selectedScouting.postValue(it)
        goToFarmScouting()
    }
    val onSearchQueries: () -> Unit = {
        if (viewModel.searchText.value.length > 2) {
            focusManager.clearFocus()
            onSearch()
        } else {
            context.showToast(R.string.search_crops_msg)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        ScoutingTopAppBar(
            activity = context as AppCompatActivity
        ) { onAddClick.invoke() }
        Row(
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FarmerTextField(
                text = { viewModel.searchText.value },
                onValueChange = {
                    viewModel.searchText.postValue(it)
                    if (it.isEmpty()) {
                        onClose()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(.85f),
                backgroundColor = Color.LightGray.copy(alpha = .2f),
                placeholderText = str(id = R.string.search_farmer_queries),
                textStyle = MaterialTheme.typography.caption,
                shape = CircleShape,
                hasFocus = hasFocus,
                keyboardActions = KeyboardActions(onSearch = { onSearchQueries() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                trailingIcon = {
                    SearchBarButtons(
                        onSearch = onSearchQueries,
                        showClose = { hasFocus.value },
                        onClose = {
                            focusManager.clearFocus()
                            viewModel.searchText.postValue("")
                            onClose()
                        }
                    )
                }
            )

            /*SearchBarIconButton(
                iconId = R.drawable.ic_sort_icon,
                padding = spacing.extraSmall,
                iconTint = Color.White,
                backgroundColor = Color.Cameron,
                onClick = { }
            )*/
        }

        TabRow(
            modifier = Modifier.fillMaxWidth(.7f),
            selectedTabIndex = selected,
            backgroundColor = Color.Transparent,
            indicator = {
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .farmerTabIndicatorOffset(width(), it[selected]),
                    color = Color.Cameron,
                    height = 3.dp
                )
            },
            divider = { }
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selected == index,
                    onClick = {
                        selected = index
                        scope.launch { state.scrollToPage(index) }
                    },
                    text = {
                        Text(
                            text = tab,
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .onGloballyPositioned { size = it.size.toSize() }
                        )
                    },
                    selectedContentColor = Color.Cameron,
                    unselectedContentColor = Color.LightGray
                )
            }
        }

        HorizontalPager(
            state = state,
            count = tabs.size
        ) { page ->
            when (page) {
                0 -> {
                    SolvedTabContent(
                        getScoutingList = { viewModel.scouting.value[true].orEmpty() },
                        getCrop = { viewModel.crops.value[it] },
                        getStage = { viewModel.stages.value[it] },
                        onClickFarmScouting = onClickFarmScouting,
                        onAddClick = onAddClick
                    )
                }
                1 -> {
                    PendingTabContent(
                        getScoutingList = { viewModel.scouting.value[false].orEmpty() },
                        getCrop = { viewModel.crops.value[it] },
                        getStage = { viewModel.stages.value[it] },
                        onClickFarmScouting = onClickFarmScouting,
                        onAddClick = onAddClick
                    )
                }
            }
        }
    }
}

@Composable
fun ScoutingTopAppBar(
    modifier: Modifier = Modifier,
    activity: AppCompatActivity,
    tintColor: Color = Color.BlackOlive,
    backgroundColor: Color = Color.White,
    onBack: () -> Unit = {},
    addClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(backgroundColor)
            .padding(LocalSpacing.current.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "Back",
            modifier = modifier
                .clickable(
                    onClick = {
                        onBack.invoke()
                        activity.findNavController(R.id.nav_host_fragment_activity_main)
                            .popBackStack()
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false
                    )
                ),
            tint = tintColor
        )

        Text(
            text = str(id = R.string.my_queries),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2,
            modifier = modifier
                .weight(1f)
                .padding(start = 8.dp, end = 8.dp),
            textAlign = TextAlign.Start,
            color = tintColor
        )


        Text(
            text = str(id = R.string.create),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2,
            modifier = modifier.padding(start = 8.dp, end = 8.dp),
            textAlign = TextAlign.Start,
            color = tintColor
        )

        Image(
            painter = painterResource(id = R.drawable.ic_add_new),
            contentDescription = null,
            modifier = modifier
                .padding(start = 8.dp)
                .clickable(
                    onClick = {
                        addClick.invoke()
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false
                    )
                )
        )
    }
}