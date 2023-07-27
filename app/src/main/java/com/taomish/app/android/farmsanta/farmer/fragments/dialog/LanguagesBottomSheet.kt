package com.taomish.app.android.farmsanta.farmer.fragments.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SelectLanguageViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.sheet_languages.LanguagesBottomSheetScreen
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.startAgain


@Suppress("UNCHECKED_CAST")
class LanguagesBottomSheet : BottomSheetDialogFragment() {

    private val viewModel by viewModels<SelectLanguageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.languages_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        view.findViewById<ComposeView>(R.id.languages_sheet_composeView).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LanguagesBottomSheetScreen(
                    viewModel = viewModel,
                    onBack = {
                        DataHolder.clearInstance()
                        requireActivity().startAgain()
                    }
                )
            }
        }
    }

    private fun initData() {
        if (DataHolder.getInstance().allLanguages != null) {
            viewModel.languagesDto.clear()
            viewModel.languagesDto.postValue(DataHolder.getInstance().allLanguages)
        } else viewModel.fetchLanguages(requireContext())

        viewModel.selectedLanguageIndex = try {
            AppPrefs(requireContext()).languageId.toInt() - 1
        } catch (e: Exception) {
            0
        }
    }

}