package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants


@Composable
fun FileOrUrlImage(modifier: Modifier = Modifier, bitmap: Bitmap?, imageUrl: String?) {
    if (bitmap != null) {
        Image(
            modifier = modifier,
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop
        )
    } else {
        RemoteImage(
            modifier = modifier,
            imageLink = URLConstants.S3_IMAGE_BASE_URL + (imageUrl ?: ""),
            contentScale = ContentScale.FillBounds
        )
    }
}