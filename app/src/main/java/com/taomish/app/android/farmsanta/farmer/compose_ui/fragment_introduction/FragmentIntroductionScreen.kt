package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_introduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.DotsIndicator
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_introduction.components.IntroductionPage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FragmentIntroductionScreen(
    onGetStarted: () -> Unit,
) {

    val spacing = LocalSpacing.current
    val style = LocalSpanStyle.current
    val pagerState = rememberPagerState()
    val data = getIntroData()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cameron),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(spacing.large))

        Image(
            painter = painterResource(id = R.drawable.app_new_logo),
            contentDescription = "Launcher",
            alignment = Alignment.Center,
            modifier = Modifier.size(40.dp)
        )


        Text(
            text = str(id = R.string.introduction_title),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(spacing.medium),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )

        RoundedShapeButton(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .padding(horizontal = spacing.medium),
            text = buildAnnotatedString {
                withStyle(style.caption) {
                    append(str(id = R.string.get_started))
                }

                withStyle(style.body1) {
                    append("   ${str(id = R.string.start_icon)}")
                }
            },
            backgroundColor = Color.Ecstasy,
            onClick = onGetStarted
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.9f)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    count = data.size,
                    itemSpacing = spacing.large,
                    verticalAlignment = Alignment.Top
                ) { page ->
                    IntroductionPage(
                        introData = data[page],
                        currentPage = currentPage
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                DotsIndicator(
                    totalDots = data.size,
                    selectedIndex = pagerState.currentPage,
                    selectedColor = Color.DarkLemonLime,
                )
            }
        }

        /*Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = spacing.large,
                        topEnd = spacing.large
                    ),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
          }*/
    }
}

@Composable
fun getIntroData(): MutableList<IntroData> {
    val introData = mutableListOf<IntroData>()
    introData.add(
        IntroData(
            title = str(id = R.string.farm_talk),
            str(id = R.string.farm_talk_sub_title)
        )
    )
    introData.add(
        IntroData(
            title = str(id = R.string.farm_queries_title),
            subTitle = str(id = R.string.farm_queries_sub_title)
        )
    )
    introData.add(
        IntroData(
            title = str(id = R.string.crop_advisory),
            subTitle = str(id = R.string.crop_advisory_sub_title)
        )
    )
    introData.add(
        IntroData(
            title = str(id = R.string.pop_title),
            subTitle = str(id = R.string.pop_sub_title)
        )
    )
    introData.add(
        IntroData(
            title = str(id = R.string.fertilizer_calculator),
            subTitle = str(id = R.string.fertilizer_calculator_sub_title)
        )
    )
    return introData
}

data class IntroData(
    val title: String,
    val subTitle: String,
)