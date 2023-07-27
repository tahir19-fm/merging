package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerDropDownContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun ProfileEditFarmInformationField(
    viewModel: SignUpAndEditProfileViewModel,
    hasFocus: MutableState<Boolean>,
    onMyCropsEditClicked: () -> Unit,
    goToFarmLocation: (Boolean) -> Unit,
) {
    val spacing = LocalSpacing.current
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {
        TextFieldWithLabel(
            modifier = Modifier.fillMaxWidth(.8f),
            text = { viewModel.farmSize.value },
            onValueChange = { viewModel.farmSize.postValue(it) },
            minChars = 0,
            label = str(id = R.string.farm_size),
            isMandatory = false,
            hasFocus = hasFocus,
            trailingIcon = { InAcre(viewModel) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "+ ${str(id = R.string.add_crops)}",
                fontWeight = FontWeight.Bold,
                color = Color.Cameron,
                style = MaterialTheme.typography.body2
            )

            Text(
                text = str(id = R.string.edit),
                fontWeight = FontWeight.Bold,
                color = Color.Cameron,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    onClick = onMyCropsEditClicked
                )
            )
        }

        EditMyCrops(
            allSelectedCrops = viewModel.allSelectedCrops,
            onAddClicked = onMyCropsEditClicked
        )

        ProfileEditMyLands(
            lands = viewModel.lands,
            onAddFarmClicked = {
                viewModel.isEditingFarm = false
                viewModel.selectedLandIndex = -1
                goToFarmLocation(false)
            },
            goToMapView = {
                viewModel.isEditingFarm = true
                viewModel.selectedLandIndex = it
                goToFarmLocation(true)
            }
        )

    }
}

@Composable
fun InAcre(viewModel: SignUpAndEditProfileViewModel) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = MutableInteractionSource()
        ) { viewModel.uOMDropDownExpanded.postValue(true) }
    ) {
        Text(
            text = viewModel.selectedUOM.value,
            color = Color.Cameron,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(end = spacing.extraSmall)
                .background(color = Color.Cameron.copy(alpha = .1f), shape = CircleShape)
                .padding(spacing.small)
        )

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