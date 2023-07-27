package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_other_farmer_pops.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Chip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ChipIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import com.taomish.app.android.farmsanta.farmer.utils.NamesAndFormatsUtil


@Composable
fun OtherFarmerPopItem(popDto: PopDto) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    Box(
        modifier = Modifier
            .padding(vertical = spacing.small)
            .height(200.dp)
            .clip(shape)
    ) {
        val imagePath =
            URLConstants.S3_IMAGE_BASE_URL + popDto.photos
                ?.elementAtOrNull(0)
                ?.fileName.toString()

        RemoteImage(
            modifier = Modifier.fillMaxSize(),
            imageLink = imagePath,
            error = R.mipmap.img_default_pop,
            contentScale = ContentScale.FillBounds
        )

        Chip(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = spacing.small, top = spacing.small),
            text = DateUtil().getDateMonthYearFormat(popDto.createdTimestamp),
            textPadding = spacing.tiny,
            textStyle = MaterialTheme.typography.overline,
            backgroundColor = Color.Black.copy(alpha = .3f)
        )

        Chip(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = spacing.small),
            text = NamesAndFormatsUtil.getCropName(popDto.crop),
            textPadding = spacing.extraSmall,
            textStyle = MaterialTheme.typography.caption,
            backgroundColor = Color.Black.copy(alpha = .4f),
            trailingIcon = {
                ChipIcon(
                    iconId = R.drawable.ic_forword_arrow_round,
                    backgroundColor = Color.White,
                    iconColor = Color.Unspecified,
                    size = 24.dp
                )
            },
            isClickable = true,
            onClick = { }
        )
    }
}