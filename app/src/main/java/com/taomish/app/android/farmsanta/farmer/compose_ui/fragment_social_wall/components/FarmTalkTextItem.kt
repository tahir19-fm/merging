package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_wall.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ReadMoreText
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil


@Composable
fun FarmTalkTextItem(
    message: Message?,
    CenterIcon: @Composable (BoxScope.() -> Unit)? = null,
    TopActionsRow: @Composable (BoxScope.() -> Unit),
    onLikeClicked: (Message?) -> Unit,
    onCommentClicked: (Message?) -> Unit,
    onReadMoreClicked: (Message?) -> Unit,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
            .background(color = Color.LightGray.copy(alpha = .2F), shape = shape)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = { onReadMoreClicked(message) }
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            ActionsColumn(
                message = message,
                onFavoriteClicked = { onLikeClicked(message) }
            ) { onCommentClicked(message) }

            Column(
                modifier = Modifier
                    .padding(spacing.small)
                    .fillMaxWidth(.85F)
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                Text(
                    text = message?.title ?: "",
                    style = MaterialTheme.typography.body2
                )

                Text(
                    text = HtmlCompat.fromHtml(
                        message?.description ?: "",
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    ).toString(),
                    style = MaterialTheme.typography.caption,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth(),
                )

                ReadMoreText(
                    onReadMoreClicked = { onReadMoreClicked.invoke(message) },
                    isExpandedable = false
                )

                if (message?.agronomyManager == true) Agronomist()

                TalkDateTime(
                    modifier = Modifier.padding(start = spacing.small),
                    dateTime = DateUtil().getDateMonthYearFormat(message?.createdTimestamp) ?: ""
                )

            }

            TopActionsRow()

            CenterIcon?.invoke(this)
        }

        /*FlowRow(horizontalGap = spacing.extraSmall, verticalGap = spacing.extraSmall) {
            (message?.tags ?: emptyList()).forEach { tag ->
                Text(
                    text = "#$tag",
                    color = Color.Blue,
                    style = MaterialTheme.typography.caption
                )
            }
        }*/

        Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray, thickness = .5.dp)

        LikesAndComments(
            likes = message?.likes ?: 0,
            comments = message?.comments?.size ?: 0,
            onLikeClicked = { onLikeClicked(message) },
            onCommentClicked = { onCommentClicked(message) }
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TalkDateTime(modifier: Modifier = Modifier, dateTime: String) {
    Chip(
        modifier = modifier,
        onClick = { },
        border = BorderStroke(width = .5.dp, color = Color.Cameron),
        colors = ChipDefaults.chipColors(
            backgroundColor = Color.LightGray.copy(alpha = .2F),
            contentColor = Color.Cameron
        )
    ) {
        Text(
            text = dateTime,
            style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold)
        )
    }
}