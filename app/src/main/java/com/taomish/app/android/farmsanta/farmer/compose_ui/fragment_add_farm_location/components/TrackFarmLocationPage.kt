package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farm_location.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerButtonWithIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun TrackFarmLocationPage(
    onTrackLocationClicked: () -> Unit,
    onPageChange: (Int) -> Unit
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .padding(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.add_land_location),
            color = Color.Cameron,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing.small)
        )


        FarmerButtonWithIcon(
            modifier = Modifier.padding(top = spacing.medium),
            text = stringResource(id = R.string.track_current_location),
            textStyle = MaterialTheme.typography.caption,
            iconId = R.drawable.ic_profile_location,
            iconSize = 16.dp,
            contentPadding = PaddingValues(horizontal = spacing.small),
            onClick = onTrackLocationClicked
        )

        Text(
            text = stringResource(id = R.string.add_location),
            color = Color.Cameron,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing.medium)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource()
                ) { onPageChange(1) }
        )
    }
}