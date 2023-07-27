package com.taomish.app.android.farmsanta.farmer.compose_ui.sheet_languages

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.core.os.LocaleListCompat
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SelectLanguageViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.ApiConstants.Language.EN
import com.taomish.app.android.farmsanta.farmer.constants.ApiConstants.Language.FR
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.utils.getLanguageIcon
import java.util.*


@Composable
fun LanguagesBottomSheetScreen(viewModel: SelectLanguageViewModel, onBack: () -> Unit) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val shape = LocalShapes.current.topRoundedMediumShape

    LazyColumn(
        modifier = Modifier
            .padding(spacing.medium)
            .background(color = Color.White, shape = shape)
    ) {

        itemsIndexed(viewModel.languagesDto) { index, language ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RectangleShape)
                    .clickable(role = Role.Button) {
                        if (viewModel.selectedLanguageIndex != index) {
                            viewModel.selectedLanguageIndex = index
                            val country = Locale.getDefault().country
                            val lang = "${if (index == 0) EN else FR}-$country"
                            AppPrefs(context).languageId = if (index == 0) "1" else "2"
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(lang)
                            )
                            onBack()
                        }
                    }
                    .padding(spacing.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.medium)
            ) {
                Icon(
                    painter = painterResource(id = language.id.getLanguageIcon()),
                    contentDescription = null,
                    tint = if (viewModel.selectedLanguageIndex == index) Color.Cameron else Color.Black
                )

                Text(
                    text = language.name,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (viewModel.selectedLanguageIndex == index) Color.Cameron else Color.Black
                    )
                )
            }
        }
    }
}