package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_image.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil


@Composable
fun SolvedQueryItem(
    farmScouting: FarmScouting,
    onMoreOptionsClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    val maxLines = remember { mutableStateOf(3) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
            .background(color = Color.Cameron.copy(alpha = .1f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.extraSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ProfileImageWithName(
                    name = "Farmer_q2",
                    profileImageLink = "",
                    textStyle = MaterialTheme.typography.caption,
                    imageSize = 28.dp
                )

                Text(
                    text = DateUtil().getDateMonthYearFormat(
                        farmScouting.createdTimestamp ?: "N/A"
                    ),
                    color = Color.Gray,
                    style = MaterialTheme.typography.overline,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = spacing.large)
                )
            }

            MoreVertIcon { onMoreOptionsClicked() }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.extraSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MetaAndValueTextRow(
                metaText = "Crop",
                valueText = "Rice"
            )

            MetaAndValueTextRow(
                metaText = "Growth Stage",
                valueText = "1 month"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.extraSmall, bottom = spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imagePath = farmScouting.images.getOrNull(0)?.image ?: ""
            RemoteImage(
                modifier = Modifier
                    .fillMaxWidth(.35f)
                    .height(112.dp),
                imageLink = URLConstants.S3_IMAGE_BASE_URL + imagePath,
                error = R.mipmap.img_default_pop,
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacing.small),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                MetaAndValueTextRow(
                    modifier = Modifier.fillMaxWidth(),
                    metaText = "Plant Part Issue",
                    valueText = "Leaf"
                )

                Text(
                    text = "Query Solution",
                    color = Color.Cameron,
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = spacing.extraSmall)
                )

                Text(
                    text = farmScouting.caption.ifEmpty { "Description is not available" },
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            end = spacing.small,
                            bottom = spacing.extraSmall
                        ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = maxLines.value
                )

                ReadMoreText(descriptionLines = maxLines)

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_done),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )

                    Text(
                        text = "Query Solved",
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold,
                        color = Color.Cameron,
                        modifier = Modifier.padding(spacing.small)
                    )
                }
            }
        }
    }
}