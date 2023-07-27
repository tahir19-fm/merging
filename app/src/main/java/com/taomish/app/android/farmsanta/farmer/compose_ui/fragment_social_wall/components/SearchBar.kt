package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SearchLeadingIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_crops.components.SearchBarButtons
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun SearchBar(
    text: MutableState<String>,
    hasFocus: MutableState<Boolean>,
    onSearch: () -> Unit,
    onClose: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val focusManager = LocalFocusManager.current
    FarmerTextField(
        text = { text.value },
        onValueChange = {
            text.postValue(it)
            if (it.isEmpty()) onClose()
        },
        modifier = Modifier
            .padding(spacing.medium)
            .fillMaxWidth(),
        hasFocus = hasFocus,
        backgroundColor = Color.LightGray.copy(alpha = .2f),
        leadingIcon = { SearchLeadingIcon() },
        placeholderText = str(id = R.string.search),
        textStyle = MaterialTheme.typography.caption,
        shape = CircleShape,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        trailingIcon = {
            SearchBarButtons(
                onSearch = onSearch,
                showClose = { hasFocus.value },
                onClose = {
                    focusManager.clearFocus()
                    text.postValue("")
                    onClose()
                }
            )
        }
    )
}

@Composable
fun SearchBarIconButton(
    @DrawableRes iconId: Int,
    padding: Dp,
    backgroundColor: Color = Color.LightGray.copy(alpha = .2f),
    backgroundPadding: Dp = 8.dp,
    iconTint: Color = Color.Cameron,
    onClick: () -> Unit,
) {
    Icon(
        painter = painterResource(id = iconId),
        contentDescription = null,
        tint = iconTint,
        modifier = Modifier
            .padding(horizontal = padding)
            .background(color = backgroundColor, shape = CircleShape)
            .padding(backgroundPadding)
            .size(24.dp)
            .clip(CircleShape)
            .clickable { onClick() }
    )
}