package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_pop

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RemoteImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.POPViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_pop.components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ViewPopFragmentScreen(
    viewModel: POPViewModel,
    onBookmark: () -> Unit,
    onShare: () -> Unit,
    goToZoomImage: (String) -> Unit,
    onClickSection: (Section) -> Unit
) {
    val spacing = LocalSpacing.current
    val sheetShape = LocalShapes.current.topRoundedMediumShape
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()



    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            ViewPopSheetContent(
                pop = viewModel.pop.value,
                onSaveClicked = { if (!viewModel.pop.value?.bookmarked!!) onBookmark() },
                onShareClicked = onShare
            )
        },
        sheetShape = sheetShape,
        scrimColor = Color.Cameron.copy(alpha = .2f),
        sheetBackgroundColor = Color.White
    ) {
        Column {
            CommonTopBar(
                title = stringResource(id = R.string.back),
                activity = context as AppCompatActivity,
                isAddRequired = false,
                addClick = {}
            )

            LazyColumn(modifier = Modifier.padding(bottom = spacing.medium)) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacing.small)
                            .height(300.dp)
                    ) {
                        val imageLink = URLConstants.S3_IMAGE_BASE_URL +
                                viewModel.pop.value
                                    ?.photos?.elementAtOrNull(0)
                                    ?.fileName.toString()

                        RemoteImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = spacing.small)
                                .clip(RectangleShape),
                            imageLink = imageLink,
                            contentScale = ContentScale.FillBounds,
                            error = R.mipmap.img_default_pop
                        )

                        Icon(
                            modifier = Modifier
                                .padding(end = spacing.small, bottom = spacing.medium)
                                .align(Alignment.BottomEnd)
                                .size(32.dp)
                                .background(color = Color.Black.copy(alpha = .5f), shape = CircleShape)
                                .padding(spacing.extraSmall)
                                .clip(CircleShape)
                                .clickable { goToZoomImage(imageLink) },
                            painter = painterResource(id = R.drawable.ic_full_screen),
                            contentDescription = null,
                            tint = Color.White
                        )

                    }

                    ActionsRow(
                        getPopName = {
                            viewModel.crops[viewModel.pop.value?.crop ?: ""]?.cropName ?: "N/A"
                        },
                        isBookmarked = viewModel.pop.value?.bookmarked ?: false,
                        onBookmark = { if (viewModel.pop.value?.bookmarked != true) onBookmark() },
                        onShare = onShare,
                        onMoreOptionClicked = { scope.launch { sheetState.show() } }
                    )

                    SectionRow(
                        heading1 = R.string.climate,
                        resId1 = R.drawable.climate,
                        heading2 = R.string.cultivars,
                        resId2 = R.drawable.cultivars,
                        onClickFirst = { onClickSection(Section.Climate) },
                        onClickSecond = { onClickSection(Section.Cultivars) }
                    )

                    SectionRow(
                        heading1 = R.string.seed_treatment,
                        resId1 = R.drawable.seed_treatment,
                        heading2 = R.string.cropping_process,
                        resId2 = R.drawable.cropping_process,
                        onClickFirst = { onClickSection(Section.SeedTreatment) },
                        onClickSecond = { onClickSection(Section.CroppingProcess) }
                    )

                    SectionRow(
                        heading1 = R.string.ipm,
                        resId1 = R.drawable.ipm,
                        heading2 = R.string.nutrition,
                        resId2 = R.drawable.nutrition,
                        onClickFirst = { onClickSection(Section.IPM) },
                        onClickSecond = { onClickSection(Section.Nutrition) }
                    )

                    PopSection(
                        modifier = Modifier
                            .padding(spacing.small)
                            .fillMaxWidth(.5f)
                            .padding(spacing.extraSmall),
                        name = R.string.harvest,
                        resId = R.drawable.ic_harvest,
                        onClick = { onClickSection(Section.Harvest) }
                    )

                }
            }
        }
    }
}