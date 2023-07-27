package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SearchLeadingIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components.SentTrailingIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@Composable
fun SendMessageTextField(
    text: MutableState<String>,
    textError: MutableState<Boolean>,
    focusManager: FocusManager,
    onSendMessageClicked: () -> Unit
) {

    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(spacing.small)
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FarmerTextField(
            text = { text.value },
            onValueChange = { text.postValue(it) },
            modifier = Modifier
                .fillMaxWidth(.7f),
            backgroundColor = Color.LightGray.copy(alpha = .2f),
            leadingIcon = { SearchLeadingIcon() },
            placeholderText = str(id = R.string.search),
            textStyle = MaterialTheme.typography.body2,
            shape = CircleShape,
            trailingIcon = {
                SentTrailingIcon {
                    if (text.value.isEmpty()) {
                        context.showToast(R.string.please_write_msg)
                    } else {
                        focusManager.clearFocus()
                        onSendMessageClicked()
                    }
                }
            },
            isError = textError.value
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_camera_green),
            contentDescription = null,
            tint = Color.Cameron,
            modifier = Modifier
                .fillMaxWidth(.5f)
                .padding(start = spacing.small, spacing.extraSmall)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_mic),
            contentDescription = null,
            tint = Color.Cameron,
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.extraSmall)
        )
    }
}