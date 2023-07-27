package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_nutri_source.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun BoxScope.SheetFooter(onClickApply: () -> Unit, onClickCancel: () -> Unit) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(.5f),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            onClick = onClickCancel
        ) {
            Text(
                text = str(id = R.string.cancel),
                style = MaterialTheme.typography.body2,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = spacing.small)
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cameron),
            onClick = onClickApply
        ) {
            Text(
                text = str(id = R.string.apply),
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(vertical = spacing.small)
            )
        }
    }
}