package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CropCircleImageBottomTitle
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun CropCalendarsRow(viewModel: CropCalendarViewModel, getCropCalendarsForCrop: () -> Unit) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(.5f)
                .background(color = Color.Cameron)
        )

        LazyRow(
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            itemsIndexed(viewModel.cropCalendars) { _, cropCalendar ->
                val cropMaster = viewModel.allCrops.find { it.uuid == cropCalendar.cropId }
                val image = URLConstants.S3_IMAGE_BASE_URL +
                        cropMaster?.photos?.elementAtOrNull(0)
                            ?.fileName.toString()
                CropCircleImageBottomTitle(
                    getImageUrl = { image },
                    getCropName = { cropMaster?.cropName },
                    isSelected = viewModel.selectedCropCalendar.value?.cropId == cropCalendar.cropId,
                    showClose = { false },
                    onClick = {
                        if (cropCalendar.cropId != viewModel.selectedCropCalendar.value?.cropId) {
                            Log.d(
                                "CropCalendarFragment",
                                "crop calendar is clicked : ${cropCalendar.id}"
                            )
                            viewModel.selectedCropCalendar.postValue(cropCalendar)
                            getCropCalendarsForCrop()
                        }
                    }
                )
            }
        }
    }
}