package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmptySavedFertilizer(modifier: Modifier = Modifier, onCalculateClicked: () -> Unit) {
    val spacing = LocalSpacing.current
    Box(modifier = modifier) {
        Text(
            text = str(id = R.string.saved_fertilizer_recommendations),
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(spacing.small)
                .align(Alignment.TopStart)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_fertilizer_calc_mascot),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
            )

            Text(
                text = str(id = R.string.no_saved_fertilizer_recommendations),
                color = Color.Limeade,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(spacing.small)
            )

            Chip(
                onClick = onCalculateClicked,
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.Limeade,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = str(id = R.string.calculate_fertilizer),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}