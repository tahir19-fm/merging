package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_pop.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing


@Composable
fun SectionRow(
    @StringRes heading1: Int,
    @DrawableRes resId1: Int,
    @StringRes heading2: Int,
    @DrawableRes resId2: Int,
    onClickFirst: () -> Unit,
    onClickSecond: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
    ) {
        PopSection(
            modifier = Modifier
                .fillMaxWidth(.5f)
                .padding(spacing.extraSmall),
            name = heading1,
            resId = resId1,
            onClick = onClickFirst
        )

        PopSection(
            modifier = Modifier
                .padding(spacing.extraSmall)
                .fillMaxWidth(),
            name = heading2,
            resId = resId2,
            onClick = onClickSecond
        )
    }
}