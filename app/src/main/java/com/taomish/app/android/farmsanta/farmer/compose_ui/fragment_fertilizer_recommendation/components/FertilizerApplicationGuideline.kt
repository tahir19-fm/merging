package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_recommendation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun FertilizerApplicationGuideline() {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .background(color = Color.White, shape = shape)
    ) {

        Text(
            text = str(id = R.string.urea_fertilizer_schedule),
            color = Color.Limeade,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(vertical = spacing.medium, horizontal = spacing.small)
        )

        Text(
            text = str(id = R.string.disease_description_sample),
            style = MaterialTheme.typography.overline,
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth()
                .border(width = .3.dp, color = Color.LightGray, shape = shape)
                .padding(spacing.small)
        )
    }
}