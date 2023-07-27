package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.MoreVertIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.FertilizerGeneratedReport
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SavedFertilizerRecommendationItem(
    fertilizerGeneratedReport: FertilizerGeneratedReport?,
    cropName: String?,
    goToFertilizerRecommendation: () -> Unit
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.bottomRoundedShape
    val extraSmallText = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 9.sp,
        letterSpacing = 0.sp
    )
    val finalReport = fertilizerGeneratedReport?.getFinalReport()

    Column(
        modifier = Modifier
            .padding(spacing.extraSmall)
            .width(160.dp)
            .border(width = .5.dp, color = Color.Limeade, shape = shape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {

            val image = URLConstants.S3_IMAGE_BASE_URL +
                    DataHolder.getInstance().cropMasterMap[fertilizerGeneratedReport?.cropId]
                        ?.photos?.firstOrNull()?.fileName.toString()

            RemoteImage(
                modifier = Modifier.fillMaxSize(),
                imageLink = image,
                contentScale = ContentScale.FillBounds,
                error = R.mipmap.img_default_pop
            )

            Chip(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(spacing.extraSmall),
                onClick = { },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Limeade
                )
            ) {
                Text(
                    text = cropName
                        ?: fertilizerGeneratedReport?.getFinalReport()?.cropName ?: "",
                    style = MaterialTheme.typography.overline
                )
            }

            Text(
                text = DateUtil.convertDateFormat(
                    fertilizerGeneratedReport?.createdTimestamp,
                    DateUtil.SERVER_DATE_FORMAT,
                    DateUtil.FERTILIZER_DATE_FORMAT
                ),
                style = MaterialTheme.typography.overline,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(spacing.small)
            )

            MoreVertIcon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(spacing.small),
                iconColor = Color.White,
                iconSize = 16.dp,
                onMoreOptionsClicked = {}
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = str(id = R.string.fertilizer),
                style = extraSmallText,
                color = Color.Limeade,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .padding(spacing.extraSmall)
            )

            Text(
                text = str(id = R.string.quantity),
                style = extraSmallText,
                color = Color.Limeade,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(.6f)
            )

            Text(
                text = str(id = R.string.unit),
                style = extraSmallText,
                color = Color.Limeade,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = spacing.extraSmall)
            )
        }

        Divider(color = Color.Limeade, thickness = .5.dp)

        FertilizerTableRow(
            fertilizer = finalReport?.fym?.fertilizerName ?: "",
            quantity = "${finalReport?.fym?.quantity}",
            unit = finalReport?.fym?.unit ?: "",
            textStyle = extraSmallText
        )
        FertilizerTableRow(
            fertilizer = finalReport?.vermicompost?.fertilizerName ?: "",
            quantity = "${finalReport?.vermicompost?.quantity}",
            unit = finalReport?.vermicompost?.unit ?: "",
            textStyle = extraSmallText
        )
        FertilizerTableRow(
            fertilizer = finalReport?.npk?.fertilizerName ?: "",
            quantity = "${finalReport?.npk?.quantity}",
            unit = finalReport?.npk?.unit ?: "",
            textStyle = extraSmallText
        )
        FertilizerTableRow(
            fertilizer = finalReport?.nitrogenous?.fertilizerName ?: "",
            quantity = "${finalReport?.nitrogenous?.quantity}",
            unit = finalReport?.nitrogenous?.unit ?: "",
            textStyle = extraSmallText
        )
        FertilizerTableRow(
            fertilizer = finalReport?.potassium?.fertilizerName ?: "",
            quantity = "${finalReport?.potassium?.quantity}",
            unit = finalReport?.potassium?.unit ?: "",
            textStyle = extraSmallText
        )
        FertilizerTableRow(
            fertilizer = finalReport?.zinc?.fertilizerName ?: "",
            quantity = "${finalReport?.zinc?.quantity}",
            unit = finalReport?.zinc?.unit ?: "",
            textStyle = extraSmallText
        )
        FertilizerTableRow(
            fertilizer = finalReport?.phosphorus?.fertilizerName ?: "",
            quantity = "${finalReport?.phosphorus?.quantity}",
            unit = finalReport?.phosphorus?.unit ?: "",
            textStyle = extraSmallText
        )
        FertilizerTableRow(
            fertilizer = finalReport?.boron?.fertilizerName ?: "",
            quantity = "${finalReport?.boron?.quantity}",
            unit = finalReport?.boron?.unit ?: "",
            textStyle = extraSmallText
        )

        Divider(color = Color.Limeade, thickness = .5.dp)

        Text(
            text = str(id = R.string.view_details),
            color = Color.Limeade,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing.extraSmall)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    onClick = goToFertilizerRecommendation
                )
        )
    }
}