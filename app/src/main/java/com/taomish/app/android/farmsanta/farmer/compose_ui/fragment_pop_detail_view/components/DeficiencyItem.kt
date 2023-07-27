package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_detail_view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ImageCarousel
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpanStyle
import com.taomish.app.android.farmsanta.farmer.models.api.pop.DeficiencyDto
import com.taomish.app.android.farmsanta.farmer.utils.notNull


@OptIn(ExperimentalPagerApi::class)
@Composable
fun DeficiencyItem(deficiencyDto: DeficiencyDto, onZoomImage: (String) -> Unit) {
    val shape = LocalShapes.current.mediumShape
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .padding(spacing.small)
            .border(width = .3.dp, color = Color.LightGray, shape = shape)
    ) {

        ImageCarousel(
            getImages = { deficiencyDto.photos ?: emptyList() },
            onZoomImage = onZoomImage
        )


        if (!(deficiencyDto.nutrient ?: deficiencyDto.nutrientId).isNullOrEmpty()) {
            AnnotatedText(
                meta = str(id = R.string.nutrient),
                value = deficiencyDto.nutrient ?: deficiencyDto.nutrientId.notNull()
            )
        }

        if (!deficiencyDto.symptomsOfDeficiency.isNullOrEmpty()) {
            AnnotatedText(
                meta = str(id = R.string.symptoms_of_deficiency),
                value = deficiencyDto.symptomsOfDeficiency.notNull()
            )
        }

        if (!deficiencyDto.preventiveMeasures.isNullOrEmpty()) {
            AnnotatedText(
                meta = str(id = R.string.preventive_measures),
                value = deficiencyDto.preventiveMeasures.notNull()
            )
        }

        if (!deficiencyDto.chemicalControl.isNullOrEmpty()) {
            AnnotatedText(
                meta = str(id = R.string.chemical_control),
                value = deficiencyDto.chemicalControl.notNull()
            )
        }

        if (!deficiencyDto.biologicalControl.isNullOrEmpty()) {
            AnnotatedText(
                meta = str(id = R.string.organic_control),
                value = deficiencyDto.biologicalControl.notNull()
            )
        }

    }
}

@Composable
fun AnnotatedText(meta: String, value: String) {
    val spacing = LocalSpacing.current
    val style = LocalSpanStyle.current
    Text(
        text = buildAnnotatedString {
            withStyle(
                style.body2.copy(
                    color = Color.Cameron,
                    fontWeight = FontWeight.Bold
                )
            ) { append("$meta : ") }
            withStyle(
                style.body2.copy(
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            ) { append(value) }
        },
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth(),
        textAlign = TextAlign.Justify
    )
}