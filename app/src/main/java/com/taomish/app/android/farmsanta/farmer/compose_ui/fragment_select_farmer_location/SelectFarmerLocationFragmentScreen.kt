package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_select_farmer_location

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_select_farmer_location.components.FarmerLocationForm
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun SelectFarmerLocationFragmentScreen(
    viewModel: SignUpAndEditProfileViewModel,
    onAddLocationClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.topRoundedLargeShape
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        RemoteImage(
            modifier = Modifier
                .padding(top = spacing.medium)
                .height(400.dp),
            imageLink = "",
            error = R.drawable.ic_location_marker_big
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(top = spacing.large)
                    .fillMaxSize()
                    .background(color = Color.White, shape = shape),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FarmerLocationForm(
                    viewModel = viewModel,
                    onAddLocationClicked = onAddLocationClicked
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.logo_tree),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .border(width = 3.dp, color = Color.White, shape = CircleShape)
            )
        }
    }
}