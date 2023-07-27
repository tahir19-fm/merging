package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Chip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.Advisory
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil


@Composable
fun QuerySolutionSection(advisory: Advisory?) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape

    val modifier = if (advisory == null) Modifier.height(240.dp) else Modifier

    if (advisory == null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(spacing.small)
                .background(color = Color.LightGray.copy(alpha = .2f), shape = shape)
        ) {

            Text(
                text = str(id = R.string.query_solution),
                style = MaterialTheme.typography.body2,
                color = Color.Cameron,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(spacing.small)
            )

            Chip(
                modifier = Modifier
                    .align(Alignment.Center),
                text = str(id = R.string.pending_query),
                textPadding = spacing.small,
                textStyle = MaterialTheme.typography.caption,
                backgroundColor = Color.Black.copy(alpha = .3f)
            )
        }
    } else {
        Column(
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth()
                .background(color = Color.LightGray.copy(alpha = .2f), shape = shape)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = str(id = R.string.query_solution),
                    style = MaterialTheme.typography.body2,
                    color = Color.Cameron
                )

                Chip(
                    modifier = Modifier
                        .padding(start = spacing.small, bottom = spacing.small),
                    text = DateUtil().getDateMonthYearFormat(advisory.createdTimestamp),
                    textPadding = spacing.extraSmall,
                    textStyle = MaterialTheme.typography.overline,
                    backgroundColor = Color.Cameron
                )
            }

            CropDisease(advisory = advisory)
        }
    }
}