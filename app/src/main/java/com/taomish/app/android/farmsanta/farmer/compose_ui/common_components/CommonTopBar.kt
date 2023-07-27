package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.findNavController
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.BlackOlive
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing

@Composable
fun CommonTopBar(
    modifier: Modifier = Modifier,
    activity: AppCompatActivity,
    title: String? = null,
    isAddRequired: Boolean = true,
    tintColor: Color = Color.BlackOlive,
    backgroundColor: Color = Color.White,
    onBack: () -> Unit = {},
    addClick: () -> Unit,
    rightIcon: @Composable () -> Unit = {
        DefaultTopAppBarIcon(
            modifier = modifier,
            tintColor = tintColor,
            onClick = addClick
        )
    },
) {
    Row(
        modifier = modifier
            .background(backgroundColor)
            .padding(LocalSpacing.current.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "Back",
            modifier = modifier
                .clickable(
                    onClick = {
                        onBack.invoke()
                        activity.findNavController(R.id.nav_host_fragment_activity_main)
                            .popBackStack()
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false
                    )
                ),
            tint = tintColor
        )

        Text(
            text = title ?: "",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2,
            modifier = modifier
                .weight(1f)
                .padding(start = 8.dp, end = 8.dp),
            textAlign = TextAlign.Start,
            color = tintColor
        )

        if (isAddRequired) {
            rightIcon()
        }
    }
}


@Composable
fun DefaultTopAppBarIcon(
    modifier: Modifier = Modifier,
    tintColor: Color,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.clickable(
            onClick = onClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = false)
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = str(id = R.string.create),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2,
            modifier = modifier.padding(start = 8.dp),
            textAlign = TextAlign.Start,
            color = tintColor
        )

        Image(
            painter = painterResource(id = R.drawable.ic_add_new),
            contentDescription = null,
            modifier = modifier
                .padding(start = 8.dp)
        )
    }
}