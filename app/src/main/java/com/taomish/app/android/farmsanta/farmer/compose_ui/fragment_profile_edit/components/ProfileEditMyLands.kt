package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.DotsIndicator
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farmer.components.EditLand
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Land


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileEditMyLands(
    modifier: Modifier = Modifier,
    lands: List<Land>,
    onAddFarmClicked: () -> Unit,
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

        Row(
            modifier = Modifier
                .padding(vertical = spacing.small)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${str(id = R.string.my_farms)} (${lands.size})",
                color = Color.Cameron,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
                    .clickable { onAddFarmClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_add_new),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onAddFarmClicked() }
                )

                Text(
                    text = str(id = R.string.add_farm),
                    color = Color.Cameron,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = spacing.extraSmall)
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            count = lands.size
        ) { page ->
            EditLand(
                farmName = lands[page].landName ?: "N/A",
                farmAddress = lands[page].farmLocation ?: "N/A",
                backgroundColor = colors[page % lands.size],
                onEdit = { goToMapView(page) }
            )
        }

        DotsIndicator(
            totalDots = pagerState.pageCount,
            selectedIndex = pagerState.currentPage,
            unSelectedColor = Color.Gray
        )
    }
}