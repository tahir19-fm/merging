package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components

import android.system.Os.remove
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CropCircleImageBottomTitle
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.DarkLemonLime
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import kotlin.reflect.KFunction1


@Composable
fun CropCalendarsRow(viewModel: CropCalendarViewModel,
                     getCropCalendarsForCrop: () -> Unit,
                     initData: () -> Unit,
                     goToAddCropCalendarFragment: () -> Unit)
{

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
                val Id = cropCalendar.id
                CropCircleImageBottomTitle(
                    getImageUrl = { image },
                    getCropName = { cropMaster?.cropName },
                    getId = Id.toString(),
                    isSelected = viewModel.selectedCropCalendar.value?.cropId == cropCalendar.cropId,
                    showClose = { false },
                    initData = initData,
                    onCloseClicked = {viewModel.cropCalendars.remove(cropCalendar) }
                ) {
                    if (cropCalendar.cropId != viewModel.selectedCropCalendar.value?.cropId) {
                        Log.d(
                            "CropCalendarFragment",
                            "crop calendar is clicked : ${cropCalendar.id}"
                        )
                        viewModel.selectedCropCalendar.postValue(cropCalendar)
                        getCropCalendarsForCrop()
                    }
                }
            }
            item {
                AddCrop(goToAddCropCalendarFragment = goToAddCropCalendarFragment)
            }
        }
    }
}

@Composable
fun AddCrop(goToAddCropCalendarFragment: () -> Unit) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(spacing.extraSmall)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = {goToAddCropCalendarFragment() }
            )
    ) {

        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(spacing.extraSmall)
                .fillMaxSize()
                .padding(bottom = spacing.small),
            painter = painterResource(id = R.drawable.add_crop),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
        )
        Text(
            text = "+ADD",
            color = Color.White,
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.DarkLemonLime,
                    shape = CircleShape
                )
                .padding(horizontal = spacing.small, vertical = spacing.extraSmall)
        )
    }
}
