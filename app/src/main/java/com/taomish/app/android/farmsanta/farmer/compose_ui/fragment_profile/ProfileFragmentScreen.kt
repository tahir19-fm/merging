package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.ImageSelectMethodDialog
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.RoundedShapeButton
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.ProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile.components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_profile_edit.components.FileOrUrlImage
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.utils.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileFragmentScreen(
    viewModel: ProfileViewModel,
    onEditClicked: () -> Unit,
    onBackPressed: () -> Unit,
    onViewFarm: (Int) -> Unit,
    onSelectGallery: () -> Unit,
    onSelectCamera: () -> Unit,
    onDone: () -> Unit,
) {
    if (viewModel.farmer.value == null) {
        Box(modifier = Modifier.padding(1.dp)) { }
        return
    }

    val animationSpec = remember {
        Animatable(0f)
            .run {
                TweenSpec<Float>(durationMillis = 300)
            }
    }
    val spacing = LocalSpacing.current
    val largeSheetShape = LocalShapes.current.topRoundedLargeShape
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
    skipHalfExpanded = true,
    animationSpec = animationSpec)
    val scope = rememberCoroutineScope()
    val closeSheet: () -> Unit = { scope.launch { sheetState.hide() } }
    var showDialog by remember { mutableStateOf(false) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                it.asBitmap(context)?.let { bitmap -> viewModel.profileBitmap = bitmap }
            }
        }
    )

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            ProfileBottomSheetContent(
                viewModel = viewModel,
                onClickClose = closeSheet
            )
        },
        sheetShape = largeSheetShape,
        scrimColor = Color.Cameron.copy(alpha = .2f),
        sheetBackgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Cameron)
        ) {
            Row(
                modifier = Modifier
                    .padding(LocalSpacing.current.medium)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = str(id = R.string.back),
                    tint = Color.White,
                    modifier = Modifier
                        .clickable(
                            onClick = onBackPressed,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = false
                            )
                        )
                )
                Text(
                    text = viewModel.farmer.value?.firstName.notNull(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 8.dp)
                        .clickable(
                            onClick = {},
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = true
                            )
                        ),
                    textAlign = TextAlign.Start
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(152.dp)
                            .background(color = Color.Cameron)
                    ) {

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .fillMaxHeight(.5f)
                                .padding(top = spacing.small)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(
                                        topStart = spacing.medium,
                                        topEnd = spacing.medium
                                    )
                                )
                        ) { }


                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxHeight()
                                .wrapContentWidth()
                        ) {
                            FileOrUrlImage(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(spacing.small)
                                    .size(136.dp)
                                    .clip(CircleShape)
                                    .clickable(
                                        indication = null,
                                        interactionSource = MutableInteractionSource()
                                    ) { showDialog = true },
                                bitmap = viewModel.profileBitmap,
                                imageUrl = viewModel.farmer.value?.profileImage
                            )

                            if (viewModel.isImageSelected) {
                                IconButton(
                                    modifier = Modifier
                                        .background(color = Color.White, shape = CircleShape)
                                        .align(Alignment.TopEnd),
                                    onClick = onDone
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_done_outline),
                                        contentDescription = null,
                                        tint = Color.Cameron,
                                        modifier = Modifier.clip(CircleShape)
                                    )
                                }
                            }
                        }

                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = spacing.small, end = spacing.small)
                                .clip(CircleShape)
                                .clickable { scope.launch { sheetState.show() } }
                        )

                    }

                    Text(
                        text = "${viewModel.farmer.value?.firstName} ${viewModel.farmer.value?.lastName}",
                        color = Color.Cameron,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = spacing.medium)
                    )

                    RoundedShapeButton(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = spacing.medium),
                        text = stringResource(id = R.string.edit),
                        textPadding = spacing.tiny
                    ) { onEditClicked() }

                    MyLands(
                        modifier = Modifier.padding(spacing.small),
                        lands = viewModel.farmer.value?.lands.orEmpty(),
                        goToMapView = onViewFarm
                    )

                    Divider(thickness = .3.dp, color = Color.LightGray)

                    ProfileMyCrops(myCrops = viewModel.myCrops)

                    Spacer(modifier = Modifier.padding(vertical = spacing.small))

                    Field(
                        metaData = str(id = R.string.name),
                        value = "${viewModel.farmer.value?.firstName} ${viewModel.farmer.value?.lastName}",
                        iconId = R.drawable.ic_profile_name
                    )

                    val mobileNumber = viewModel.farmer.value!!.mobile.notNull()

                    Field(
                        metaData = str(id = R.string.mobile_number),
                        value = mobileNumber,
                        iconId = R.drawable.ic_profile_mobile
                    )

                    Field(
                        metaData = str(id = R.string.date_of_birth),
                        value = DateUtil().getDateMonthYearFormat(viewModel.farmer.value!!.dateOfBirth)
                            .notNull(),
                        iconId = R.drawable.ic_profile_date
                    )

                    Field(
                        metaData = str(id = R.string.education),
                        value = viewModel.farmer.value?.education.notNull(),
                        iconId = R.drawable.ic_profile_education
                    )

                    Field(
                        metaData = str(id = R.string.gender),
                        value = viewModel.farmer.value?.gender.notNull(),
                        iconId = R.drawable.ic_profile_gender
                    )

                    val territory = viewModel.farmer.value?.territory?.joinToString {
                        DataHolder.getInstance().territoryMap[it]?.territoryName ?: ""
                    } ?: ""

                    LocationField(
                        address = viewModel.farmer.value?.address ?: "",
                        country = territory,
                        state = viewModel.region,
                        pin = viewModel.farmer.value?.pin ?: "",
                        district = viewModel.farmer.value?.district ?: "",
                        subDistrict = viewModel.farmer.value?.subDistrict ?: "",
                        village = viewModel.farmer.value?.village ?: ""
                    )

                    Field(
                        metaData = str(id = R.string.data_source),
                        value = viewModel.farmer.value?.dataSource?.ifEmpty { "-" } ?: "-",
                        iconId = R.drawable.ic_profile_education
                    )

                    Field(
                        metaData = str(id = R.string.have_smart_phone),
                        value = str(id = if (viewModel.farmer.value?.hasPhone == true) R.string.yes else R.string.no),
                        iconId = R.drawable.ic_profile_mobile
                    )
                }
            }
        }
    }

    if (showDialog) {
        ImageSelectMethodDialog(
            onSelectCamera = {
                onSelectCamera()
                showDialog = false
            },
            onSelectGallery = {
                if (ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable()) {
                    photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                } else onSelectGallery()
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}