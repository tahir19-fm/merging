package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_nutri_source.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.NutriSourceViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.RiceFlower
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun NutriSourceBottomSheet(viewModel: NutriSourceViewModel, onCloseClicked: () -> Unit) {
    val spacing = LocalSpacing.current
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val onPageChange: (Int) -> Unit = {
        scope.launch { pagerState.animateScrollToPage(it) }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            SheetHeader(onCloseClicked = onCloseClicked)

            Divider(thickness = .3.dp, color = Color.LightGray)

            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .fillMaxHeight()
                        .background(Color.RiceFlower),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = str(id = R.string.select_categories),
                        color = Color.Cameron,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .padding(spacing.small)
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource(),
                                onClick = { onPageChange(0) }
                            )
                    )

                    if (viewModel.selectedCategory.value == null) {
                        Row(
                            modifier = Modifier
                                .padding(spacing.small)
                                .fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_info_outlined),
                                contentDescription = null,
                                tint = Color.LightGray,
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(top = spacing.extraSmall)
                            )

                            Text(
                                text = str(id = R.string.select_categories_msg),
                                color = Color.Gray,
                                style = MaterialTheme.typography.overline,
                                modifier = Modifier.padding(top = spacing.extraSmall)
                            )
                        }
                    } else {
                        Text(
                            text = str(id = R.string.products),
                            color = Color.Cameron,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .padding(spacing.small)
                                .clickable(
                                    indication = null,
                                    interactionSource = MutableInteractionSource(),
                                    onClick = { onPageChange(1) }
                                )
                        )
                    }
                }

                HorizontalPager(
                    modifier = Modifier.fillMaxWidth(),
                    state = pagerState,
                    count = 2,
                    userScrollEnabled = false
                ) { page ->
                    if (page == 0) {
                        Categories(viewModel = viewModel)
                    } else {
                        Products(viewModel = viewModel)
                    }
                }
            }
        }

        SheetFooter(
            onClickApply = { onCloseClicked() },
            onClickCancel = { onCloseClicked() }
        )
    }
}