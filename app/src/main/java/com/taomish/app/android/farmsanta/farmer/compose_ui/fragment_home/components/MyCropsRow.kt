package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CropCircleImageBottomTitle
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.Screen
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@Composable
fun MyCropsRow(
    crops: SnapshotStateList<CropMaster>,
    onAddClicked: () -> Unit,
    onDelete: (CropMaster) -> Unit,
    onNavigationItemClicked: (Screen) -> Unit
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    var showClose by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {

        Row(
            modifier = Modifier
                .padding(vertical = spacing.small)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = str(id = R.string.my_crops),
                color = Color.White,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )

            if (!showClose) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = str(id = R.string.edit),
                        color = Color.White,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .clickable {
                                if (crops.isNotEmpty()) {
                                    showClose = true
                                } else {
                                    context.showToast(R.string.no_crop_error_msg)
                                }
                            }
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = spacing.small)
                            .size(16.dp)
                            .clickable { onNavigationItemClicked(Screen.MyCrops) }
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { showClose = false }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (crops.size < 3) {
                MyCropsAddButton(onAddClicked)
            }

            LazyRow {
                items(crops) { crop ->
                    val image = URLConstants.S3_IMAGE_BASE_URL + crop.photos
                        ?.elementAtOrNull(0)
                        ?.fileName.toString()

                    CropCircleImageBottomTitle(
                        getImageUrl = { image },
                        getCropName = { crop.cropName ?: "N/A" },
                        isSelected = false,
                        showClose =  { showClose },
                        onCloseClicked = { onDelete(crop) },
                        onClick = { }
                    )
                }
            }
        }
    }
}