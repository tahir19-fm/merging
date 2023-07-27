package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

sealed class Screen {

    object AskQueries : Screen()

    object MyQueries : Screen()

    object FarmTalks : Screen()

    object Pops : Screen()

    object CropAdvisory : Screen()

    object NutriSource : Screen()

    object MarketAnalysis : Screen()

    object CropCalendar : Screen()

    object MyCrops : Screen()

    object Weather : Screen()

    object Diseases : Screen()

    object FertilizerCalculator : Screen()
}
