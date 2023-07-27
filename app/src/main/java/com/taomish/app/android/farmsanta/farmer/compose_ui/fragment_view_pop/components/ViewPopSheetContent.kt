package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_pop.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CircleIconWithText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.AzureRadiance
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OutrageousOrange
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto


@Composable
fun ViewPopSheetContent(
    pop: PopDto?,
    onSaveClicked: () -> Unit,
    onShareClicked: () -> Unit
) {
    if (pop == null) {
        Box(modifier = Modifier.padding(1.dp))
        return
    }
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        /*Column(
            modifier = Modifier
                .clickable { onProfileClicked() },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RemoteImage(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .border(width = 0.3.dp, color = Color.Cameron, shape = CircleShape),
                imageLink = "",
                contentScale = ContentScale.Crop
            )

            Text(
                text = str(id = R.string.view_profile),
                color = Color.Cameron,
                modifier = Modifier.padding(vertical = spacing.small)
            )
        }*/

        CircleIconWithText(
            iconId = R.drawable.ic_bookmark,
            text = str(id = R.string.save),
            tint = Color.OutrageousOrange,
            onClick = onSaveClicked
        )

        CircleIconWithText(
            iconId = R.drawable.ic_share_paper_plane,
            text = str(id = R.string.share),
            tint = Color.AzureRadiance,
            onClick = onShareClicked
        )
    }
}