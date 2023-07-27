package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_sign_up

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.hbb20.CountryCodePicker
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_login.components.MobileNoTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@Composable
fun SignUpFragmentScreen(
    viewModel: SignUpAndEditProfileViewModel,
    requestOtp: () -> Unit,
    onSignIn: () -> Unit,
    onTerms: () -> Unit
) {
    val spacing = LocalSpacing.current
    val cardShape = LocalShapes.current.smallCardShape
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
            viewModel.countryCode.postValue(selectedCountryCode)
        }
        try {
            setCountryForPhoneCode(viewModel.countryCode.value.toInt())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Card(
                modifier = Modifier
                    .padding(start = spacing.medium, end = spacing.medium, top = 20.dp)
                    .fillMaxWidth(),
                elevation = 4.dp,
                shape = cardShape,
                backgroundColor = Color.White
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.app_new_logo),
                        contentDescription = "Launcher",
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(top = 12.dp)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_farm_santa_text),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(top = spacing.small)
                            .width(144.dp)
                            .height(42.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.sign_up),
                        color = Color.Cameron,
                        modifier = Modifier.padding(top = spacing.medium)
                    )

                    MobileNoTextField(
                        text = viewModel.mobileNumber,
                        countryCodePicker = countryCodePicker,
                        onDone = {
                            viewModel.countryCode.postValue(countryCodePicker.textView_selectedCountry.text.toString())
                            if (viewModel.validateMobile()) {
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
                        text = stringResource(id = R.string.sign_up),
                        textPadding = spacing.extraSmall,
                        textStyle = MaterialTheme.typography.caption,
                        onClick = {
                            viewModel.countryCode.postValue(countryCodePicker.textView_selectedCountry.text.toString())
                            if (viewModel.validateMobile()) {
                                requestOtp()
                            } else {
                                context.showToast(context.getString(R.string.phone_no_invalid))
                            }
                        }
                    )

                    Row(modifier = Modifier.padding(top = spacing.large)) {
                        Text(
                            text = stringResource(id = R.string.already_have_account),
                            color = Color.LightGray,
                            style = MaterialTheme.typography.caption
                        )

                        Text(
                            text = " ${stringResource(id = R.string.login)} ",
                            color = Color.Cameron,
                            textDecoration = TextDecoration.Underline,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.clickable { onSignIn() }
                        )
                    }


                    Text(
                        text = stringResource(id = R.string.terms_and_conditions_accept),
                        color = Color.LightGray,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = spacing.medium),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = stringResource(id = R.string.terms_and_conditions),
                        color = Color.Cameron,
                        style = MaterialTheme.typography.caption,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.padding(vertical = spacing.large)
                            .clickable(
                                onClick = onTerms,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = true
                                )
                            )
                    )
                }
            }

        }
    }
}