package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.GetLanguagesTask
import com.taomish.app.android.farmsanta.farmer.background.GetTerritoryTask
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.master.Language
import com.taomish.app.android.farmsanta.farmer.models.api.master.Territory
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

@Suppress("UNCHECKED_CAST")
class SelectLanguageViewModel : ViewModel() {

    val selectedCountry = mutableStateOf("")
    val countryDropDownExpanded = mutableStateOf(false)
    val selectedLanguage = mutableStateOf("")
    var selectedLanguageIndex by mutableStateOf(-1)
    val languageDropDownExpanded = mutableStateOf(false)
    val selectedTerritoryDto = mutableStateOf<Territory?>(null)
    val territoriesDto = mutableStateListOf<Territory>()

    val languages = mutableListOf<String>()
    val territories = mutableListOf<String>()
    val languagesDto = mutableStateListOf<Language>()

    fun fetchTerritory(context: Context) {
        territoriesDto.clear()
        val task = GetTerritoryTask()
        task.context = context
        task.setShowLoading(true)
        task.setLoadingMessage(context.getString(R.string.territories_loading_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    val list = data.toList() as List<Territory>?
                    if (!list.isNullOrEmpty()) {
                        territoriesDto.clear()
                        territories.clear()
                        territoriesDto.addAll(list)
                        territories.addAll(list.map { it.territoryName ?: "" })
                        DataHolder.getInstance().setAllTerritories(list.toTypedArray())
                        selectedCountry.postValue(list.firstOrNull()?.territoryName ?: "")
                        selectedTerritoryDto.postValue(list.firstOrNull())
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                context.showToast(context.getString(R.string.fetch_territories_error_msg))
            }
        })
        task.execute()
    }

    fun fetchLanguages(context: Context) {
        languages.clear()
        val task = GetLanguagesTask()
        task.context = context
        task.setShowLoading(true)
        task.setLoadingMessage(context.getString(R.string.language_loading_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    val list = data.toList() as List<Language>?
                    if (!list.isNullOrEmpty()) {
                        DataHolder.getInstance().setLanguages(list.toTypedArray())
                        languages.clear()
                        languages.addAll(list.map { it.name })
                        languagesDto.clear()
                        languagesDto.addAll(list)
                        val id = AppPrefs(context).languageId
                        if (id == null) {
                            selectedLanguageIndex = 0
                            selectedLanguage.postValue(languages[0])
                        } else {
                            try {
                                list.find { it.id == id.toInt() }?.let {
                                    selectedLanguageIndex = list.indexOf(it)
                                    selectedLanguage.postValue(it.name)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) { }
        })
        task.execute()
    }


    fun setLanguageId(context: Context) {
        val appPrefs = AppPrefs(context)
        val id = DataHolder.getInstance().allLanguages[selectedLanguageIndex].id
        appPrefs.languageId = id.toString()
    }
}