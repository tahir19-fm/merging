package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farm_location.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.utils.isNotEmpty
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import com.taomish.app.android.farmsanta.farmer.utils.toCrop


@Composable
fun AddLandLocationForm(
    viewModel: SignUpAndEditProfileViewModel,
    onPageChange: (Int) -> Unit,
    onAddFarmClicked: () -> Unit,
    onEditLocationClicked: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val sheetShape = LocalShapes.current.topRoundedMediumShape
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    BackHandler(hasFocus.value) { focusManager.clearFocus() }
    val validate: (Boolean) -> Color = {
        if (it) Color.Cameron.copy(alpha = .1f) else Color.LightGray.copy(alpha = .2f)
    }
    val onSelect: (CropMaster) -> Unit = { cropMaster ->
        val crop = cropMaster.toCrop()
        viewModel.landCrops.find { it.cropId == crop.cropId }.let {
            if (it == null) {
                viewModel.landCrops.add(crop)
            } else {
                context.showToast(R.string.already_added_crop)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.RiceFlower)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing.small, horizontal = spacing.medium)
                .background(color = Color.White, shape = CircleShape),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.padding(start = spacing.small)) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_profile_location),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = viewModel.farmLocation ?: str(id = R.string.select_location),
                    color = Color.Cameron,
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = spacing.extraSmall)
                        .fillMaxWidth(.72f)
                )
            }

            Text(
                text = str(id = R.string.edit),
                color = Color.White,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .background(color = Color.Cameron, shape = CircleShape)
                    .padding(vertical = spacing.extraSmall, horizontal = spacing.small)
                    .clickable(onClick = onEditLocationClicked)
            )
        }

        Column(
            modifier = Modifier
                .background(color = Color.White, shape = sheetShape),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .padding(spacing.small)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable(
                            onClick = { onPageChange(0) },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false)
                        ),
                    tint = Color.Cameron
                )
            }

            LabeledTextField(
                modifier = Modifier
                    .padding(spacing.small)
                    .fillMaxWidth(),
                label = str(id = R.string.farm_registration_number),
                text = { viewModel.registrationNo.value },
                onValueChange = { viewModel.registrationNo.postValue(it) },
                textStyle = MaterialTheme.typography.caption,
                hasFocus = hasFocus,
                minChars = 3,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
            )

            LabeledTextField(
                modifier = Modifier
                    .padding(spacing.small)
                    .fillMaxWidth(),
                label = str(id = R.string.name_this_farm_location),
                text = { viewModel.farmName.value },
                onValueChange = { viewModel.farmName.postValue(it) },
                textStyle = MaterialTheme.typography.caption,
                hasFocus = hasFocus,
                minChars = 3,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
            )

            FarmerTextField(
                modifier = Modifier
                    .padding(spacing.small)
                    .fillMaxWidth()
                    .background(color = validate(viewModel.landArea.isNotEmpty())),
                text = { viewModel.landArea.value },
                onValueChange = { viewModel.landArea.postValue(it) },
                placeholderText = stringResource(id = R.string.land_area),
                textStyle = MaterialTheme.typography.body2,
                hasFocus = hasFocus,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                trailingIcon = { Hectare(viewModel) }
            )


            FarmerDropDownMenu(
                modifier = Modifier
                    .padding(spacing.small)
                    .fillMaxWidth(),
                title = "${stringResource(id = R.string.water_source)} ",
                titleFontWeight = FontWeight.Bold,
                textStyle = MaterialTheme.typography.caption,
                expanded = viewModel.waterSourceDropDownExpanded,
                selected = viewModel.waterSource,
                options = viewModel.waterSources,
                backgroundColor = Color.LightGray.copy(alpha = .2f),
                backgroundScale = spacing.small,
                showTitle = false
            )

            LazyRow(
                modifier = Modifier
                    .padding(spacing.small)
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                items(viewModel.allSelectedCrops) { item ->
                    AddLandCrop(crop = item, onClick = onSelect)
                }
            }


            RoundedShapeButton(
                modifier = Modifier.padding(vertical = spacing.small),
                text = str(id = R.string.add_farm),
                textPadding = spacing.tiny,
                onClick = onAddFarmClicked
            )
        }
    }
}


@Composable
fun Hectare(viewModel: SignUpAndEditProfileViewModel) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = MutableInteractionSource()
        ) { viewModel.uOMDropDownExpanded.postValue(true) }
    ) {
        Box(
            modifier = Modifier
                .height(40.dp)
                .background(color = Color.Cameron, shape = RectangleShape)
                .padding(horizontal = spacing.small, vertical = spacing.extraSmall)
        ) {
            Text(
                text = viewModel.selectedUOM.value,
                color = Color.White,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        FarmerDropDownContent(
            modifier = Modifier.wrapContentWidth(),
            expanded = viewModel.uOMDropDownExpanded,
            selected = viewModel.selectedUOM,
            items = viewModel.landUOMs.map { it.name },
            onSelectOption = { index, option ->
                viewModel.selectedUOM.postValue(option)
                viewModel.selectedUOMDto.postValue(viewModel.landUOMs[index])
            }
        )
    }
}