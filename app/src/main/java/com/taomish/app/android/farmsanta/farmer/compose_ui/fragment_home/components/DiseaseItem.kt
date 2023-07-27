package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DiseaseItem(
    modifier: Modifier = Modifier,
    pair: Pair<String, String>,
    onDiseaseClicked: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .padding(horizontal = spacing.extraSmall, vertical = spacing.small)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onDiseaseClicked
            )
    ) {
        RemoteImage(
            modifier = Modifier.fillMaxSize(),
            imageLink = URLConstants.S3_IMAGE_BASE_URL + pair.second,
            error = R.mipmap.img_default_pop,
            contentScale = ContentScale.FillBounds
        )

        val colors = ChipDefaults.chipColors(
            backgroundColor = Color.Black.copy(alpha = .4f),
            contentColor = Color.White
        )

        Chip(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = spacing.small)
                .fillMaxWidth(.8f),
            onClick = onDiseaseClicked,
            colors = colors
        ) {
            Text(
                text = pair.first,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.fillMaxWidth(.85f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_forword_arrow_round),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = spacing.extraSmall)
                    .size(24.dp)
                    .fillMaxWidth()
            )
        }
    }
}