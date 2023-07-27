package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farmer


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ImageSelectMethodDialog
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farmer.components.AddFarmerFarmerInformationFields
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components.BasicInformationForm
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants


@Composable
fun AddFarmerFragmentScreen(
    viewModel: SignUpAndEditProfileViewModel,
    openDatePickerDialog: () -> Unit,
    goToAddCrops: () -> Unit,
    goToAddOrEditFarmLocation: (Boolean) -> Unit,
    onSelectCamera: () -> Unit,
    onSelectGallery: () -> Unit,
    onDone: () -> Unit
) {
    val spacing = LocalSpacing.current
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var showDialog by remember { mutableStateOf(false) }
    BackHandler(hasFocus.value) { focusManager.clearFocus() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier
                    .size(136.dp)
            ) {

                if (viewModel.profileBitmap.value != null) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        bitmap = viewModel.profileBitmap.value!!.asImageBitmap(),
                        contentDescription = null
                    )
                } else {
                    RemoteImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        imageLink = URLConstants.S3_IMAGE_BASE_URL + viewModel.selectedFarmer.value?.profileImage
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_scouting_add_camera),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.BottomEnd)
                        .clickable { showDialog = true }
                )
            }

            Text(
                text = str(id = R.string.add_image),
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = spacing.small)
            )
        }
        item {
            BasicInformationForm(
                viewModel = viewModel,
                hasFocus = hasFocus,
                openDataPickerDialog = openDatePickerDialog
            )

            AddFarmerFarmerInformationFields(
                viewModel = viewModel,
                hasFocus = hasFocus,
                onMyCropsEditClicked = goToAddCrops,
                onAddOrEditFarmClicked = goToAddOrEditFarmLocation
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedShapeButton(
                    modifier = Modifier.fillMaxWidth(.7f),
                    text = str(id = R.string.done),
                    textStyle = MaterialTheme.typography.body2,
                    onClick = onDone
                )
            }
        }
    }

    if (showDialog) {
        ImageSelectMethodDialog(
            onSelectCamera = {
                onSelectCamera()
                showDialog = false
            },
            onSelectGallery = {
                onSelectGallery()
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}
