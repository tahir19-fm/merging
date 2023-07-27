package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_crop_calendar.components

import androidx.annotation.DrawableRes
import com.taomish.app.android.farmsanta.farmer.R


@DrawableRes
fun Int?.getWeekIcon() : Int {
    return when(this) {
        null -> R.drawable.ic_seed_selection
        1 -> R.drawable.ic_crop_planning
        2 -> R.drawable.ic_seed_selection
        3 -> R.drawable.ic_seed_selection
        4 -> R.drawable.ic_field_preparation
        5 -> R.drawable.ic_fertilizer_requirement
        6 -> R.drawable.ic_rainfall
        7 -> R.drawable.ic_field_preparation
        8 -> R.drawable.seed_treatment
        9 -> R.drawable.seed_treatment
        10 -> R.drawable.ic_crop_planning
        11 -> R.drawable.ipm
        12 -> R.drawable.ic_field_preparation
        13 -> R.drawable.ipm
        14 -> R.drawable.seed_treatment
        else -> R.drawable.ic_field_preparation
    }
}


@DrawableRes
fun Int?.getStageIcon() : Int {
    return when(this) {
        null -> R.drawable.ic_vegetative_stage_banner
        -1 -> R.drawable.ic_pre_seeding_stage_banner
        0 -> R.drawable.ic_seeding_stage_banner
        1 -> R.drawable.ic_vegetative_stage_banner
        else -> R.drawable.ic_pre_seeding_stage_banner
    }
}