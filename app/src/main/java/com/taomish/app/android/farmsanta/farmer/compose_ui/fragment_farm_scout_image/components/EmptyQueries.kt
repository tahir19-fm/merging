package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_image.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun BoxScope.EmptyQueries(onClick: () -> Unit, modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = spacing.extraLarge)
            .align(Alignment.Center)
            .clickable(indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_add_new),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp)
        )

        Text(
            text = str(id = R.string.create_new_query),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = spacing.large)
        )
    }
}