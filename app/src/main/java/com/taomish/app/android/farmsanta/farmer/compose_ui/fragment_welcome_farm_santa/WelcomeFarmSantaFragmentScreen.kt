package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_welcome_farm_santa

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun WelcomeFarmSantaFragmentScreen() {
    val spacing = LocalSpacing.current
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = spacing.large)
                .fillMaxWidth()
                .height(140.dp),
            painter = painterResource(id = R.drawable.welcome_santa_helicopter),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Image(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = spacing.medium)
                .size(120.dp, 80.dp),
            painter = painterResource(id = R.drawable.ic_select_language_charts_svg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {

            Image(
                modifier = Modifier
                    .padding(start = spacing.medium)
                    .size(80.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_welcome_crop_vector),
                contentDescription = null
            )

            Image(
                modifier = Modifier.height(300.dp),
                painter = painterResource(id = R.drawable.welcome_screen_mascot),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}