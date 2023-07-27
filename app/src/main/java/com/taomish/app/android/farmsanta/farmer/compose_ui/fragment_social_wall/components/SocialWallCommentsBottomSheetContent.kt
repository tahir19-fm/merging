package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SearchLeadingIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.message.Comment
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@Composable
fun SocialWallCommentsBottomSheetContent(
    text: MutableState<String>,
    textError: MutableState<Boolean>,
    onComment: () -> Unit,
    comments: List<Comment>,
    likes: Int
) {
    val spacing = LocalSpacing.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.small)
    ) {
        item {
            FarmerTextField(
                text = { text.value },
                onValueChange = { text.postValue(it) },
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = Color.LightGray.copy(alpha = .2f),
                leadingIcon = { SearchLeadingIcon() },
                placeholderText = str(id = R.string.comment),
                textStyle = MaterialTheme.typography.body2,
                shape = CircleShape,
                trailingIcon = {
                    SentTrailingIcon {
                        if (text.value.isEmpty()) {
                            context.showToast(R.string.please_add_comment)
                        } else {
                            focusManager.clearFocus()
                            onComment()
                        }
                    }
                },
                isError = textError.value
            )

            Text(
                text = "${str(id = R.string.view)} ${comments.size} ${str(id = R.string.comments)}",
                modifier = Modifier.padding(top = spacing.medium, bottom = spacing.small)
            )
        }

        items(comments) { comment ->
            CommentListItem(
                comment = comment
            ) { }
        }
    }
}

@Composable
fun SentTrailingIcon(
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_send),
        contentDescription = null,
        tint = Color.Cameron,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(4.dp)
    )
}