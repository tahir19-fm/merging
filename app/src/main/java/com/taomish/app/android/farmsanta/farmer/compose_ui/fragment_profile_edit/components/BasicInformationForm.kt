package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.hbb20.CountryCodePicker
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerDropDownMenu
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_login.components.MobileNoTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.isEmpty
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun BasicInformationForm(
    viewModel: SignUpAndEditProfileViewModel,
    hasFocus: MutableState<Boolean>,
    openDataPickerDialog: () -> Unit
) {
    val spacing = LocalSpacing.current
    val shape = MaterialTheme.shapes.small
    val context = LocalContext.current
    val validate: (Boolean) -> Color = {
        if (viewModel.address.value.length >= 3) Color.Cameron.copy(alpha = .1f)
        else Color.LightGray.copy(alpha = .2f)
    }

    val countryCodePicker = CountryCodePicker(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        ccpDialogShowFlag = true
        ccpDialogShowNameCode = true
        ccpDialogShowTitle = true
        contentColor = Color.Cameron.toArgb()
        setArrowColor(Color.Cameron.toArgb())
        setDialogTextColor(Color.Cameron.toArgb())
        showNameCode(false)
        textView_selectedCountry.textSize = 10f
        setOnCountryChangeListener {
            viewModel.countryCode.postValue(selectedCountryCode)
        }
        try {
            setCountryForPhoneCode(viewModel.countryCode.value.toInt())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (viewModel.countryCode.isEmpty()) {
            "+${viewModel.countryCode.value}".also { textView_selectedCountry.text = it }
        }
        setOnCountryChangeListener {
            viewModel.countryCode.postValue(textView_selectedCountry.text.toString())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {

        MobileNoTextField(
            text = viewModel.mobileNumber,
            countryCodePicker = countryCodePicker,
            enabled = false
        )

        TextFieldWithLabel(
            modifier = Modifier.fillMaxWidth(.8f),
            text = { viewModel.secondaryMobileNo.value },
            onValueChange = { viewModel.secondaryMobileNo.postValue(it) },
            minChars = 10,
            label = str(id = R.string.secondary_number),
            isMandatory = false,
            hasFocus = hasFocus,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        TextFieldWithLabel(
            modifier = Modifier.fillMaxWidth(.8f),
            text = { viewModel.email.value },
            onValueChange = { viewModel.email.postValue(it) },
            label = str(id = R.string.email),
            isMandatory = false,
            hasFocus = hasFocus,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFieldWithLabel(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .padding(end = spacing.medium),
                text = { viewModel.firstName.value },
                onValueChange = { viewModel.firstName.postValue(it) },
                label = str(id = R.string.first_name),
                isMandatory = true,
                hasFocus = hasFocus
            )

            TextFieldWithLabel(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacing.medium),
                text = { viewModel.middleName.value },
                onValueChange = { viewModel.middleName.postValue(it) },
                label = str(id = R.string.middle_name),
                isMandatory = false,
                hasFocus = hasFocus
            )
        }

        TextFieldWithLabel(
            modifier = Modifier.fillMaxWidth(.5f),
            text = { viewModel.lastName.value },
            onValueChange = { viewModel.lastName.postValue(it) },
            minChars = 3,
            label = str(id = R.string.last_name),
            isMandatory = true,
            hasFocus = hasFocus
        )

        GenderRadioButtons(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .padding(vertical = spacing.small),
            genders = viewModel.genders,
            selected = viewModel.selectedGender
        )

        FarmerDropDownMenu(
            modifier = Modifier.fillMaxWidth(.7f),
            title = "${str(id = R.string.education)} ",
            titleFontWeight = FontWeight.Bold,
            expanded = viewModel.educationDropDownExpanded,
            selected = viewModel.education,
            options = viewModel.educationNames.value,
            showAsterisk = false,
            backgroundColor = Color.Cameron.copy(alpha = .1f),
            backgroundScale = spacing.small
        )


        Text(
            text = "+ ${str(id = R.string.add_address)}",
            fontWeight = FontWeight.Bold,
            color = Color.Cameron,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(top = spacing.medium)
        )

        FarmerTextField(
            modifier = Modifier
                .padding(top = spacing.medium)
                .fillMaxWidth()
                .background(color = validate(viewModel.address.value.length >= 3), shape = shape),
            text = { viewModel.address.value },
            onValueChange = { viewModel.address.postValue(it) },
            placeholderText = str(id = R.string.q_h_no),
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
                title = str(id = R.string.country),
                showTitle = false,
                expanded = viewModel.territoryDropDownExpanded,
                selected = viewModel.territory,
                options = viewModel.territories,
                backgroundColor = Color.Cameron.copy(alpha = .1f),
                backgroundScale = spacing.small,
                onSelectOption = { index, _ ->
                    if (index < viewModel.territoriesDto.size) {
                        viewModel.selectedTerritoryDto.postValue(viewModel.territoriesDto[index])
                        viewModel.selectedTerritories.clear()
                        viewModel.selectedTerritories.add(viewModel.territoriesDto[index].uuid)
                        viewModel.clearAddressFields()
                    }
                    viewModel.fetchRegionsByTerritory(context)
                }
            )

            FarmerDropDownMenu(
                modifier = Modifier
                    .padding(start = spacing.small)
                    .fillMaxWidth(),
                title = viewModel.selectedRegionDto.value?.regionName ?: str(id = R.string.state),
                showTitle = false,
                expanded = viewModel.regionDropDownExpanded,
                selected = viewModel.region,
                options = viewModel.regions,
                backgroundColor = Color.Cameron.copy(alpha = .1f),
                backgroundScale = spacing.small,
                onSelectOption = { index, _ ->
                    if (index < viewModel.regionsDto.size) {
                        viewModel.selectedRegionDto.postValue(viewModel.regionsDto[index])
                        viewModel.selectedRegions.clear()
                        viewModel.selectedRegions.add(viewModel.regionsDto[index].uuid)
                    }
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
                title = viewModel.county.value.ifEmpty { str(id = R.string.district) },
                expanded = viewModel.countyDropDownExpanded,
                selected = viewModel.county,
                options = viewModel.counties,
                backgroundColor = Color.Cameron.copy(alpha = .1f),
                backgroundScale = spacing.small,
                showTitle = false,
                onSelectOption = { index, _ ->
                    if (viewModel.countiesDto.size > index) {
                        viewModel.selectedCountyDto.postValue(viewModel.countiesDto[index])
                    }
                    viewModel.fetchSubCountiesByCounty(context)
                }
            )

            FarmerDropDownMenu(
                modifier = Modifier
                    .padding(start = spacing.small)
                    .fillMaxWidth(),
                title = viewModel.subCounty.value.ifEmpty { str(id = R.string.sub_district) },
                expanded = viewModel.subCountyDropDownExpanded,
                selected = viewModel.subCounty,
                options = viewModel.subCounties,
                backgroundColor = Color.Cameron.copy(alpha = .1f),
                backgroundScale = spacing.small,
                showTitle = false,
                onSelectOption = { index, _ ->
                    viewModel.selectedSubCountyDto.postValue(viewModel.subCountiesDto[index])
                    viewModel.fetchVillagesBySubCounty(context)
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
                    .fillMaxWidth(),
                title = viewModel.village.value.ifEmpty { str(id = R.string.village) },
                titleFontWeight = FontWeight.Bold,
                textStyle = MaterialTheme.typography.body2,
                expanded = viewModel.villageDropDownExpanded,
                selected = viewModel.village,
                options = viewModel.villages,
                backgroundColor = Color.Cameron.copy(alpha = .1f),
                backgroundScale = spacing.small,
                showTitle = false,
                onSelectOption = { index, _ ->
                    if (viewModel.villagesDto.size > index) {
                        viewModel.selectedVillageDto.postValue(viewModel.villagesDto[index])
                    }
                }
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .padding(top = spacing.medium)
        ) {

            Text(
                text = str(id = R.string.date_of_birth),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2
            )

            Row(
                modifier = Modifier
                    .padding(top = spacing.small)
                    .fillMaxSize()
                    .background(color = Color.Cameron.copy(alpha = .1f), shape = shape)
                    .padding(spacing.small)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource(),
                        onClick = openDataPickerDialog
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = viewModel.dateOfBirth.value ?: str(id = R.string.select_date),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = spacing.small)
                )

                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.Cameron,
                    modifier = Modifier.padding(end = spacing.small)
                )
            }
        }
    }
}