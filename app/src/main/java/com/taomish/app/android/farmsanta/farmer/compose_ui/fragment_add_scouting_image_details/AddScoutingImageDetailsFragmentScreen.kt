package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_scouting_image_details

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FarmScoutingViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_scouting_image_details.components.SelectedImagesCard
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_scouting_image_details.components.SendButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Nyanza
import com.taomish.app.android.farmsanta.farmer.utils.add
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.saveFileInStorage

@Composable
fun AddScoutingImageDetailsFragmentScreen(
    viewModel: FarmScoutingViewModel,
    onGallery: () -> Unit,
    onDone: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                saveFileInStorage(
                    context = context,
                    uri = it
                )?.let { file -> viewModel.imageFiles.add(file.path) }
            }
        }
    )

    BackHandler(hasFocus.value) { focusManager.clearFocus() }
    Column(
        modifier = Modifier
            .background(color = Color.Nyanza)
    ) {
        CommonTopBar(
            activity = context as AppCompatActivity,
            title = stringResource(id = R.string.raise_a_query),
            isAddRequired = true,
            addClick = {},
            rightIcon = {
                SendButton(onSendClicked = onDone)
            }
        )

        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .padding(spacing.small)
                        .fillMaxSize()
                        .padding(spacing.small)
                ) {
                    SelectedImagesCard(
                        getImages = { viewModel.imageFiles.value },
                        onAddClicked = {
                            focusManager.clearFocus()
                            showDialog = true
                        }
                    ) {
                        viewModel.imageFiles.postValue(
                            viewModel.imageFiles.value.filterIndexed { i, _ -> i != it }
                        )
                    }

                    FarmerTextField(
                        modifier = Modifier
                            .padding(top = spacing.medium)
                            .fillMaxSize(),
                        backgroundColor = Color.White,
                        text = { viewModel.queryTitleText.value },
                        onValueChange = { viewModel.queryTitleText.postValue(it) },
                        placeholderText = str(id = R.string.add_query_title),
                        hasFocus = hasFocus,
                        shape = shape
                    )

                    FarmerDropDownMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = spacing.medium),
                        title = str(id = R.string.select_crop),
                        expanded = viewModel.cropExpanded,
                        selected = viewModel.crop,
                        options = viewModel.cropList,
                        backgroundScale = spacing.small,
                        onSelectOption = { index, _ ->
                            viewModel.selectedCropDto.postValue(viewModel.cropDtoList[index])
                        }
                    )

                    FarmerDropDownMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = spacing.medium),
                        title = str(id = R.string.select_plant_part),
                        expanded = viewModel.plantExpanded,
                        selected = viewModel.plantText,
                        options = viewModel.plantList,
                        backgroundScale = spacing.small,
                        onSelectOption = { index, _ ->
                            viewModel.selectedPlantDto.postValue(viewModel.plantPartDtoList[index])
                        }
                    )

                    FarmerDropDownMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = spacing.medium),
                        title = str(id = R.string.growth_stage),
                        expanded = viewModel.growthStageExpanded,
                        selected = viewModel.growthStageText,
                        options = viewModel.growthStageList,
                        backgroundScale = spacing.small,
                        onSelectOption = { index, _ ->
                            viewModel.selectedStageDto.postValue(viewModel.growthStageDtoList[index])
                        }
                    )


                    Text(
                        text = str(id = R.string.write_your_query),
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .padding(top = spacing.medium)
                            .fillMaxWidth(.7f)
                    )


                    FarmerTextField(
                        modifier = Modifier
                            .padding(top = spacing.small)
                            .fillMaxSize(),
                        backgroundColor = Color.White,
                        boxHeight = 120.dp,
                        text = { viewModel.queryText.value },
                        onValueChange = { viewModel.queryText.postValue(it) },
                        placeholderText = str(id = R.string.type_here),
                        hasFocus = hasFocus,
                        shape = shape,
                        maxLines = 5
                    )

                    /*Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = spacing.medium),
                        horizontalArrangement = Arrangement.End
                    ) {
                        SendButton(onSendClicked = onDone)
                    }*/
                }

            }
        }
    }


    if (showDialog) {
        ImageSelectMethodDialog(
            onSelectCamera = {
                viewModel.captureImage.postValue(true)
                showDialog = false
            },
            onSelectGallery = {
                if (ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable()) {
                    photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                } else onGallery()
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}