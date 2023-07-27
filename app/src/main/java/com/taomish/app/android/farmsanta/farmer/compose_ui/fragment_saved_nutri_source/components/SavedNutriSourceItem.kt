package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_saved_nutri_source.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
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
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpanStyle
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Valencia


@Composable
fun SavedNutriSourceItem(onNutriSourceClicked: () -> Unit) {
    val spacing = LocalSpacing.current
    val style = LocalSpanStyle.current

    Column(
        modifier = Modifier
            .padding(spacing.extraSmall)
            .width(160.dp)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {

            RemoteImage(
                modifier = Modifier.fillMaxSize(),
                imageLink = "https://cdn-prodapi.iffcobazar.in/pub/media/catalog/product/n/u/nutri-rich_5_kg_6_-min.png",
                contentScale = ContentScale.FillBounds
            )

            Icon(
                imageVector = Icons.Filled.Bookmark,
                contentDescription = null,
                tint = Color.Valencia,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(horizontal = spacing.small, vertical = spacing.extraSmall)
                    .background(color = Color.White.copy(alpha = .5f), shape = CircleShape)
                    .padding(spacing.extraSmall)
            )
        }


        Text(
            text = "Boron 20",
            color = Color.Cameron,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = spacing.small, vertical = spacing.extraSmall)
        )

        Text(
            text = buildAnnotatedString {
                pushStyle(style.caption.copy(color = Color.LightGray))
                append("${str(id = R.string.mrp)}: ")
                pop()
                pushStyle(style.caption.copy(color = Color.Cameron))
                append("Rs 500")
            },
            modifier = Modifier.padding(horizontal = spacing.small, vertical = spacing.extraSmall)
        )

        Text(
            text = str(id = R.string.lorem_ipsum_long),
            style = MaterialTheme.typography.caption,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.small, vertical = spacing.extraSmall)
        )

        Text(
            text = "${str(id = R.string.view_product)} ${str(id = R.string.right_arrow)}",
            color = Color.Cameron,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(horizontal = spacing.small, vertical = spacing.extraSmall)
                .clickable(onClick = onNutriSourceClicked)
        )
    }
}