package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.DotsIndicator
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Land


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyLands(
    modifier: Modifier = Modifier,
    lands: List<Land>,
    goToMapView: (Int) -> Unit
) {
    val spacing = LocalSpacing.current
    val pagerState = rememberPagerState()
    val colors = listOf(
        Color.Cameron,
        Color.OrangePeel,
        Color.OutrageousOrange,
        Color.Denim,
        Color.Thunderbird
    )

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "${str(id = R.string.my_farms)} (${lands.size})",
            color = Color.Cameron,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing.small)
        )

        if (lands.isEmpty()) {
            Text(
                text = str(id = R.string.no_land_have_been_added_yet),
                color = Color.Cameron,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )
        }


        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            count = lands.size
        ) { page ->
            LandView(
                land = lands[page],
                backgroundColor = colors[page % lands.size],
                onViewFarm = { goToMapView(page) }
            )
        }

        DotsIndicator(
            totalDots = pagerState.pageCount,
            selectedIndex = pagerState.currentPage,
            unSelectedColor = Color.Gray
        )
    }
}