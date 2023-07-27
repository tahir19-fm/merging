package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components.AddCropCalendarBottomSheet
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components.CropCircleImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components.MyComposeCalendar
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.*
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.utils.asString
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddCropCalendarFragmentScreen(
    viewModel: CropCalendarViewModel,
    saveCropCalendar: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val sheetShape = LocalShapes.current.topRoundedMediumShape
    var showCalendar by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val onSelect: (CropMaster) -> Unit = {
        viewModel.selectedCrop.postValue(it)
        scope.launch { sheetState.hide() }
        viewModel.validate()
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            AddCropCalendarBottomSheet(
                viewModel = viewModel,
                onSelect = onSelect,
                onDone = { scope.launch { sheetState.hide() } }
            )
        },
        sheetShape = sheetShape,
        scrimColor = Color.Cameron.copy(alpha = .2f),
        sheetBackgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.small)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "${str(id = R.string.create)} ${str(id = R.string.your)} ${str(id = R.string.crop_calendar)}",
                fontWeight = FontWeight.Bold,
                color = Color.Cameron,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = spacing.medium)
            )

            Text(
                text = str(id = R.string.select_your_crops),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = spacing.medium, bottom = spacing.small)
            )

            if (viewModel.selectedCrop.value != null) {
                CropCircleImage(crop = viewModel.selectedCrop.value!!, isSelected = true)
            } else {
                LazyRow(modifier = Modifier.padding(spacing.small)) {
                    items(viewModel.userCrops) { crop ->
                        CropCircleImage(crop = crop, isSelected = false, onSelect = onSelect)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(spacing.small)
                    .clickable { scope.launch { sheetState.show() } },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = str(id = R.string.view_other_crops),
                    style = MaterialTheme.typography.caption,
                    color = Color.Cameron
                )

                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.Cameron,
                    modifier = Modifier.padding(start = spacing.small)
                )
            }

            Row(
                modifier = Modifier
                    .padding(spacing.small),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_crop_calendar_new),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = str(id = R.string.select_crop_sowing_date),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = spacing.small)
                )
            }

            Row(
                modifier = Modifier
                    .padding(spacing.small)
                    .border(width = .7.dp, color = Color.Cameron, shape = shape)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource(),
                        onClick = { showCalendar = true }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = viewModel.date.value?.asString("dd") ?: "dd",
                    style = MaterialTheme.typography.caption,
                    color = Color.Cameron,
                    modifier = Modifier.padding(start = spacing.small)
                )

                Divider(
                    modifier = Modifier
                        .padding(spacing.small)
                        .height(16.dp)
                        .width(1.dp),
                    color = Color.Cameron
                )

                Text(
                    text = viewModel.date.value?.asString("MM") ?: "mm",
                    style = MaterialTheme.typography.caption,
                    color = Color.Cameron
                )

                Divider(
                    modifier = Modifier
                        .padding(spacing.small)
                        .height(16.dp)
                        .width(1.dp),
                    color = Color.Cameron
                )

                Text(
                    text = viewModel.date.value?.asString("yyyy") ?: "yyyy",
                    style = MaterialTheme.typography.caption,
                    color = Color.Cameron,
                    modifier = Modifier.padding(end = spacing.small)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.small),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier.wrapContentWidth(),
                    onClick = saveCropCalendar,
                    enabled = viewModel.buttonEnabled.value,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Cameron,
                        contentColor = Color.White,
                        disabledBackgroundColor = Color.Citron
                    )
                ) {
                    Text(
                        text = str(id = R.string.create_crop_calendar),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }


    if (showCalendar) {
        MyComposeCalendar(
            startDate = viewModel.date.value ?: LocalDate.now(),
            onDone = {
                viewModel.date.postValue(it)
                viewModel.validate()
                showCalendar = false
            },
            onDismiss = { showCalendar = false }
        )
    }

}