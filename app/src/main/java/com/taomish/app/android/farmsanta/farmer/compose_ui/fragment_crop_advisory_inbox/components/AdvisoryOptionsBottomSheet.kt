package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CircleIconWithText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.AzureRadiance
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OutrageousOrange


@Composable
fun AdvisoryOptionsBottomSheet(
    onViewAdvisoryClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onShareClicked: () -> Unit
    ) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleIconWithText(
            iconId = R.drawable.ic_advisory,
            text = str(id = R.string.view_advisory),
            textStyle = MaterialTheme.typography.caption,
            tint = Color.Cameron,
            onClick = onViewAdvisoryClicked
        )

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