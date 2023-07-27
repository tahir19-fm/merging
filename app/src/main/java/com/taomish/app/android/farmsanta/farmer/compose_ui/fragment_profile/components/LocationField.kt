package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.MetaText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ValueText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun LocationField(
    address: String,
    country: String,
    state: String,
    district: String,
    subDistrict: String,
    pin: String,
    village: String
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    var size by remember { mutableStateOf(Size.Zero) }
    val height: @Composable () -> Dp = { with(LocalDensity.current) { size.height.toDp() } }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
            .background(color = Color.Cameron.copy(alpha = 0.1f), shape = shape)
            .onGloballyPositioned { size = it.size.toSize() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_profile_location),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = spacing.small)
                .size(32.dp),
            tint = Color.Unspecified
        )

        Divider(
            color = Color.Gray,
            modifier = Modifier
                .height(height() - spacing.small)
                .width(1.dp)
        )

        Column(
            modifier = Modifier
                .padding(spacing.small)
        ) {
            Text(
                text = str(id = R.string.location),
                color = Color.Gray,
                style = MaterialTheme.typography.caption
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                MetaText(text = "${str(id = R.string.address)}: ")
                ValueText(text = address)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.fillMaxWidth(.5F)) {
                    MetaText(text = "${str(id = R.string.country)}: ")
                    ValueText(text = country)
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    MetaText(text = "${str(id = R.string.state)}: ")
                    ValueText(text = state)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.fillMaxWidth(.5F)) {
                    MetaText(text = "${str(id = R.string.district)}: ")
                    ValueText(text = district)
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    MetaText(text = "${str(id = R.string.sub_district)}: ")
                    ValueText(text = subDistrict)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.fillMaxWidth(.5F)) {
                    MetaText(text = "${str(id = R.string.village)}: ")
                    ValueText(text = village)
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    MetaText(text = "${str(id = R.string.pin_code)}: ")
                    ValueText(text = pin)
                }
            }
        }
    }
}