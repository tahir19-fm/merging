package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_recommendation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SelectableChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpanStyle


@Composable
fun FertilizerRecommendationBottomSheet(selectedCrop: String?, onCloseClicked: () -> Unit) {
    val spacing = LocalSpacing.current
    val style = LocalSpanStyle.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 96.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SelectableChip(
                modifier = Modifier.padding(spacing.extraSmall),
                text = "${str(id = R.string.crop)}: ${selectedCrop ?: " Mango "}",
                isSelected = false,
                unselectedBackgroundColor = Color.Limeade,
                unselectedContentColor = Color.White
            )

            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                tint = Color.Limeade,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onCloseClicked)
            )
        }

        Text(
            text = buildAnnotatedString {
                pushStyle(style.body2.copy(color = Color.Limeade))
                append("+${str(id = R.string.add_image)}")
                pop()
                pushStyle(style.overline.copy(color = Color.LightGray))
                append("  ${str(id = R.string.optional)}")
            },
            modifier = Modifier.padding(spacing.small)
        )

        SelectFertilizerImages(list = listOf(), onAddClicked = { }, onCloseClicked = { })

        Row(
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = str(id = R.string.select_from_gallery),
                style = MaterialTheme.typography.body1
            )

            Text(
                text = str(id = R.string.view_all),
                style = MaterialTheme.typography.caption,
                color = Color.Limeade
            )
        }

        LazyRow(modifier = Modifier.padding(spacing.small)) {
            items(10) {
                RemoteImage(
                    modifier = Modifier
                        .size(96.dp)
                        .padding(spacing.extraSmall),
                    imageLink = "",
                    contentScale = ContentScale.FillBounds,
                    error = R.mipmap.img_default_pop
                )
            }
        }

        Divider(color = Color.LightGray, thickness = .5.dp)
    }
}