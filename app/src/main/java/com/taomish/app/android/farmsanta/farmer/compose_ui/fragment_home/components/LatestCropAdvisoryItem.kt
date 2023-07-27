package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import com.taomish.app.android.farmsanta.farmer.utils.NamesAndFormatsUtil.getAdvisoryTagName


@Composable
fun LatestCropAdvisoryItem(
    advisory: CropAdvisory,
    onAdvisoryClicked: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .padding(spacing.extraSmall)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onAdvisoryClicked
            ),
        verticalArrangement = Arrangement.Center
    ) {

        ProfileImageWithName(
            modifier = Modifier
                .padding(start = spacing.extraSmall, top = spacing.extraSmall),
            name = advisory.firstName ?: "",
            profileImageLink = URLConstants.S3_IMAGE_BASE_URL + advisory.profileImage,
            textStyle = MaterialTheme.typography.caption,
            textColor = Color.LightGray,
            imageSize = 24.dp
        )

        val imagePath = URLConstants.S3_IMAGE_BASE_URL +
                advisory.photos?.elementAtOrNull(0)
                    ?.fileName.toString()
        Box(
            modifier = Modifier
                .width(180.dp)
                .height(200.dp)
                .padding(horizontal = spacing.extraSmall, vertical = spacing.small)
        ) {
            RemoteImage(
                modifier = Modifier.fillMaxSize(),
                imageLink = imagePath,
                error = R.mipmap.img_default_pop,
                contentScale = ContentScale.FillBounds
            )

            Chip(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = spacing.small, bottom = spacing.small),
                text = DateUtil().getDateMonthYearFormat(advisory.createdTimestamp),
                textPadding = spacing.tiny,
                textStyle = MaterialTheme.typography.overline,
                backgroundColor = Color.Black.copy(alpha = .3f)
            )

            ChipIcon(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                iconId = R.drawable.ic_forword_arrow_round,
                backgroundColor = Color.White,
                iconColor = Color.Unspecified,
                size = 24.dp,
                onClick = { onAdvisoryClicked() }
            )
        }

        Text(
            text = getAdvisoryTagName(
                advisory.advisoryTag ?: "",
                advisory.advisoryTagName ?: ""
            ),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(start = spacing.extraSmall, top = spacing.extraSmall)
        )


        RoundedShapeButton(
            modifier = Modifier
                .padding(spacing.medium)
                .fillMaxWidth(),
            text = str(id = R.string.view_advisory),
            textPadding = spacing.tiny,
            textStyle = MaterialTheme.typography.caption,
            onClick = onAdvisoryClicked
        )

    }
}