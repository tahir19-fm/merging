package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_about_us

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.BuildConfig
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun AboutUsScreen() {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CommonTopBar(
            title = str(id = R.string.about_us),
            activity = context as AppCompatActivity,
            isAddRequired = false,
            addClick = {}
        )

        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.fs_new_mascot_logo),
            contentDescription = "Mascot"
        )


        Image(
            modifier = Modifier
                .padding(spacing.small)
                .width(240.dp)
                .height(42.dp),
            painter = painterResource(id = R.drawable.new_logo_farm_santa),
            contentDescription = "Heading"
        )


        Text(
            text = "Version : ${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.body2.copy(
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(horizontal = spacing.medium, vertical = spacing.small)
                .fillMaxWidth()
        )

        Text(
            text = str(id = R.string.about_us),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(horizontal = spacing.medium, vertical = spacing.small)
                .fillMaxWidth()
        )


        Text(
            text = str(id = R.string.about_us_description),
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Justify
            ),
            modifier = Modifier
                .padding(horizontal = spacing.medium)
                .fillMaxWidth()
        )
    }
}