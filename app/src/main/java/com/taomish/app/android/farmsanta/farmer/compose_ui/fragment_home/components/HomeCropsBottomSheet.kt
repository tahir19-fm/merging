package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.components.FilterOptions
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OrangePeel
import com.taomish.app.android.farmsanta.farmer.models.view_model.HomeViewModel
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@Composable
fun HomeCropsBottomSheet(
    viewModel: HomeViewModel,
    closeSheet: () -> Unit
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                FilterOptions(
                    modifier = Modifier.padding(bottom = spacing.extraLarge),
                    title = str(id = R.string.select_crops),
                    options = viewModel.crops,
                    getText = { it.cropName },
                    selectedOptions = viewModel.myCrops,
                    itemBackgroundColor = Color.OrangePeel,
                    itemContentColor = Color.White,
                    onSelect = {
                        if (viewModel.myCrops.size < 3) {
                            if (viewModel.myCrops.find { crop -> it == crop } == null) {
                                viewModel.myCrops.add(it)
                            } else {
                                context.showToast(R.string.already_added_crop)
                            }
                        } else {
                            context.showToast(R.string.maximum_crops_msg)
                        }
                    },
                    onDelete = { viewModel.myCrops.remove(it) }
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(.5f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                onClick = closeSheet
            ) {
                Text(
                    text = str(id = R.string.cancel),
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = spacing.small)
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cameron),
                onClick = { viewModel.applyCropChanges(context, onComplete = closeSheet) }
            ) {
                Text(
                    text = str(id = R.string.apply),
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = spacing.small)
                )
            }
        }
    }
}