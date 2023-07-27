package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FertilizerCalculatorViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.FertilizerTypes


@Composable
fun FertilizerSourceSelectionPage(viewModel: FertilizerCalculatorViewModel) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        Text(
            text = str(id = R.string.choose_your_preferred_fertilizer),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth(.8f)
                .padding(vertical = spacing.small)
        )

        FertilizerSourceSelectionField(
            tagStrId = R.string.npk_complex,
            dropDownExpanded = viewModel.npkComplexDropDownExpanded,
            selected = viewModel.npkComplexSelected,
            options = viewModel.fertilizerSourceDetails.filter { it.fertilizerType == FertilizerTypes.NPK_FERTILIZERS }
        )


        FertilizerSourceSelectionField(
            tagStrId = R.string.potassic,
            dropDownExpanded = viewModel.potassicDropDownExpanded,
            selected = viewModel.potassicSelected,
            options = viewModel.fertilizerSourceDetails.filter { it.fertilizerType == FertilizerTypes.POTASSIC_FERTILIZER }
        )

        FertilizerSourceSelectionField(
            tagStrId = R.string.zinc_text,
            dropDownExpanded = viewModel.zincDropDownExpanded,
            selected = viewModel.zincSelected,
            options = viewModel.fertilizerSourceDetails.filter { it.fertilizerType == FertilizerTypes.ZINC_FERTILIZER }
        )

        FertilizerSourceSelectionField(
            tagStrId = R.string.bron,
            dropDownExpanded = viewModel.bronDropDownExpanded,
            selected = viewModel.bronSelected,
            options = viewModel.fertilizerSourceDetails.filter { it.fertilizerType == FertilizerTypes.BORON_FERTILIZER }
        )

        FertilizerSourceSelectionField(
            tagStrId = R.string.phosphorous_text,
            dropDownExpanded = viewModel.phosphorousDropDownExpanded,
            selected = viewModel.phosphorousSelected,
            options = viewModel.fertilizerSourceDetails.filter { it.fertilizerType == FertilizerTypes.PHOSPHORUS_FERTILIZER }
        )

        FertilizerSourceSelectionField(
            tagStrId = R.string.nitrogenous,
            dropDownExpanded = viewModel.nitrogenousDropDownExpanded,
            selected = viewModel.nitrogenousSelected,
            options = viewModel.fertilizerSourceDetails.filter { it.fertilizerType == FertilizerTypes.NITROGENOUS_FERTILIZER }
        )


        Spacer(modifier = Modifier.height(spacing.extraLarge + spacing.medium))
    }
}