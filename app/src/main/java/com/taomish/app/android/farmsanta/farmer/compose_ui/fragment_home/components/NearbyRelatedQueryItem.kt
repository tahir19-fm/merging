package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil


@Composable
fun NearbyRelatedQueryItem(
    homeViewModel: HomeViewModel,
    farmScouting: FarmScouting,
    goToQueryDetailsFragment: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .width(180.dp)
            .padding(spacing.extraSmall)
            .background(color = Color.LightGray.copy(alpha = .2f))
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = goToQueryDetailsFragment
            ),
        verticalArrangement = Arrangement.Center
    ) {
        val imagePath = farmScouting.images.getOrNull(0)?.image ?: ""
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp)
                .padding(horizontal = spacing.extraSmall, vertical = spacing.small)
        ) {
            RemoteImage(
                modifier = Modifier.fillMaxSize(),
                imageLink = URLConstants.S3_IMAGE_BASE_URL + imagePath,
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
            valueText = DataHolder.getInstance().cropArrayList?.find {
                it.uuid == farmScouting.crop
            }?.cropName ?: ""
        )

        MetaAndValueTextRow(
            modifier = rowModifier,
            metaText = str(id = R.string.growth_stage),
            valueText = homeViewModel.growthStages.find {
                it.uuid == farmScouting.cropStage
            }?.name ?: ""
        )

        MetaAndValueTextRow(
            modifier = rowModifier,
            metaText = str(id = R.string.plant_part_issue),
            valueText = farmScouting.images.firstOrNull()?.plantPart ?: ""
        )

        RoundedShapeButton(
            modifier = rowModifier,
            text = str(id = if (farmScouting.advisoryExist) R.string.view_solution else R.string.view_query),
            textPadding = spacing.tiny,
            textStyle = MaterialTheme.typography.caption,
            onClick = goToQueryDetailsFragment
        )
    }
}