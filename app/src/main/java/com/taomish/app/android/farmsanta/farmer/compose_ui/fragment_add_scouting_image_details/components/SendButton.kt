package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_scouting_image_details.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun SendButton(onSendClicked: () -> Unit) {
    val spacing = LocalSpacing.current
    Button(
        onClick = onSendClicked,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cameron)
    ) {

        Row(
            modifier = Modifier.padding(spacing.extraSmall),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = str(id = R.string.send),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(end = spacing.small)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_send),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}