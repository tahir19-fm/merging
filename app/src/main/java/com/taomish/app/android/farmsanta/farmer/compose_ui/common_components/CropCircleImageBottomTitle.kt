package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.DeleteCropCalendarTask
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.TomatoRed
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener

@Composable
fun CropCircleImageBottomTitle(
    getImageUrl: () -> String,
    getCropName: () -> String?,
    getId: String,
    isSelected: Boolean,
    selectedColor: Color = Color.Cameron,
    unselectedColor: Color = Color.White,
    selectedContentColor: Color = Color.White,
    unselectedContentColor: Color = Color.Cameron,
    showClose: () -> Boolean,
    initData: (() -> Unit)? = null,
    onCloseClicked: (() -> Unit)? = null,
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(spacing.extraSmall)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = { if (!isSelected) onClick() }
            )
    ) {

        RemoteImage(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(spacing.extraSmall)
                .fillMaxSize()
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = unselectedColor,
                    shape = CircleShape
                )
                .padding(bottom = spacing.small),
            imageLink = getImageUrl(),
            contentScale = ContentScale.Crop,
            error = R.mipmap.img_default_pop
        )
        if(getId != "") {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.TomatoRed, shape = RoundedCornerShape(16.dp))
                    .align(Alignment.TopEnd)
                    .border(
                        width = .9.dp,
                        color = selectedColor,
                        shape = CircleShape
                    )
                    .clickable(onClick = { showDialog.value = true })
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(spacing.extraSmall)
                        .size(16.dp)
                )
            }
        }
        Text(
            text = getCropName() ?: "",
            color = if (isSelected) selectedContentColor else unselectedContentColor,
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .border(
                    width = .9.dp,
                    color = selectedColor,
                    shape = CircleShape
                )
                .background(
                    color = if (isSelected) selectedColor else unselectedColor,
                    shape = CircleShape
                )
                .padding(horizontal = spacing.small, vertical = spacing.extraSmall)
        )

        if (showClose()) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                tint = Color.Cameron,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color.White, shape = CircleShape)
                    .border(width = .3.dp, color = Color.Cameron, shape = CircleShape)
                    .padding(spacing.extraSmall)
                    .size(12.dp)
                    .clip(CircleShape)
                    .clickable { onCloseClicked?.invoke() }
            )
        }
        if (showDialog.value) {
            Dialog(onDismissRequest = {
                showDialog.value = false
            }) {
                Box(modifier = Modifier.padding(16.dp)
                    .background(Color.White,RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .heightIn(min=150.dp)
                    .widthIn(min=350.dp)
                ) {
                    Text(text= "Are you sure you want to delete ${getCropName() ?: ""}.",
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp))
                    Button(
                        onClick = {
                            showDialog.value = false
                        },
                        modifier = Modifier.padding(spacing.medium)
                            .align(Alignment.BottomStart),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Cameron,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text= "Cancel", fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            deleteCropCalendar(context,getId,initData)
                            onCloseClicked?.invoke()
                            showDialog.value = false
                        },
                        modifier = Modifier.padding(spacing.medium)
                            .align(Alignment.BottomEnd),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.TomatoRed,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text= "Delete",
                            fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
fun deleteCropCalendar(context: Context, uuid: String?, initData: (() -> Unit)?) {
    val deleteTask = DeleteCropCalendarTask(uuid ?:"")
    deleteTask.context = context
    deleteTask.setShowLoading(true)
    deleteTask.setOnTaskCompletionListener(object : OnTaskCompletionListener {
        override fun onTaskSuccess(data: Any?) {
            Toast.makeText(context,"Crop deleted Successfully",Toast.LENGTH_SHORT).show()
            if (initData != null) {
                initData()
            }
        }

        override fun onTaskFailure(reason: String?, errorMessage: String?) {
            Toast.makeText(context,"Something Wrong",Toast.LENGTH_SHORT).show()
            if (initData != null) {
                initData()
            }
        }
    })
    deleteTask.execute()
}

