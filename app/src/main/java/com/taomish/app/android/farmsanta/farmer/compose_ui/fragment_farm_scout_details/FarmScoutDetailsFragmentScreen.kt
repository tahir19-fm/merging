package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.GetPlantPartTask
import com.taomish.app.android.farmsanta.farmer.compose_ui.FarmSantaTag
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.AdditionalField
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.CommonTopBar
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.farmerTabIndicatorOffset
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.str
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.tabIndicatorOffset
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.FarmScoutingViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_farm_scout_details.components.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.BlackOlive
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LGray
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LightSilver
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Nyanza
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil
import com.taomish.app.android.farmsanta.farmer.utils.NamesAndFormatsUtil.getUUIDName


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FarmScoutDetailsFragmentScreen(viewModel: FarmScoutingViewModel,onShare: () -> Unit={}) {
    val farmScouting = viewModel.selectedScouting.value
    val context = LocalContext.current

    if (farmScouting == null) {
        Box(modifier = Modifier.padding(1.dp))
        return
    }
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        NavigationBar(
            title = "${viewModel.crops.value[farmScouting.crop]?.cropName ?: "-"} " + str(
                id = R.string.query
            ), activity = context as AppCompatActivity,
            onShare = {
                onShare.invoke()
            }
        )
        LazyColumn {
            item {
                ScoutDetailImageCard(farmScouting = farmScouting)
                Plant(
                    text = "${str(id = R.string.plant)}: " + (viewModel.crops.value[farmScouting.crop]?.cropName
                        ?: "-")
                )
                Query(text = "${str(id = R.string.plant_part_issue)}: ${viewModel.plantPartsIssue.value}")
                if (farmScouting.advisoryExist) {
                    QuerySolved(
                        text = str(id = R.string.query_solution),
                        date = DateUtil().getDateMonthYearFormat(farmScouting.createdTimestamp)
                    )
                    TabView(viewModel = viewModel)
                    if (farmScouting.images.firstOrNull()?.comment != null && farmScouting.images.firstOrNull()?.comment != "") {
                        Personalized(text = str(id = R.string.personalized_notes))
                        Personalizednote(text = "${farmScouting.images.firstOrNull()?.comment}")
                    }
                } else {
                    Replyquestion(text = "Query Detailed")
                    QuerySolutionPending(text = farmScouting.caption)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBar(
    title: String,
    modifier: Modifier = Modifier,
    activity: AppCompatActivity,
    tintColor: Color = Color.BlackOlive,
    onBack: () -> Unit = {},
    onShare: () -> Unit = {}
) {
    val titleTextStyle = TextStyle(
        fontSize = 16.sp, fontWeight = FontWeight.Bold
    )
    TopAppBar(backgroundColor = Color.White, navigationIcon = {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "Back",
            modifier = modifier
                .padding(start = 10.dp)
                .clickable(
                    onClick = {
                        onBack.invoke()
                        activity
                            .findNavController(R.id.nav_host_fragment_activity_main)
                            .popBackStack()
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false
                    )
                ),
            tint = tintColor
        )
    }, title = { Text(text = title, style = titleTextStyle) }, actions = {
        IconButton(onClick = {
            onShare.invoke()

        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_share),
                modifier = Modifier.size(36.dp),
                contentDescription = "Action"
            )
        }
        IconButton(onClick = {
            /* Handle your second action here */
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_option),
                modifier = Modifier.size(36.dp),
                contentDescription = "Action 2"
            )
        }
    })
}

@Composable
fun Plant(text: String) {
    Text(
        text = text,
        color = Color.Cameron,
        modifier = Modifier.padding(start = 26.dp, top = 15.dp, bottom = 10.dp)
    )
}

@Composable
fun Query(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 26.dp, end = 26.dp)
            .background(Color.LGray, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Cameron)) {
                append("Query" + "\n\n")
            }
            append(text)
            Image(
                painter = painterResource(id = R.drawable.ic_message),
                contentDescription = "Image",
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp, top = 10.dp)
            )
        }

        Text(
            text = annotatedString,
            fontSize = 13.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(top = 10.dp, bottom = 16.dp, start = 10.dp, end = 10.dp)
        )
    }
}

@Composable
fun QuerySolved(text: String, date: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 26.dp, end = 16.dp)
                .background(color = Color.Cameron, shape = RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_new_logo_white),
                contentDescription = "Image",
                modifier = Modifier
                    .size(26.dp)
                    .padding(start = 6.dp)
                    .clip(CircleShape)
            )
            Text(
                text = text,
                color = Color.White, fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp, top = 6.dp, bottom = 6.dp, end = 6.dp),
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 26.dp)
        ) {
            Text(
                text = date,
                color = Color.Black,
            )
        }
    }
}

