package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun ReadMoreText(
    modifier: Modifier = Modifier,
    descriptionLines: MutableState<Int>? = null,
    minLines: Int = 3,
    isExpandedable: Boolean = false,
    onReadMoreClicked: (() -> Unit)? = null
) {
    val readMore = str(id = R.string.read_more)
    val readLess = str(id = R.string.read_less)
    var readText by remember { mutableStateOf(if (!isExpandedable) readMore else readLess) }
    Text(
        text = "$readText ${stringResource(id = R.string.right_arrow)}",
        style = MaterialTheme.typography.caption,
        color = Color.Cameron,
        textAlign = TextAlign.End,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) {
                if (isExpandedable) {
                    if (readText == readMore) {
                        readText = readLess
                        descriptionLines?.postValue(Int.MAX_VALUE)
                    } else {
                        readText = readMore
                        descriptionLines?.postValue(minLines)
                    }
                }
                onReadMoreClicked?.invoke()
            }
    )
}