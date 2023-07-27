package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polygon
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.utils.bitmapDescriptor
import com.taomish.app.android.farmsanta.farmer.utils.getLocationName


@Composable
fun DrawLand(
    getCoordinates: () -> ImmutableList<LatLng>,
    center: LatLng?,
    getFarmName: () -> String
) {
    val context = LocalContext.current
    val marker = bitmapDescriptor(context, R.mipmap.ic_sign_up_location_field_foreground)
    val circle = bitmapDescriptor(context, R.drawable.ic_circle, 20)
    getCoordinates().let { coordinates ->
        if (coordinates.isNotEmpty()) {
            Polygon(
                points = getCoordinates(),
                fillColor = Color.Green.copy(alpha = .2f),
                strokeColor = Color.Cameron,
                strokeJointType = JointType.BEVEL,
                strokeWidth = 5f
            )

            center?.let {
                Marker(
                    position = it,
                    title = getFarmName(),
                    snippet = it.getLocationName(context),
                    icon = marker
                )
            }

            getCoordinates().forEach { position ->
                Marker(
                    position = position,
                    title = getFarmName(),
                    snippet = position.getLocationName(context),
                    icon = circle
                )
            }
        }
    }
}