package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_advisory

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.FarmSantaTag
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.BlackOlive
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import com.taomish.app.android.farmsanta.farmer.utils.NamesAndFormatsUtil


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ViewAdvisoryFragmentScreen(advisory: CropAdvisory?) {
    if (advisory == null) {
        Box(modifier = Modifier.padding(1.dp))
        return
    }
    val spacing = LocalSpacing.current
    val pagerState = rememberPagerState()
    val context = LocalContext.current
    var plantname = NamesAndFormatsUtil.getCropName(advisory.crop)
    var grothStage = advisory.growthStage?.let { NamesAndFormatsUtil.getUUIDName(it)}
    Log.d("Crop Condition","Crop Name:"+advisory.cropName.toString())
    Log.d("Crop Condition","Growth Stage:"+advisory.growthStageName.toString())
    if(plantname == "N/A" && !advisory.growthStageName.isNullOrEmpty())
    {
        plantname = advisory.growthStageName
    }
    if(grothStage.equals("N/A") && !advisory.growthStageName.isNullOrEmpty())
    {
        grothStage = advisory.growthStageName
    }
    Log.d("Crop Condition","Crop Name:"+plantname)
    Log.d("Crop Condition","Growth Stage:"+grothStage)

    Column {
        CommonTopBar(
            activity = context as AppCompatActivity,
            isAddRequired = false,
            title = str(id = R.string.crop_advisory),
            addClick = {}
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing.medium, horizontal = spacing.small)
        ) {
            item {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {

                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = spacing.small)
                            .clip(RectangleShape),
                        state = pagerState,
                        count = advisory.photos?.size ?: 1
                    ) { page ->
                        val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                                advisory.photos
                                    ?.elementAtOrNull(page)
                                    ?.fileName.toString()

                        RemoteImage(
                            modifier = Modifier.fillMaxSize(),
                            imageLink = imageLink,
                            contentScale = ContentScale.FillBounds,
                            error = R.mipmap.img_default_pop
                        )
                    }


                    ProfileImageWithName(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = spacing.small, start = spacing.small)
                            .background(color = Color.Black.copy(alpha = .5F), shape = CircleShape)
                            .padding(vertical = spacing.extraSmall, horizontal = spacing.small),
                        name = "${advisory.firstName} ${advisory.lastName}",
                        profileImageLink = URLConstants.S3_IMAGE_BASE_URL + advisory.profileImage,
                        imageSize = 32.dp,
                        textStyle = MaterialTheme.typography.caption,
                        textColor = Color.White
                    )
                    Log.d("ProfileImage",""+URLConstants.S3_IMAGE_BASE_URL + advisory.profileImage)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DotsIndicator(
                        totalDots = advisory.photos?.size ?: 0,
                        selectedIndex = pagerState.currentPage,
                        unSelectedColor = Color.Gray
                    )
                }

                Text(
                    text = "${str(id = R.string.advisory)}: ${
                        NamesAndFormatsUtil.getAdvisoryTagName(
                            advisory.advisoryTag ?: "",
                            advisory.advisoryTagName ?: ""
                        )
                    }",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(spacing.small)
                )

                Row(
                    modifier = Modifier
                        .padding(vertical = spacing.small)
                ) {
                    Chip(
                        text = "${str(id = R.string.plant)}: ${plantname}",
                        textStyle = MaterialTheme.typography.overline,
                        backgroundColor = Color.Cameron
                    )

                    Chip(
                        modifier = Modifier.padding(start = spacing.small),
                        text = "${str(id = R.string.growth_stage)}: ${grothStage}",
                        textStyle = MaterialTheme.typography.overline,
                        backgroundColor = Color.Cameron
                    )
                }


                Text(
                    text = advisory.advisory.ifEmpty { str(id = R.string.description_not_available) },
                    style = MaterialTheme.typography.body2,
                    color = Color.BlackOlive,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth()
                )


                FarmSantaTag(
                    modifier = Modifier.padding(spacing.small),
                    title = str(id = R.string.production_recommendation)
                )

                AdditionalField(
                    description = advisory.productRecommendation,
                    backgroundColor = Color.Transparent
                )

                Spacer(modifier = Modifier.height(spacing.medium))

                Text(
                    text = "${str(id = R.string.uploaded_on)} : ${
                        DateUtil().getDateMonthYearFormat(
                            advisory.createdTimestamp
                        )
                    }",
                    color = Color.Cameron,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }


}