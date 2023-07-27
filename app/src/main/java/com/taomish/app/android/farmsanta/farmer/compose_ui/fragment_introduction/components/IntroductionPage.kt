package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_introduction.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_introduction.IntroData
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LightSilver
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun IntroductionPage(
    modifier: Modifier = Modifier,
    introData: IntroData,
    currentPage: Int
) {

    val spacing = LocalSpacing.current
    val icons = listOf(
        R.drawable.ic_farm_talk_new,
        R.drawable.ic_ask_queries_new,
        R.drawable.ic_advisory_green_new,
        R.drawable.ic_pop_green_new,
        R.drawable.ic_calculate_fertilizer
    )

    Box(
        modifier = modifier
            .background(color = Color.Cameron)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mascot_introduction),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .width(240.dp)
                        .fillMaxHeight(.8f)
                        .padding(12.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                )

                if (currentPage > 1) {
                    IntroductionIcon(
                        modifier = Modifier.align(Alignment.TopCenter),
                        iconId = icons[2]
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(
                            top = 32.dp,
                            start = spacing.extraLarge + spacing.medium,
                            end = spacing.extraLarge + spacing.medium,
                            bottom = spacing.small
                        )
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    horizontalArrangement = if (currentPage < 3) Arrangement.End else Arrangement.SpaceBetween
                ) {
                    if (currentPage >= 3) {
                        IntroductionIcon(iconId = icons[3])
                    }
                    if (currentPage >= 1) {
                        IntroductionIcon(iconId = icons[1])
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(spacing.medium)
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    horizontalArrangement = if (currentPage < 4) Arrangement.End else Arrangement.SpaceBetween
                ) {
                    if (currentPage >= 4) {
                        IntroductionIcon(iconId = icons[4])
                    }
                    if (currentPage >= 0) {
                        IntroductionIcon(iconId = icons[0])
                    }
                }
            }

            Text(
                modifier = Modifier.padding(top = spacing.large),
                text = introData.title,
                color = Color.White,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(spacing.medium),
                text = introData.subTitle,
                color = Color.LightSilver,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,

                )
        }

    }
}