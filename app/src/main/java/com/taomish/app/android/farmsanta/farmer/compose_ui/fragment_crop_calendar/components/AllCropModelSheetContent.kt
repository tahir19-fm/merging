package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calender.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.CropCalendarViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crop_calendar.components.MyComposeCalendar
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Citron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.asString
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import java.time.LocalDate
import kotlin.reflect.KFunction3

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllCropModalSheetContent(
    viewModel: CropCalendarViewModel,
    updateCropCalendar: KFunction3<String, Int, Int, Unit>,
    cropId:String, stageId:Int, id:Int,
    onCloseClicked: () -> Unit,
    dd:String, mm:String, yyyy:String
) {
    val spacing = LocalSpacing.current
    val shape =  LocalShapes.current.smallShape
    var showCalendar by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = str(id = R.string.edit),
            fontWeight = FontWeight.Bold,
            color = Color.Cameron,
            modifier = Modifier.padding(start = spacing.medium, top = spacing.extraLarge, bottom = spacing.medium)
        )
        Box(
            modifier = Modifier.padding(start = spacing.medium)
        ) {
            Row {

                Image(
                    painter = painterResource(id = R.drawable.ic_crop_calendar_new),
                    contentDescription = null,
                    modifier = Modifier.size(23.dp)
                )

                Text(
                    text = "Edit Crop Showing Date",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = spacing.small)
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(spacing.medium)
                .border(width = .7.dp, color = Color.Cameron, shape = shape)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    onClick = { showCalendar = true }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = viewModel.date.value?.asString("dd") ?: dd,
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
                text = viewModel.date.value?.asString("MM") ?: mm,
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
                text = viewModel.date.value?.asString("yyyy") ?: yyyy,
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
                onClick = {viewModel.setUpdateButtonEnabled()
                    updateCropCalendar(cropId,stageId,id)
                          onCloseClicked()},
                enabled = viewModel.updateButtonEnabled.value,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Cameron,
                    contentColor = Color.White,
                    disabledBackgroundColor = Color.Citron
                )
            ) {
                Text(
                    text = "Update Calender",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
    if (showCalendar) {
        MyComposeCalendar(
            startDate = viewModel.date.value ?: LocalDate.now(),
            onDone = {
                viewModel.date.postValue(it)
                viewModel.check()
                showCalendar = false
            },
            onDismiss = { showCalendar = false }
        )
    }
}