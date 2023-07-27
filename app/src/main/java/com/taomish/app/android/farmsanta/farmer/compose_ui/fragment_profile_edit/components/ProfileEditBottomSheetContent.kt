package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components.CropUI
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Crop
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileEditBottomSheetContent(
    viewModel: SignUpAndEditProfileViewModel,
    onDone: () -> Unit
) {
    val spacing = LocalSpacing.current
    val selected = remember { mutableStateOf(0) }
    val state = rememberPagerState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val tabs = listOf("Fruits", "Vegetables", "Cereals")
    val onSelect: (String) -> Unit = {
        if (viewModel.selectedCrops.find { crop -> it == crop } == null) {
            viewModel.selectedCrops.add(it)
        } else {
            context.showToast("You've already added this crop")
        }
    }
    val onDelete: (String) -> Unit = {
        viewModel.selectedCrops.remove(it)
    }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Add Crops",
                fontWeight = FontWeight.Bold,
                color = Color.Cameron
            )

            Text(
                text = "Done",
                fontWeight = FontWeight.Bold,
                color = Color.Cameron,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    onClick = onDone
                )
            )
        }

        val crop = Crop()
        LazyRow(modifier = Modifier.padding(spacing.small)) {
            if (viewModel.selectedCrops.isNotEmpty()) {
                items(viewModel.selectedCrops) { name ->
                    CropUI(crop = crop, name = name, onDelete = { onDelete(name) })
                }
            } else {
                item {
                    Text(
                        text = stringResource(id = R.string.has_no_crop),
                        color = Color.Cameron,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Select Crops",
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.clickable { }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "All Crops: ",
                color = Color.Cameron,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth(.2f)
            )

            Tabs(
                modifier = Modifier.fillMaxWidth(),
                selected = selected,
                tabs = tabs,
                state = state,
                scope = scope,
                textStyle = MaterialTheme.typography.overline
            )
        }

        FarmerPager(state = state, tabs = { tabs.size }) { page ->
            when (page) {
                0 -> {
                    Fruits(onSelect = onSelect)
                }
                1 -> {
                    Vegetables(onSelect = onSelect)
                }
                2 -> {
                    Cereals(onSelect = onSelect)
                }
            }
        }

    }
}