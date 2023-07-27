package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_pop.components

sealed class Section(val tabIndex: Int) {

    object Climate: Section(0)

    object Cultivars: Section(1)

    object SeedTreatment: Section(2)

    object CroppingProcess: Section(3)

    object IPM: Section(4)

    object Nutrition: Section(5)

    object Harvest: Section(6)

}
