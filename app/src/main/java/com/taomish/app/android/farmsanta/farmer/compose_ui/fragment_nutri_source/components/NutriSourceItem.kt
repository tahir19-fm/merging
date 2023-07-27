package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_nutri_source.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpanStyle


@Composable
fun NutriSourceItem(onNutriSourceClicked: () -> Unit) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    val style = LocalSpanStyle.current
    Row(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
            .background(color = Color.White, shape = shape),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RemoteImage(
            modifier = Modifier
                .fillMaxWidth(.4f)
                .height(164.dp)
                .padding(spacing.extraSmall),
            imageLink = "https://cdn-prodapi.iffcobazar.in/pub/media/catalog/product/n/u/nutri-rich_5_kg_6_-min.png",
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing.small)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Boron 20",
                    color = Color.Cameron,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = buildAnnotatedString {
                        pushStyle(style.caption.copy(color = Color.Gray))
                        append("${str(id = R.string.mrp)}: ")
                        pop()
                        pushStyle(style.caption.copy(color = Color.Cameron))
                        append("Rs 500")
                    }
                )
            }

            Text(
                text = str(id = R.string.lorem_ipsum_long),
                style = MaterialTheme.typography.caption,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = buildAnnotatedString {
                    pushStyle(style.caption.copy(color = Color.Gray))
                    append("${str(id = R.string.product_nutrient)}: ")
                    pop()
                    pushStyle(style.caption.copy(color = Color.Cameron))
                    append("Boron")
                }
            )

            Text(
                text = "${str(id = R.string.view_product)} ${str(id = R.string.right_arrow)}",
                color = Color.Cameron,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.clickable(onClick = onNutriSourceClicked)
            )
        }
    }
}