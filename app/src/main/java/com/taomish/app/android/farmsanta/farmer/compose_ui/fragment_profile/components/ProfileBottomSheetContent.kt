package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.ProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun ProfileBottomSheetContent(
    viewModel: ProfileViewModel,
    onClickClose: () -> Unit
) {
    val spacing = LocalSpacing.current
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small + spacing.extraSmall)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = null,
                        tint = Color.Black
                    )

                    Text(
                        text = str(id = R.string.settings),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = spacing.small)
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    tint = Color.Cameron,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onClickClose() }
                )
            }

            SettingsItem(
                checked = viewModel.allNotificationChecked,
                strId = R.string.all_notification,
                iconId = R.drawable.ic_profile_bell
            )

            SettingsItem(
                checked = viewModel.talksActivityNotificationChecked,
                strId = R.string.farm_talks_activity,
                iconId = R.drawable.ic_profile_talks_activity_notification
            )

            SettingsItem(
                checked = viewModel.farmTalksNotificationChecked,
                strId = R.string.alert_farm_talks,
                iconId = R.drawable.ic_profile_talks_notification
            )

            SettingsItem(
                checked = viewModel.alertAdvisoryNotificationChecked,
                strId = R.string.alert_advisory,
                iconId = R.drawable.ic_profile_advisory_notification
            )

            SettingsItem(
                checked = viewModel.alertPopNotificationChecked,
                strId = R.string.alert_pop,
                iconId = R.drawable.ic_profile_alert_pop
            )

            SettingsItem(
                checked = viewModel.newsAlertChecked,
                strId = R.string.news_alert,
                iconId = R.drawable.ic_profile_news
            )

            /*ChangePasswordButton { }

            RoundedShapeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small),
                text = "Logout",
                textStyle = MaterialTheme.typography.body2,
                onClick = {}
            )*/
        }
    }
}