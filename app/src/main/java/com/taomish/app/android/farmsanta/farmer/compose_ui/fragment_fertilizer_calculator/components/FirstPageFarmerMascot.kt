package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FirstPageFarmerMascot(onCalculateClicked: () -> Unit) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
            .background(Color.Limeade)
    ) {
        CommonTopBar(
            activity = context as AppCompatActivity,
            isAddRequired = false,
            title = str(id = R.string.fertilizer_calculator),
            backgroundColor = Color.Limeade,
            tintColor = Color.White,
            addClick = {}
        )
        Text(
            text = str(id = R.string.fertilizer_screen_title),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = spacing.medium, start = spacing.medium)
                .fillMaxWidth(.75f)
                .wrapContentHeight()
        )

        Row(
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Chip(
                modifier = Modifier.padding(start = spacing.medium),
                onClick = onCalculateClicked,
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Limeade
                )
            ) {
                Text(
                    text = str(id = R.string.calculate_fertilizer),
                    style = MaterialTheme.typography.caption
                )
            }

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_fertilizer_calc_mascot),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.6f)
            )
        }
    }
}