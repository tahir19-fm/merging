package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.onTap
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components.SearchBarButtons
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.disease.Disease
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import kotlinx.coroutines.launch


@Composable
fun HomeFragmentScreen(
    homeViewModel: HomeViewModel,
    onNavigationItemClicked: (Screen) -> Unit,
    openViewAdvisoryFragment: (CropAdvisory) -> Unit,
    goToQueryDetailsFragment: (FarmScouting, Int) -> Unit,
    goToViewPop: (PopDto) -> Unit,
    goToDiseaseDetails: (Disease) -> Unit,
    fetchMarketData: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val scope = rememberCoroutineScope()
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val state = rememberScrollState()
    BackHandler(hasFocus.value) { focusManager.clearFocus() }
    val searchText by homeViewModel.searchText.observeAsState("")
    var scrollToPosition = 0f

    val onSearchHome: () -> Unit = {
        homeViewModel.searchText.postValue(searchText)
        scope.launch {
            if (searchText.isEmpty()) state.animateScrollTo(0)
            else state.animateScrollTo(scrollToPosition.toInt())
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(state = state)
            .onTap { focusManager.clearFocus() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_home_top_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small)
            ) {

                Prices(
                    getPrices = homeViewModel::getPrices,
                    backgroundColor = Color.White,
                    contentColor = Color.Cameron
                )

                Spacer(modifier = Modifier.height(spacing.small))

                FarmerTextField(
                    text = { searchText ?: "" },
                    onValueChange = { homeViewModel.searchText.postValue(it) },
                    modifier = Modifier
                        .padding(spacing.small)
                        .fillMaxWidth(),
                    boxHeight = 36.dp,
                    backgroundColor = Color.White,
                    placeholderText = str(id = R.string.search),
                    textStyle = MaterialTheme.typography.caption,
                    shape = CircleShape,
                    hasFocus = hasFocus,
                    keyboardActions = KeyboardActions(onSearch = { onSearchHome() }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    trailingIcon = {
                        SearchBarButtons(
                            onSearch = onSearchHome,
                            showClose = { hasFocus.value },
                            onClose = {
                                focusManager.clearFocus()
                                homeViewModel.searchText.postValue("")
                            }
                        )
                    }
                )
            }
        }
        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                MarketNavigation()
            }
        }
        NavigationBoardCard(onNavigationItemClicked = onNavigationItemClicked)

        /*FertilizerCalculatorBanner(onNavigationItemClicked = onNavigationItemClicked)*/


        WeatherCard(
            onNavigationItemClicked = onNavigationItemClicked,
            homeViewModel = homeViewModel
        )

        LatestCropAdvisoriesSection(
            modifier = Modifier.onGloballyPositioned { coordinates ->
                scrollToPosition = coordinates.positionInParent().y
            },
            searchText = searchText,
            getAdvisories = homeViewModel::getCropAdvisories,
            onNavigationItemClicked = onNavigationItemClicked,
            openViewAdvisoryFragment = openViewAdvisoryFragment
        )

        PopsSection(
            searchText = searchText,
            getPops = homeViewModel::getUserPops,
            onNavigationItemClicked = onNavigationItemClicked,
            goToViewPop = goToViewPop
        )

        MarketAnalysisGraph(
            viewModel = homeViewModel,
            onNavigationItemClicked = onNavigationItemClicked,
            fetchMarketData = fetchMarketData
        )

        NearbyRelatedFarmQueriesSection(
            homeViewModel = homeViewModel,
            getScouting = homeViewModel::getFarmsScouting,
            onNavigationItemClicked = onNavigationItemClicked,
            goToQueryDetailsFragment = goToQueryDetailsFragment
        )

        DiseasesInCropsSection(
            homeViewModel = homeViewModel,
            onDiseaseClicked = goToDiseaseDetails,
            onNavigationItemClicked = onNavigationItemClicked
        )
    }
}