package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_nutri_source

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerTextField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.SearchLeadingIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.NutriSourceViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_advisory_inbox.FilterIcon
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_nutri_source.components.NutriSourceBottomSheet
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_nutri_source.components.NutriSourceItem
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.expand
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NutriSourceFragmentScreen(
    nutriSourceViewModel: NutriSourceViewModel,
    goToNutriSourceDetails: () -> Unit,
    goToSavedNutriSource: () -> Unit
) {
    val spacing = LocalSpacing.current
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    BackHandler(sheetState.isVisible || hasFocus.value) {
        focusManager.clearFocus()
        scope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            NutriSourceBottomSheet(
                viewModel = nutriSourceViewModel,
                onCloseClicked = { scope.launch { sheetState.hide() } }
            )
        },
        scrimColor = Color.Cameron.copy(alpha = .2f),
        sheetBackgroundColor = Color.White
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = spacing.medium)
                .background(Color.LightGray.copy(alpha = .2f))
        ) {
            item {
                Row(
                    modifier = Modifier
                        .padding(spacing.small)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FarmerTextField(
                        modifier = Modifier.fillMaxWidth(.85f),
                        text = { nutriSourceViewModel.searchText.value },
                        onValueChange = { nutriSourceViewModel.searchText.postValue(it) },
                        placeholderText = str(id = R.string.search),
                        shape = CircleShape,
                        leadingIcon = { SearchLeadingIcon() },
                        backgroundColor = Color.White,
                        hasFocus = hasFocus,
                        trailingIcon = {
                            FilterIcon(iconSize = 16.dp) {
                                scope.launch { sheetState.expand() }
                            }
                        }
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_bookmark),
                        contentDescription = null,
                        tint = Color.Cameron,
                        modifier = Modifier
                            .size(40.dp)
                            .background(color = Color.White, shape = CircleShape)
                            .padding(spacing.extraSmall)
                            .clip(CircleShape)
                            .clickable(onClick = goToSavedNutriSource)
                    )
                }
            }

            items(10) {
                NutriSourceItem(onNutriSourceClicked = goToNutriSourceDetails)
            }
        }
    }
}