package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_login

import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.hbb20.CountryCodePicker
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.LoginViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_login.components.MobileNoTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import com.taomish.app.android.farmsanta.farmer.constants.ApiConstants
import com.taomish.app.android.farmsanta.farmer.utils.isEmpty
import com.taomish.app.android.farmsanta.farmer.utils.language
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@Composable
fun LoginFragmentScreen(
    loginViewModel: LoginViewModel,
    requestOtp: () -> Unit,
    onSignUp: () -> Unit,
    onTerms: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    val countryCodePicker = CountryCodePicker(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        ccpDialogShowFlag = true
        ccpDialogShowNameCode = true
        ccpDialogShowTitle = true
        contentColor = Color.Cameron.toArgb()
        setArrowColor(Color.Cameron.toArgb())
        setDialogTextColor(Color.Cameron.toArgb())
        showNameCode(false)
        textView_selectedCountry.textSize = 10f
        setOnCountryChangeListener {
            loginViewModel.countryCode.postValue(selectedCountryCode)
        }
        try {
            setCountryForPhoneCode(loginViewModel.countryCode.value.toInt())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (loginViewModel.countryCode.isEmpty()) {
            "+${loginViewModel.countryCode.value}".also { textView_selectedCountry.text = it }
        }
        setOnCountryChangeListener {
            loginViewModel.countryCode.postValue(textView_selectedCountry.text.toString())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(Color.RiceFlower)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.TopStart),
                painter = painterResource(id = R.drawable.login_mascot_img),
                contentDescription = "Background",
                contentScale = ContentScale.Fit,
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val welcomeId =
                if (context.language == ApiConstants.Language.EN) R.drawable.ic_welcome_text_en
                else R.drawable.ic_welcome_text_fr

            Icon(
                painter = painterResource(id = welcomeId),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .width(240.dp)
                    .height(42.dp)
                    .padding(top = spacing.medium)
            )

            /*Text(
                text = str(id = R.string.login),
                color = Color.Cameron,
                modifier = Modifier.padding(top = spacing.large)
            )*/

            MobileNoTextField(
                text = loginViewModel.phoneNumber,
                countryCodePicker = countryCodePicker,
                onDone = {
                    loginViewModel.countryCode.postValue(
                        countryCodePicker.textView_selectedCountry.text.toString()
                    )
                    if (loginViewModel.validate()) {
                        requestOtp()
                    } else {
                        context.showToast(context.getString(R.string.phone_no_invalid))
                    }
                }
            )

            RoundedShapeButton(
                modifier = Modifier
                    .padding(top = spacing.small)
                    .fillMaxWidth(.7f),
                text = str(id = R.string.login),
                textPadding = spacing.extraSmall,
                textStyle = MaterialTheme.typography.caption,
                onClick = {
                    loginViewModel.countryCode.postValue(
                        countryCodePicker.textView_selectedCountry.text.toString()
                    )
                    if (loginViewModel.validate()) {
                        requestOtp()
                    } else {
                        context.showToast(context.getString(R.string.phone_no_invalid))
                    }
                }
            )

            Row(
                modifier = Modifier.padding(top = spacing.medium),
                horizontalArrangement = Arrangement.spacedBy(space = spacing.small)
            ) {
                Text(
                    text = str(id = R.string.new_user),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.caption
                )

                Text(
                    text = str(id = R.string.sign_up),
                    color = Color.Cameron,
                    style = MaterialTheme.typography.caption,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onSignUp() }
                )
            }

            Text(
                text = str(id = R.string.terms_and_conditions_accept),
                color = Color.LightGray,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = spacing.medium),
                textAlign = TextAlign.Center
            )

            Text(
                text = str(id = R.string.terms_and_conditions),
                color = Color.Cameron,
                style = MaterialTheme.typography.caption,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(vertical = spacing.medium)
                    .clickable(
                        onClick = onTerms,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true)
                    )
            )

            Image(
                painter = painterResource(id = R.drawable.ic_login_farmers),
                contentDescription = "Farmers",
                modifier = Modifier.padding(bottom = spacing.large),
                contentScale = ContentScale.Fit
            )
        }
    }
}