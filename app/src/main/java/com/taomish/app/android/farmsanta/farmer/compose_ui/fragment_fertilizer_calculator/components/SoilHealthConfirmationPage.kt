package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun SoilHealthConfirmationPage(hasReport: MutableState<Boolean>) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.padding(spacing.small),
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            Text(
                text = str(id = R.string.soil_health),
                color = Color.Limeade,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = str(id = R.string.soil_health_report_question),
                color = Color.Limeade,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2
            )

            Row {

                Row(
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) { hasReport.postValue(true) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = hasReport.value,
                        onClick = { hasReport.postValue(true) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Limeade,
                            unselectedColor = Color.LightGray
                        ),
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = str(id = R.string.yes),
                        style = MaterialTheme.typography.body2,
                        color = if (hasReport.value) Color.Limeade else Color.LightGray,
                        modifier = Modifier.padding(start = spacing.medium)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(start = spacing.extraLarge)
                        .clickable(
                            indication = null,
                            interactionSource = MutableInteractionSource()
                        ) { hasReport.postValue(false) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = hasReport.value.not(),
                        onClick = { hasReport.postValue(false) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Limeade,
                            unselectedColor = Color.LightGray
                        ),
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = str(id = R.string.no),
                        style = MaterialTheme.typography.body2,
                        color = if (hasReport.value.not()) Color.Limeade else Color.LightGray,
                        modifier = Modifier.padding(start = spacing.medium)
                    )
                }

            }
        }
    }
}