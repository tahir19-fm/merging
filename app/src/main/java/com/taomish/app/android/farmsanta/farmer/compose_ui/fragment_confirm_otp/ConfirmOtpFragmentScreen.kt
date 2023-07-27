package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_confirm_otp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.ConfirmOtpViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_confirm_otp.components.OtpCharTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@Composable
fun ConfirmOtpFragmentScreen(
    confirmOtpViewModel: ConfirmOtpViewModel,
    onAuthenticateOtp: () -> Unit,
    onBackPressed: () -> Unit,
    resendToken: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.mediumShape
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val hasFocus = remember { mutableStateOf(false) }
    BackHandler(hasFocus.value) { focusManager.clearFocus() }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = spacing.large, horizontal = spacing.medium)
                .fillMaxSize()
                .background(color = Color.White, shape = shape)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth()) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable(
                            onClick = onBackPressed,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false)
                        ),
                    tint = Color.Cameron
                )
            }

            Text(
                text = stringResource(id = R.string.enter_otp_msg),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.medium)
            )

            Row(Modifier.padding(top = spacing.medium)) {
                Text(
                    text = "${stringResource(id = R.string.sent_on)}  ",
                    style = MaterialTheme.typography.caption,
                    color = Color.LightGray
                )

                Text(
                    text = confirmOtpViewModel.mobileNo.value ?: "",
                    style = MaterialTheme.typography.caption,
                    color = Color.Cameron
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small),
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                confirmOtpViewModel.otpDigits.forEachIndexed { index, digit ->
                    OtpCharTextField(
                        digit = digit,
                        hasFocus = hasFocus,
                        focusManager = focusManager,
                        index = index
                    )
                }
            }

            RoundedShapeButton(
                modifier = Modifier
                    .padding(top = spacing.medium)
                    .fillMaxWidth(.7f),
                text = stringResource(id = R.string.verify),
                textPadding = spacing.extraSmall,
                textStyle = MaterialTheme.typography.caption,
                onClick = {
                    if (confirmOtpViewModel.validate()) {
                        onAuthenticateOtp()
                    } else {
                        context.showToast(context.getString(R.string.otp_invalid_message))
                    }
                }
            )

            Text(
                text = stringResource(id = R.string.change_number),
                color = Color.LightGray,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(top = spacing.medium, start = spacing.small)
                    .fillMaxWidth()
                    .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                        onBackPressed()
                    }
            )

            Text(
                text = "${stringResource(id = R.string.resend_otp)} ${confirmOtpViewModel.countDownText.value}",
                color = Color.Cameron,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(top = spacing.medium, end = spacing.small)
                    .fillMaxWidth()
                    .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                        if (!confirmOtpViewModel.isTimerOn.value) {
                            confirmOtpViewModel.enableResendToken.postValue(true)
                            resendToken()
                        }
                    }
            )
        }
    }
}