@Composable
fun Personalized(text: String) {
    Box(
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.app_new_logo_white),
                contentDescription = "Image",
                modifier = Modifier
                    .size(30.dp)
                    .padding(top = 3.dp)
            )
            Text(
                text = text,
                color = Color.Gray,
                modifier = Modifier.padding(start = 5.dp, top = 3.dp)
            )
        }
    }
}


@Composable
fun Personalizednote(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .background(Color.Nyanza)
    ) {
        Text(
            text = text,
            color = Color.Gray,
            fontSize = 13.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
        )
    }
}

@Composable
fun TabView(viewModel: FarmScoutingViewModel) {
    val tabs = listOf("Issue Characteristics", "Chemical Control")
    val selectedTabIndex = remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .heightIn(300.dp)
            .padding(start = 26.dp, end = 26.dp, bottom = 16.dp)
            .background(Color.LGray, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column {
            androidx.compose.material3.TabRow(selectedTabIndex.value, indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = Color.Cameron,
                    height = 2.dp,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value])
                )
            }) {
                tabs.forEachIndexed { index, title ->
                    androidx.compose.material3.Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index },
                        text = {
                            val textColor = if (selectedTabIndex.value == index) {
                                Color.Cameron // Custom selected tab text color
                            } else {
                                Color.Gray // Custom unselected tab text color
                            }
                            Text(text = title, color = textColor, textAlign = TextAlign.Center)
                        },
                        modifier = Modifier.background(Color.LGray)
                    )
                }
            }
            Box(
                modifier = Modifier.padding(
                    top = 20.dp, start = 20.dp, bottom = 20.dp, end = 20.dp
                )
            ) {
                if (selectedTabIndex.value == 0) {
                    Text(text = buildAnnotatedString {
                        var a = 0
                        if (!viewModel.advisory.value?.advisoryDetails?.localName.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Name: ")
                            }
                            append("${viewModel.advisory.value?.advisoryDetails?.localName}")
                            a++
                        }
                        if (!viewModel.advisory.value?.advisoryDetails?.symptomsOfAttack.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nSymptoms of Attack: ")
                            }
                            append("${viewModel.advisory.value?.advisoryDetails?.symptomsOfAttack}")
                            a++
                        }
                        if (!viewModel.advisory.value?.advisoryDetails?.favourableConditions.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nFavourable Conditions: ")
                            }
                            append("${viewModel.advisory.value?.advisoryDetails?.favourableConditions}")
                            a++
                        }
                        if (!viewModel.advisory.value?.advisoryDetails?.preventiveMeasures.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nPreventive Measures: ")
                            }
                            append("${viewModel.advisory.value?.advisoryDetails?.preventiveMeasures}")
                            a++
                        }
                        if (!viewModel.advisory.value?.advisoryDetails?.culturalMechanicalControl.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nCultural / Mechanical Control: ")
                            }
                            append("${viewModel.advisory.value?.advisoryDetails?.culturalMechanicalControl}")
                            a++
                        }
                        if (!viewModel.advisory.value?.advisoryDetails?.description.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nDescription: ")
                            }
                            append("${viewModel.advisory.value?.advisoryDetails?.description}")
                            a++
                        }
                        if (viewModel.advisory.value?.advisoryDetails?.culturalControl != null && viewModel.advisory.value?.advisoryDetails?.culturalControl != "") {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nCultural Control: ")
                            }
                            append("${viewModel.advisory.value?.advisoryDetails?.culturalControl}")
                            a++
                        }
                        if (a == 0) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("No Data Available")
                            }
                        }
                    }, fontSize = 13.sp, textAlign = TextAlign.Justify)
                } else if (selectedTabIndex.value == 1 && !viewModel.advisory.value?.advisoryTable.isNullOrEmpty()) {
                    Text(text = buildAnnotatedString {
                        var b = 0
                        if (!viewModel.advisory.value?.advisoryTable?.get(0)?.formulation.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Formulation: ")
                            }
                            append("${viewModel.advisory.value?.advisoryTable?.get(0)?.formulation}")
                            b++
                        }
                        if (!viewModel.advisory.value?.advisoryTable?.get(0)?.formulationType.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nFormulation Type: ")
                            }
                            append(viewModel.advisory.value?.advisoryTable?.get(0)?.formulationType?.let {
                                getUUIDName(
                                    it
                                )
                            })
                            b++
                        }
                        if (!viewModel.advisory.value?.advisoryTable?.get(0)?.cultivars.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nCultivars: ")
                            }
                            viewModel.advisory.value?.advisoryTable?.get(0)?.cultivars?.forEach { cultivar ->
                                append(cultivar?.let { getUUIDName(it) } + ",")
                            }
                            b++
                        }
                        if (!viewModel.advisory.value?.advisoryTable?.get(0)?.cultivarGroups.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nCultivar Groups: ")
                            }
                            viewModel.advisory.value?.advisoryTable?.get(0)?.cultivarGroups?.forEach { cultivarGrops ->
                                append(cultivarGrops?.let { getUUIDName(it) } + ",")
                            }
                            b++
                        }
                        if (viewModel.advisory.value?.advisoryTable?.get(0)?.dosage?.unit != null && viewModel.advisory.value?.advisoryTable?.get(
                                0
                            )?.dosage?.unit != 0
                        ) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nDosage: ")
                            }
                            append(
                                "${viewModel.advisory.value?.advisoryTable?.get(0)?.dosage?.unit} "
                            )
                            if (!viewModel.advisory.value?.advisoryTable?.get(0)?.dosage?.uom.isNullOrEmpty()) {
                                append("${viewModel.advisory.value?.advisoryTable?.get(0)?.dosage?.uom} ")
                            }
                            b++
                        }
                        if (!viewModel.advisory.value?.advisoryTable?.get(0)?.dosageMethod.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nDosage Method: ")
                            }
                            append(viewModel.advisory.value?.advisoryTable?.get(0)?.dosageMethod?.let {
                                getUUIDName(
                                    it
                                )
                            })
                            b++
                        }
                        if (viewModel.advisory.value?.advisoryTable?.get(0)?.waterRequirement?.unit != null && viewModel.advisory.value?.advisoryTable?.get(
                                0
                            )?.waterRequirement?.unit != 0
                        ) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nWater Requirement: ")
                            }
                            append(
                                "${viewModel.advisory.value?.advisoryTable?.get(0)?.waterRequirement?.unit} "
                            )
                            if (!viewModel.advisory.value?.advisoryTable?.get(0)?.waterRequirement?.uom.isNullOrEmpty()) {
                                append("${viewModel.advisory.value?.advisoryTable?.get(0)?.waterRequirement?.uom} ")
                            }
                            b++
                        }
                        if (!viewModel.advisory.value?.advisoryTable?.get(0)?.applicationMethod.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nApplication Method: ")
                            }
                            append(viewModel.advisory.value?.advisoryTable?.get(0)?.applicationMethod?.let {
                                getUUIDName(
                                    it
                                )
                            })
                            b++
                        }
                        if (viewModel.advisory.value?.advisoryTable?.get(0)?.preHarvestInterval?.unit != null && viewModel.advisory.value?.advisoryTable?.get(
                                0
                            )?.preHarvestInterval?.unit != 0
                        ) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nPre Harvest Interval: ")
                            }
                            append(
                                "${viewModel.advisory.value?.advisoryTable?.get(0)?.preHarvestInterval?.unit} "
                            )
                            if (!viewModel.advisory.value?.advisoryTable?.get(0)?.preHarvestInterval?.uom.isNullOrEmpty()) {
                                append("${viewModel.advisory.value?.advisoryTable?.get(0)?.preHarvestInterval?.uom} ")
                            }
                            b++
                        }
                        if (!viewModel.advisory.value?.advisoryTable?.get(0)?.toxicityLevel.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nToxicity Level: ")
                            }
                            append(viewModel.advisory.value?.advisoryTable?.get(0)?.toxicityLevel?.let {
                                getUUIDName(
                                    it
                                )
                            })
                            b++
                        }
                        if (!viewModel.advisory.value?.advisoryTable?.get(0)?.modeOfAction.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\n\nMode of Action: ")
                            }
                            append(viewModel.advisory.value?.advisoryTable?.get(0)?.modeOfAction?.let {
                                getUUIDName(
                                    it
                                )
                            })
                            b++
                        }
                        if (b == 0) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("No Data Available")
                            }
                        }
                    }, fontSize = 13.sp, textAlign = TextAlign.Justify)
                } else {
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("No Data Available")
                        }
                    }, fontSize = 13.sp, textAlign = TextAlign.Justify)
                }
            }
        }
    }
}

@Composable
fun Replyquestion(text: String) {
    Box(
        modifier = Modifier.padding(start = 26.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Row {
            Text(
                text = text,
                color = Color.Cameron,
                modifier = Modifier.padding(start = 5.dp, top = 3.dp)
            )
        }
    }
}

@Composable
fun QuerySolutionPending(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 26.dp, end = 26.dp)
            .heightIn(min = 200.dp)
            .background(Color.LGray)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 8.dp, top = 5.dp, bottom = 5.dp, end = 8.dp),
        )
    }
}

