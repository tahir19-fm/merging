package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FertilizerCalculatorViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun SoilHealthFormPage(viewModel: FertilizerCalculatorViewModel, onDone: (Int) -> Unit) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    BackHandler(viewModel.hasFocus.value) { focusManager.clearFocus() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = str(id = R.string.soil_health),
            color = Color.Limeade,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = spacing.small, vertical = spacing.medium)
        )

        Column(
            modifier = Modifier
                .padding(vertical = spacing.medium, horizontal = spacing.small)
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = .2f), shape)
        ) {
            Text(
                text = str(id = R.string.enter_details_of_soil_test_report),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    horizontal = spacing.extraSmall,
                    vertical = spacing.medium
                )
            )

            SoilHealthTextField(
                text = viewModel.nitrogenTextField,
                level = viewModel.nitrogenLevel,
                placeHolderId = R.string.nitrogen_text,
                unit = str(id = R.string.nitrogen_unit),
                low = viewModel.nitrogenHighLowLevels.first,
                high = viewModel.nitrogenHighLowLevels.second,
                hasFocus = viewModel.hasFocus
            )

            SoilHealthTextField(
                text = viewModel.phosphorousTextField,
                level = viewModel.phosphorousLevel,
                placeHolderId = R.string.phosphorous_text,
                unit = str(id = R.string.phosphorous_unit),
                low = viewModel.phosphorousHighLowLevels.first,
                high = viewModel.phosphorousHighLowLevels.second,
                hasFocus = viewModel.hasFocus
            )

            SoilHealthTextField(
                text = viewModel.potassiumTextField,
                level = viewModel.potassiumLevel,
                placeHolderId = R.string.pottasium_text,
                unit = str(id = R.string.pottasium_unit),
                low = viewModel.potassiumHighLowLevels.first,
                high = viewModel.potassiumHighLowLevels.second,
                hasFocus = viewModel.hasFocus
            )

            SoilHealthTextField(
                text = viewModel.zincTextField,
                level = viewModel.zincLevel,
                placeHolderId = R.string.zinc_text,
                unit = str(id = R.string.zinc_unit),
                low = viewModel.zincHighLowLevels.first,
                high = viewModel.zincHighLowLevels.second,
                hasFocus = viewModel.hasFocus
            )

            SoilHealthTextField(
                text = viewModel.boronTextField,
                level = viewModel.boronLevel,
                placeHolderId = R.string.boron_text,
                unit = str(id = R.string.boron_unit),
                low = viewModel.boronHighLowLevels.first,
                high = viewModel.boronHighLowLevels.second,
                hasFocus = viewModel.hasFocus
            )

            SoilHealthTextField(
                text = viewModel.sulphurTextField,
                level = viewModel.sulphurLevel,
                placeHolderId = R.string.sulphur_text,
                unit = str(id = R.string.sulphur_unit),
                low = viewModel.sulphurHighLowLevels.first,
                high = viewModel.sulphurHighLowLevels.second,
                hasFocus = viewModel.hasFocus
            )

            SoilHealthTextField(
                text = viewModel.pHTextField,
                level = viewModel.pHLevel,
                placeHolderId = R.string.ph,
                unit = str(id = R.string.not_available),
                low = viewModel.pHHighLowLevels.first,
                high = viewModel.pHHighLowLevels.second,
                hasFocus = viewModel.hasFocus,
                isPhValue = true,
                onDone = {
                    if (!viewModel.hasReport.value || viewModel.validate(context))
                        onDone(Page.SELECT_CROPS)
                }
            )

            Spacer(modifier = Modifier.height(spacing.medium))
        }

        Spacer(modifier = Modifier.height(spacing.extraLarge))
    }
}