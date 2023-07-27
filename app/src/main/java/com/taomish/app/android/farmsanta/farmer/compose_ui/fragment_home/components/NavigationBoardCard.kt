package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun NavigationBoardCard(
    onNavigationItemClicked: (Screen) -> Unit
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    Card(
        modifier = Modifier
            .padding(spacing.medium)
            .fillMaxWidth(),
        elevation = 4.dp,
        shape = shape,
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            IconsRow(
                firstIconId = R.drawable.my_crops,
                firstTextId = R.string.my_crops,
                firstIconColor = Color.Unspecified,
                firstIconOnClick = { onNavigationItemClicked(Screen.MyCrops) },
                secondIconId = R.drawable.my_queries,
                secondTextId = R.string.my_queries,
                secondIconColor = Color.Unspecified,
                secondIconOnClick = { onNavigationItemClicked(Screen.MyQueries) },
                thirdIconId = R.drawable.farm_talk,
                thirdTextId = R.string.farm_talk,
                thirdIconColor = Color.Unspecified,
                thirdIconOnClick = { onNavigationItemClicked(Screen.FarmTalks) }
            )

            IconsRow(
                firstIconId = R.drawable.pop,
                firstTextId = R.string.pop,
                firstIconColor = Color.Unspecified,
                firstIconOnClick = { onNavigationItemClicked(Screen.Pops) },
                secondIconId = R.drawable.ic_advisory,
                secondTextId = R.string.crop_advisory,
                secondIconColor = Color.Unspecified,
                secondIconOnClick = { onNavigationItemClicked(Screen.CropAdvisory) },
               thirdIconId = R.drawable.nutrisource,
                thirdTextId = R.string.nutri_source,
                thirdIconColor = Color.Unspecified,
                thirdIconOnClick = { onNavigationItemClicked(Screen.NutriSource) }
            )

            IconsRow(
                firstIconId = R.drawable.market,
                firstTextId = R.string.market,
                firstIconColor = Color.Unspecified,
                firstIconOnClick = { onNavigationItemClicked(Screen.MarketAnalysis) },
                secondIconId = R.drawable.crop_calendar,
                secondTextId = R.string.crop_calendar,
                secondIconColor = Color.Unspecified,
                secondIconOnClick = { onNavigationItemClicked(Screen.CropCalendar) },
                thirdIconId = R.drawable.fertilizer_calculator_new,
                thirdTextId = R.string.fertilizer_calculator,
                thirdIconColor = Color.Unspecified,
                thirdIconOnClick = { onNavigationItemClicked(Screen.FertilizerCalculator) }
            )
        }
    }
}