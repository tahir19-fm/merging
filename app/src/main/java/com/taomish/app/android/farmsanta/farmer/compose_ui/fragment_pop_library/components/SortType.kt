package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_pop_library.components


@Suppress("EnumEntryName")
enum class SortType(val value: String) {
    Alphabetically("Alphabetically"), By_Date("By Date");

    companion object {
        fun getNames(): List<String> = values().map { it.value }
    }
}