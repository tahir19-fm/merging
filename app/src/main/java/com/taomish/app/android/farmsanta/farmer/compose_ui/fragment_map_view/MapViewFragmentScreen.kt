package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view.components.DrawLand
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view.components.ImmutableList
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view.components.MapButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view.components.MapInfoDialog
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OrangePeel
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Land
import com.taomish.app.android.farmsanta.farmer.utils.getCenterCoordinate
import com.taomish.app.android.farmsanta.farmer.utils.getLocationName
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

private const val MAP_ZOOM_FACTOR = 15f

@Composable
fun MapViewFragmentScreen(
    viewModel: SignUpAndEditProfileViewModel,
    viewLand: Land?,
    onDone: () -> Unit,
) {
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val cameraPositionState = rememberCameraPositionState {
        if (viewModel.coordinates.value.isNotEmpty()) {
            viewModel.coordinates.value.getCenterCoordinate()?.let {
                position = CameraPosition.fromLatLngZoom(it, MAP_ZOOM_FACTOR)
            }
        } else {
            DataHolder.getInstance().lastKnownLocation?.let {
                position = CameraPosition.fromLatLngZoom(
                    LatLng(it.latitude, it.longitude),
                    MAP_ZOOM_FACTOR
                )
            }
        }
    }
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = true,
            )
        )
    }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL, isMyLocationEnabled = true))
    }
    var isAddingBoundary by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
            onMapLoaded = {
                moveMapToLocation(viewLand, cameraPositionState, viewModel)
            },
            onMapClick = {
                if (isAddingBoundary && viewModel.coordinates.value.size < 4) {
                    viewModel.coordinates.postValue(ImmutableList(viewModel.coordinates.value + it))
                }
            }
        ) {
            DrawLand(
                getCoordinates = { viewModel.coordinates.value },
                center = viewModel.coordinates.value.getCenterCoordinate(),
                getFarmName = { viewModel.farmName.value }
            )
        }

        Column(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.TopEnd)
                .padding(spacing.medium),
            verticalArrangement = Arrangement.spacedBy(spacing.extraSmall),
            horizontalAlignment = Alignment.End
        ) {
            MapButton(
                titleId = R.string.pin_boundary,
                iconId = if (isAddingBoundary) R.drawable.ic_close else R.drawable.ic_add_boundary,
                tint = Color.Cameron
            ) {
                if (!isAddingBoundary) {
                    viewModel.coordinates.postValue(ImmutableList(emptyList()))
                    viewModel.farmLocation = null
                    context.showToast(R.string.select_your_land)
                }

                isAddingBoundary = !isAddingBoundary
            }

            if (viewModel.coordinates.value.isNotEmpty() && isAddingBoundary) {
                MapButton(
                    titleId = R.string.undo,
                    iconId = R.drawable.ic_undo,
                    tint = Color.OrangePeel
                ) {
                    viewModel.coordinates.postValue(
                        ImmutableList(viewModel.coordinates.value - viewModel.coordinates.value.last())
                    )
                }
            }
        }

        RoundedShapeButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = spacing.large),
            text = str(id = R.string.add_farm),
            enabled = viewModel.coordinates.value.size > 2
        ) {
            if (viewModel.coordinates.value.size > 2) {
                viewModel.farmLocation = viewModel.coordinates.value.getCenterCoordinate()
                    ?.getLocationName(context)
                onDone()
            } else {
                context.showToast(R.string.farm_least_points_msg)
            }
        }

        if (!viewModel.didShowDialog) {
            MapInfoDialog { viewModel.didShowDialog = true }
        }
    }
}


private fun moveMapToLocation(
    viewLand: Land?,
    cameraPositionState: CameraPositionState,
    viewModel: SignUpAndEditProfileViewModel,
) {
    if (viewLand != null && viewLand.coordinates != null) {
        viewLand.coordinates?.firstOrNull()?.let {
            cameraPositionState.position =
                CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), MAP_ZOOM_FACTOR)
        }
    } else {
        if (viewModel.coordinates.value.isNotEmpty()) {
            viewModel.coordinates.value.getCenterCoordinate()?.let {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, MAP_ZOOM_FACTOR)
            }
        } else {
            DataHolder.getInstance().lastKnownLocation?.let {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(it.latitude, it.longitude),
                    MAP_ZOOM_FACTOR
                )
            }
        }
    }
}