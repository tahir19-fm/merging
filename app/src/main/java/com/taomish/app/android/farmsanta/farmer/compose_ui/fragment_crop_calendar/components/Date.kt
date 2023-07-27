package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OrangePeel
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto


@Composable
fun Date(date: String, onEditClicked: () -> Unit) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .padding(spacing.small)
            .background(color = Color.Cameron, shape = CircleShape)
            .padding(horizontal = spacing.small, vertical = spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = date,
            color = Color.White,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(spacing.extraSmall)
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.OrangePeel,shape = RoundedCornerShape(16.dp))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit_calendar),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(spacing.extraSmall)
                    .size(18.dp)
                    .clickable {
                        onEditClicked()
                    }
            )
        }
    }
}