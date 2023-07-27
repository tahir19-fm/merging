package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_select_language

import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SelectLanguageViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_select_language.components.DrawableDropDownMenu
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.OrangePeel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import com.taomish.app.android.farmsanta.farmer.constants.ApiConstants.Language.EN
import com.taomish.app.android.farmsanta.farmer.constants.ApiConstants.Language.FR
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast
import java.util.Locale


@Composable
fun SelectLanguageFragmentScreen(
    viewModel: SelectLanguageViewModel, onNext: (Int) -> Unit,
) {
    val spacing = LocalSpacing.current
    val sheetShape = LocalShapes.current.topRoundedLargeShape
    val context = LocalContext.current
    val orientation = LocalConfiguration.current.orientation
    var changeUi by remember { mutableStateOf(value = true, policy = neverEqualPolicy()) }
    val onUiChange: () -> Boolean = { changeUi }

    if (onUiChange())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cameron)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RemoteImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.55F)
                    .padding(spacing.small),
                imageLink = "",
                error = R.drawable.ic_location_marker_big
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.RiceFlower, shape = sheetShape),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.let_us_know),
                    fontWeight = FontWeight.Bold,
                    color = Color.Cameron,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(spacing.medium)
                        .fillMaxWidth()
                )
                Log.d("PHONEAUTH", "SelectLanguageFragmentScreen: ${viewModel.selectedCountry}")
                DrawableDropDownMenu(
                    modifier = Modifier.padding(spacing.small),
                    title = str(id = R.string.select_country),
                    iconId = R.drawable.ic_profile_location,
                    expanded = viewModel.countryDropDownExpanded,
                    selected = viewModel.selectedCountry,
                    options = viewModel.territories,
                    backgroundScale = spacing.small,
                    onSelectOption = { index, _ ->
                        viewModel.selectedCountry.postValue(
                            viewModel.territoriesDto[index].territoryName ?: ""
                        )
                        viewModel.selectedTerritoryDto.postValue(viewModel.territoriesDto[index])
                    }
                )

                DrawableDropDownMenu(
                    modifier = Modifier.padding(spacing.small),
                    title = str(id = R.string.select_language),
                    iconId = R.drawable.ic_language,
                    expanded = viewModel.languageDropDownExpanded,
                    selected = viewModel.selectedLanguage,
                    options = viewModel.languages,
                    backgroundScale = spacing.small,
                    onSelectOption = { index, _ ->
                        if (viewModel.selectedLanguageIndex != index) {
                            viewModel.selectedLanguageIndex = index
                            val country = Locale.getDefault().country
                            val language = "${(if (index == 0) EN else FR)}-$country"
                            AppPrefs(context).languageId = if (index == 0) "1" else "2"
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(language)
                            )
                            changeUi = true
                        }
                    }
                )

                RoundedShapeButton(
                    modifier = Modifier
                        .fillMaxWidth(.7f)
                        .padding(vertical = spacing.small),
                    text = str(id = R.string.next),
                    textPadding = spacing.tiny,
                    backgroundColor = Color.OrangePeel,
                    onClick = {
                        if (viewModel.selectedCountry.value.isNotEmpty() && viewModel.selectedLanguage.value.isNotEmpty()) {
                            onNext(viewModel.selectedTerritoryDto.value?.phoneCode ?: 91)
                        } else {
                            context.showToast(context.getString(R.string.select_language_validation_error_msg))
                        }
                    }
                )

                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(96.dp)
                            .background(Color.RiceFlower)
                    )
                }
            }
        }

}

