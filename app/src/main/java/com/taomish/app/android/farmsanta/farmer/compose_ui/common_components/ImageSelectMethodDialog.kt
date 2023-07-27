package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrowseGallery
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower


@Composable
fun ImageSelectMethodDialog(
    onSelectCamera: () -> Unit,
    onSelectGallery: () -> Unit,
    onDismiss: () -> Unit
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape

    Dialog(
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.RiceFlower, shape = shape)
            ) {
                Text(
                    text = str(id = R.string.select_method),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(
                        horizontal = spacing.small,
                        vertical = spacing.medium
                    )
                )

                Spacer(modifier = Modifier.height(spacing.large))

                Divider(thickness = .3.dp, color = Color.LightGray.copy(alpha = .5f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelectCamera()
                            onDismiss()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Camera,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(spacing.small)
                            .size(48.dp)
                    )

                    Text(
                        text = str(id = R.string.camera),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(spacing.small)
                    )
                }

                Divider(thickness = .3.dp, color = Color.LightGray.copy(alpha = .5f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelectGallery()
                            onDismiss()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.BrowseGallery,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(spacing.small)
                            .size(48.dp)
                    )

                    Text(
                        text = str(id = R.string.gallery),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(spacing.small)
                    )
                }

                Divider(thickness = .3.dp, color = Color.LightGray.copy(alpha = .5f))

                Spacer(modifier = Modifier.height(spacing.large))
            }
        }
    )

}