package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_select_farmer_location.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerDropDownMenu
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun FarmerLocationForm(
    viewModel: SignUpAndEditProfileViewModel,
    onAddLocationClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    BackHandler(hasFocus.value) { focusManager.clearFocus() }

    Column(
        modifier = Modifier
            .padding(
                top = spacing.large.plus(spacing.small),
                start = spacing.small,
                end = spacing.small,
                bottom = spacing.medium
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        FarmerTextField(
            modifier = Modifier
                .padding(top = spacing.medium)
                .fillMaxWidth()
                .background(
                    color = if (viewModel.address.value.length >= 5) Color.Cameron.copy(alpha = .1f)
                    else Color.LightGray.copy(alpha = .1f)
                ),
            text = { viewModel.address.value },
            onValueChange = { viewModel.address.postValue(it) },
            placeholderText = stringResource(id = R.string.address),
            textStyle = MaterialTheme.typography.body2,
            hasFocus = hasFocus
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacing.medium)
        ) {

            FarmerDropDownMenu(
                modifier = Modifier
                    .padding(end = spacing.small)
                    .fillMaxWidth(.5f),
                title = str(id = R.string.country_new),
                expanded = viewModel.territoryDropDownExpanded,
                selected = viewModel.territory,
                options = viewModel.territories,
                backgroundColor = Color.Cameron.copy(alpha = .1f),
                backgroundScale = spacing.small,
                showTitle = false,
                onSelectOption = { index, _ ->
                    viewModel.selectedTerritoryDto.postValue(viewModel.territoriesDto[index])
                    viewModel.selectedTerritories.clear()
                    viewModel.selectedTerritories.add(viewModel.territoriesDto[index].uuid)
                    viewModel.fetchRegionsByTerritory(context)
                }
            )

            FarmerDropDownMenu(
                modifier = Modifier
                    .padding(start = spacing.small)
                    .fillMaxWidth(),
                title = "${stringResource(id = R.string.state_new)} ",
                expanded = viewModel.regionDropDownExpanded,
                selected = viewModel.region,
                options = viewModel.regions,
                backgroundColor = Color.Cameron.copy(alpha = .1f),
                backgroundScale = spacing.small,
                showTitle = false,
                onSelectOption = { index, _ ->
                    viewModel.selectedRegionDto.postValue(viewModel.regionsDto[index])
                    viewModel.selectedRegions.clear()
                    viewModel.selectedRegions.add(viewModel.regionsDto[index].uuid)
                    viewModel.fetchCountiesByRegion(context)
                    viewModel.pinCode.postValue(viewModel.selectedRegionDto.value?.zipcode ?: "N/A")
                }
            )

        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacing.medium)
        ) {


            FarmerDropDownMenu(
                modifier = Modifier
                    .padding(end = spacing.small)
                    .fillMaxWidth(.5f),
                title = stringResource(id = R.string.district_new),
                expanded = viewModel.countyDropDownExpanded,
                selected = viewModel.county,
                options = viewModel.counties,
                backgroundColor = Color.Cameron.copy(alpha = .1f),
                backgroundScale = spacing.small,
                showTitle = false,
                onSelectOption = { index, _ ->
                    viewModel.selectedCountyDto.postValue(viewModel.countiesDto[index])
                    viewModel.fetchSubCountiesByCounty(context)
                }
            )

            FarmerTextField(
                modifier = Modifier
                    .padding(start = spacing.small)
                    .fillMaxWidth()
                    .background(
                        color = if (viewModel.pinCode.value.length >= 3)
                            Color.Cameron.copy(alpha = .1f)
                        else Color.LightGray.copy(alpha = .2f)
                    ),
                text = { viewModel.pinCode.value },
                onValueChange = { viewModel.pinCode.postValue(it) },
                enabled = false,
                boxHeight = 36.dp,
                placeholderText = stringResource(id = R.string.pin_code),
                textStyle = MaterialTheme.typography.body2,
                hasFocus = hasFocus,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }


        FarmerDropDownMenu(
            modifier = Modifier
                .padding(top = spacing.small, bottom = spacing.small)
                .fillMaxWidth(),
            title = "${stringResource(id = R.string.village)} ",
            titleFontWeight = FontWeight.Bold,
            textStyle = MaterialTheme.typography.body2,
            expanded = viewModel.subCountyDropDownExpanded,
            selected = viewModel.village,
            options = viewModel.subCounties,
            backgroundColor = Color.Cameron.copy(alpha = .1f),
            backgroundScale = spacing.small,
            showTitle = false,
            onSelectOption = { index, _ ->
                viewModel.selectedSubCountyDto.postValue(viewModel.subCountiesDto[index])
            }
        )

        RoundedShapeButton(
            modifier = Modifier.padding(top = spacing.small),
            text = str(id = R.string.add_location),
            textPadding = spacing.tiny,
            onClick = onAddLocationClicked
        )
    }
}