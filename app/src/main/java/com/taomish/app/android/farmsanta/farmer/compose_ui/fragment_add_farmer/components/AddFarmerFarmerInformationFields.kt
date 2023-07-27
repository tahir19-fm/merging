package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_farmer.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components.InAcre
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components.EditMyCrops
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components.TextFieldWithLabel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun AddFarmerFarmerInformationFields(
    viewModel: SignUpAndEditProfileViewModel,
    hasFocus: MutableState<Boolean>,
    onMyCropsEditClicked: () -> Unit,
    onAddOrEditFarmClicked: (Boolean) -> Unit,
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
            onAddClicked = onMyCropsEditClicked,
        )

        AddFarmerLands(
            lands = viewModel.lands,
            onAddFarmClicked = {
                viewModel.isEditingFarm = false
                viewModel.selectedLandIndex = -1
                onAddOrEditFarmClicked(false)
            },
            goToEditFarm = {
                viewModel.isEditingFarm = true
                viewModel.selectedLandIndex = it
                onAddOrEditFarmClicked(true)
            }
        )

    }
}