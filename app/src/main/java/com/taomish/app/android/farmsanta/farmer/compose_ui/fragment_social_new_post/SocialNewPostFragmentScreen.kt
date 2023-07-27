package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_new_post

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SocialNewPostViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_add_scouting_image_details.components.SelectedImagesCard
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_social_new_post.components.ChipsRow
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.saveFileInStorage
import com.taomish.app.android.farmsanta.farmer.utils.showToast


@Composable
fun SocialNewPostFragmentScreen(
    viewModel: SocialNewPostViewModel,
    onSelectCamera: () -> Unit,
    onSelectGallery: () -> Unit,
    onAddPost: () -> Unit,
) {
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val hasFocus = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var showDialog by remember { mutableStateOf(false) }
    BackHandler(hasFocus.value) { focusManager.clearFocus() }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                saveFileInStorage(
                    context = context,
                    uri = it
                )?.let { file -> viewModel.images.add(file) }
            }
        }
    )

    Column {
        CommonTopBar(
            activity = context as AppCompatActivity,
            isAddRequired = false,
            title = str(id = R.string.create_new_post),
            addClick = {}
        )

        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .padding(spacing.small)
                        .fillMaxSize()
                        .padding(spacing.small)
                ) {

                    SelectedImagesCard(
                        getImages = { viewModel.images.map { it.path } },
                        onAddClicked = { showDialog = true }
                    ) { viewModel.images.removeAt(it) }

                    Text(
                        text = stringResource(R.string.add_post_details),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(vertical = spacing.small)
                    )

                    Text(
                        text = stringResource(R.string.post_title),
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = spacing.small)
                    )

                    FarmerTextField(
                        modifier = Modifier
                            .padding(top = spacing.small)
                            .fillMaxSize(.75f),
                        backgroundColor = Color.LightGray.copy(alpha = .2f),
                        text = { viewModel.postTitleText.value },
                        onValueChange = { viewModel.postTitleText.postValue(it) },
                        placeholderText = "",
                        hasFocus = hasFocus,
                        shape = shape
                    )

                    Text(
                        text = stringResource(R.string.add_farm_talk_details),
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = spacing.small)
                    )

                    FarmerTextField(
                        modifier = Modifier
                            .padding(top = spacing.small)
                            .fillMaxSize(),
                        backgroundColor = Color.LightGray.copy(alpha = .2f),
                        boxHeight = 120.dp,
                        text = { viewModel.talkDetailText.value },
                        onValueChange = { viewModel.talkDetailText.postValue(it) },
                        placeholderText = "",
                        hasFocus = hasFocus,
                        shape = shape,
                        maxLines = 5
                    )

                    Text(
                        text = stringResource(R.string.add_tags),
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = spacing.small)
                    )

                    ChipsRow(
                        list = viewModel.tags,
                        onClickClose = { viewModel.deleteTag(it) }
                    )

                    FarmerTextField(
                        modifier = Modifier
                            .padding(top = spacing.small)
                            .fillMaxSize(),
                        backgroundColor = Color.LightGray.copy(alpha = .2f),
                        text = { viewModel.tagText.value },
                        onValueChange = { viewModel.tagText.postValue(it) },
                        placeholderText = "",
                        hasFocus = hasFocus,
                        shape = shape,
                        trailingIcon = {
                            Add {
                                if (viewModel.tagText.value.isEmpty()) {
                                    context.showToast(R.string.please_enter_text)
                                } else {
                                    viewModel.addTag()
                                }
                            }
                        }
                    )

                    /* FarmerDropDownMenu(
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(vertical = spacing.medium),
                         title = str(id = R.string.select_locations),
                         titleTextColor = Color.LightGray,
                         textStyle = MaterialTheme.typography.caption,
                         expanded = socialNewPostViewModel.locationExpanded,
                         selected = socialNewPostViewModel.selectedRegion,
                         options = socialNewPostViewModel.allRegionsList,
                         backgroundColor = Color.LightGray.copy(.3f),
                         backgroundScale = spacing.small,
                         onSelectOption = { _, _ -> socialNewPostViewModel.addRegion() }
                     )
 */
                    ChipsRow(
                        list = viewModel.selectedRegions,
                        onClickClose = { viewModel.deleteRegion(it) }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = spacing.medium),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundedShapeButton(
                            modifier = Modifier.padding(spacing.small),
                            text = str(id = R.string.add_post),
                            onClick = onAddPost,
                            textStyle = MaterialTheme.typography.body2
                        )
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
        }
    }

}

@Composable
fun Add(onClick: () -> Unit) {
    val spacing = LocalSpacing.current
    Text(
        text = str(id = R.string.add),
        color = Color.White,
        style = MaterialTheme.typography.body2,
        modifier = Modifier
            .padding(end = spacing.extraSmall)
            .background(
                color = Color.Cameron,
                shape = CircleShape
            )
            .padding(
                start = spacing.medium,
                end = spacing.medium,
                top = spacing.small,
                bottom = spacing.small
            )
            .clip(CircleShape)
            .clickable(onClick = onClick)
    )
}