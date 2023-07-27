package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ReadMoreText
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpanStyle
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.Advisory
import com.taomish.app.android.farmsanta.farmer.utils.formatDetails


@OptIn(ExperimentalPagerApi::class)
@Composable
fun CropDisease(advisory: Advisory) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val context = LocalContext.current
    val style = LocalSpanStyle.current
    var maxLines by remember { mutableStateOf(3) }
    val pagerState = rememberPagerState()
    var isExpandable by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .background(color = Color.White, shape = shape),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {

            Column(
                modifier = Modifier
                    .padding(spacing.extraSmall)
                    .fillMaxWidth(.35f)
                    .height(140.dp)
            ) {
                HorizontalPager(
                    modifier = Modifier
                        .padding(bottom = spacing.extraSmall)
                        .fillMaxSize(),
                    state = pagerState,
                    count = advisory.images.size
                ) { page ->
                    RemoteImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(112.dp),
                        imageLink = URLConstants.S3_IMAGE_BASE_URL + advisory.images[page].imageId,
                        error = R.mipmap.img_default_pop,
                        contentScale = ContentScale.FillBounds
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacing.small, end = spacing.extraSmall),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = advisory.advisoryDetails?.localName ?: "N/A",
                    color = Color.Cameron,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing.extraSmall)
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style.caption) {
                            append(advisory.advisoryDetails.formatDetails(context).trimIndent())
                        }
                    },
                    maxLines = maxLines,
                    overflow = TextOverflow.Visible,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth()
                )

                ReadMoreText(
                    isExpandedable = isExpandable,
                    onReadMoreClicked = {
                        maxLines = if (maxLines == 3) Int.MAX_VALUE else 3
                        isExpandable = maxLines == 3
                    }
                )
            }
        }
    }
}