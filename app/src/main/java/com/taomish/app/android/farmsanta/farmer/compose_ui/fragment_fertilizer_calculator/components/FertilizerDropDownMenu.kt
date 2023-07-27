package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_fertilizer_calculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.FarmerDropDownContent
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Limeade
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalShapes
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.models.api.fertilizer.FertilizerSourceDetails
import com.taomish.app.android.farmsanta.farmer.utils.postValue


@Composable
fun FertilizerDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean>,
    selected: MutableState<FertilizerSourceDetails>,
    options: List<FertilizerSourceDetails>
) {
    val spacing = LocalSpacing.current
    val shape = LocalShapes.current.smallShape
    val selectedValue = remember {
        mutableStateOf(selected.value.fertilizerName ?: "")
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(
            indication = null,
            interactionSource = MutableInteractionSource()
        ) { expanded.postValue(true) }
    ) {
        Row(
            modifier = modifier
                .height(40.dp)
                .background(color = Color.LightGray.copy(alpha = .2f), shape = shape),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selected.value.fertilizerName ?: "",
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = spacing.small)
                    .fillMaxWidth(.9f)
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_dropdown_arrow),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(end = spacing.small)
                    .size(16.dp)
            )
        }

        FarmerDropDownContent(
            modifier = Modifier.wrapContentWidth(),
            expanded = expanded,
            selected = selectedValue,
            items = options.map { it.fertilizerName ?: "" },
            selectedItemColor = Color.Limeade,
            unselectedItemColor = Color.Gray,
            onSelectOption = { pos, _ -> selected.postValue(options[pos]) }
        )
    }
}