package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerDropDownMenu
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SelectableChip
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FertilizerCalculatorViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun CropInputsPage(
    viewModel: FertilizerCalculatorViewModel,
    onPageChange: (Int) -> Unit,
) {
    val spacing = LocalSpacing.current
    val focusManager = LocalFocusManager.current
    BackHandler(viewModel.hasFocus.value) {
        focusManager.clearFocus()
    }
    val cropNames =
        viewModel.fertilizerFruitDetails.distinctBy { it.cropName ?: "" }.map { it.cropName ?: "" }
    val ages by remember {
        derivedStateOf {
            viewModel.fertilizerFruitDetails.filter { it.cropName == viewModel.fruitCropNameSelected.value }
                .distinctBy { it.planting }.sortedBy { it.planting }
        }
    }

    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        Text(
            text = str(id = R.string.add_your_crop_inputs),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = spacing.medium)
        )

        if (viewModel.isFruitCropSelected) {
            FarmerDropDownMenu(
                modifier = Modifier.wrapContentWidth(),
                title = str(id = R.string.select_crop),
                textStyle = MaterialTheme.typography.body2,
                expanded = viewModel.fruitCropExpanded,
                selected = viewModel.fruitCropNameSelected,
                selectedItemColor = Color.Limeade,
                backgroundColor = Color.LightGray.copy(alpha = .2f),
                arrowColor = Color.Limeade,
                backgroundScale = spacing.medium,
                options = cropNames,
                onSelectOption = { _: Int, _: String ->
                    viewModel.selectedFruitCrop.postValue(null)
                    viewModel.ageOfCropSelected.postValue("")
                }
            )

            FarmerDropDownMenu(
                modifier = Modifier.wrapContentWidth(),
                title = str(id = R.string.age_of_crop),
                textStyle = MaterialTheme.typography.body2,
                expanded = viewModel.ageOfCropExpanded,
                selected = viewModel.ageOfCropSelected,
                selectedItemColor = Color.Limeade,
                backgroundColor = Color.LightGray.copy(alpha = .2f),
                arrowColor = Color.Limeade,
                backgroundScale = spacing.medium,
                options = ages.map {
                    if (it.planting == 0.0) str(id = R.string.planting)
                    else "${it.planting?.toInt()?.toString()} ${str(id = R.string.year)}"
                },
                onSelectOption = { pos, _ -> viewModel.selectedFruitCrop.postValue(ages[pos]) }
            )
        } else {
            Text(text = str(id = R.string.crop), style = MaterialTheme.typography.body2)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectableChip(
                    text = viewModel.selectedCrop.value?.cropName ?: str(id = R.string.crop),
                    textStyle = MaterialTheme.typography.body2,
                    isSelected = false,
                    unselectedBackgroundColor = Color.Limeade,
                    unselectedContentColor = Color.White
                )

                Text(
                    text = str(id = R.string.change),
                    color = Color.Limeade,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onPageChange(Page.SELECT_CROPS) }
                )
            }
        }

        FarmerTextField(
            modifier = Modifier.fillMaxWidth(.8f),
            text = { viewModel.cropArea.value },
            onValueChange = { viewModel.cropArea.postValue(it) },
            placeholderText = str(id = R.string.crop_area),
            textStyle = MaterialTheme.typography.body2,
            textColor = Color.Limeade,
            trailingIcon = { FertilizerTag(text = str(id = R.string.in_hectare)) },
            hasFocus = viewModel.hasFocus,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { onPageChange(Page.FERTILIZER_SOURCE_SELECTION) })
        )
    }
}