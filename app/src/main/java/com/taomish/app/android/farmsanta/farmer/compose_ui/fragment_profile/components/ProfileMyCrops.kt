package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CropCircleImageBottomTitle
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import kotlin.reflect.KFunction1


@Composable
fun ProfileMyCrops(myCrops: List<CropMaster>) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .padding(spacing.small)
            .fillMaxWidth()
    ) {

        Text(
            text = str(id = R.string.my_crops),
            color = Color.Cameron,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = spacing.small)
        )

        if (myCrops.isEmpty()) {
            Text(
                text = str(id = R.string.no_crop_have_been_added_yet),
                color = Color.Cameron,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }

        LazyRow {
            items(myCrops) { crop ->
                val image = URLConstants.S3_IMAGE_BASE_URL + crop.photos?.elementAtOrNull(0)
                    ?.fileName.toString()

                CropCircleImageBottomTitle(
                    getImageUrl = { image },
                    getCropName = { crop.cropName ?: "N/A" },
                    getId = "",
                    isSelected = false,
                    showClose = { false }
                ) { }
            }
        }
    }
}
fun deleteCropCalender(){}