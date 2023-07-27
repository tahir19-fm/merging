package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower


@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onViewFarmLocationClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(spacing.small)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.RiceFlower, shape = shape)
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_land_map_marker),
                contentDescription = null,
                tint = Color.Unspecified
            )

            RoundedShapeButton(
                modifier = Modifier.wrapContentWidth(),
                text = str(id = R.string.view_farm_location),
                textPadding = spacing.zero,
                onClick = onViewFarmLocationClicked
            )
        }
    }

}