package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.EmptyList
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto


@Composable
fun HarvestTabContent(
    pop: PopDto?,
    harvesting: String?,
    postHarvesting: String?,
    onZoomImage: (String) -> Unit,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    LazyColumn(modifier = Modifier.padding(bottom = spacing.large)) {
        item {
            if (!harvesting.isNullOrEmpty() || !postHarvesting.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(spacing.small)
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                            pop?.photos?.elementAtOrNull(0)
                                ?.fileName.toString()

                    RemoteImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(shape),
                        imageLink = imageLink,
                        error = R.mipmap.img_default_pop,
                        contentScale = ContentScale.FillBounds
                    )

                    Icon(
                        modifier = Modifier
                            .padding(end = spacing.small, bottom = spacing.medium)
                            .align(Alignment.BottomEnd)
                            .size(32.dp)
                            .background(color = Color.Black.copy(alpha = .5f), shape = CircleShape)
                            .padding(spacing.extraSmall)
                            .clip(CircleShape)
                            .clickable { onZoomImage(imageLink) },
                        painter = painterResource(id = R.drawable.ic_full_screen),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(spacing.medium))

                Text(
                    text = "${str(id = R.string.harvesting_methods)}: ",
                    color = Color.Cameron,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(spacing.small)
                )


                Text(
                    text = harvesting ?: "-",
                    color = Color.Gray,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(spacing.small)
                )

                Text(
                    text = "${str(id = R.string.post_harvesting_technologies)}: ",
                    color = Color.Cameron,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(spacing.small)
                )

                Text(
                    text = postHarvesting ?: "-",
                    color = Color.Gray,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(spacing.small)
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    EmptyList(text = str(id = R.string.nothing_to_show))
                }
            }
        }
    }
}