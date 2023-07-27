package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_image.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil


@Composable
fun QueryItem(
    modifier: Modifier = Modifier,
    farmScouting: FarmScouting,
    getCrop: () -> CropMaster?,
    getStage: () -> CropStage?,
    isQuerySolved: Boolean,
    onViewSolutionClicked: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    Column(
        modifier = modifier
            .padding(spacing.extraSmall)
            .background(color = Color.Cameron.copy(alpha = .1f)),
        verticalArrangement = Arrangement.Center
    ) {
        val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                farmScouting.images.getOrNull(0)
                    ?.image.toString()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp)
                .padding(horizontal = spacing.extraSmall, vertical = spacing.small)
        ) {
            RemoteImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape),
                imageLink = imageLink,
                error = R.mipmap.img_default_pop,
                contentScale = ContentScale.FillBounds
            )

            Chip(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = spacing.small, bottom = spacing.small),
                text = DateUtil().getDateMonthYearFormat(farmScouting.createdTimestamp),
                textPadding = spacing.extraSmall,
                textStyle = MaterialTheme.typography.overline,
                backgroundColor = Color.Black.copy(alpha = .3f)
            )
        }


        val rowModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.extraSmall)

        MetaAndValueTextRow(
            modifier = rowModifier,
            metaText = str(id = R.string.crop),
            valueText = getCrop()?.cropName ?: "N/A"
        )

        MetaAndValueTextRow(
            modifier = rowModifier,
            metaText = str(id = R.string.growth_stage),
            valueText = getStage()?.name ?: "N/A"
        )

        MetaAndValueTextRow(
            modifier = rowModifier,
            metaText = str(id = R.string.plant_part_issue),
            valueText = farmScouting.images.firstOrNull()?.plantPart ?: "N/A"
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FarmerButtonWithIcon(
                modifier = Modifier.padding(start = spacing.extraSmall),
                text = str(id = if (isQuerySolved) R.string.view_solution else R.string.view_query),
                textStyle = MaterialTheme.typography.caption,
                iconId = if (isQuerySolved) R.drawable.ic_done else R.drawable.ic_ask_queries_new,
                iconTint = Color.White,
                onClick = onViewSolutionClicked,
                contentPadding = PaddingValues(horizontal = spacing.extraSmall)
            )
        }
    }
}