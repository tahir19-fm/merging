package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.fragment.findNavController
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.db.ClearDBTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_introduction.FragmentIntroductionScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener

class FragmentIntroduction: FarmSantaBaseFragment() {
    override fun init() {

    }

    override fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun initViewsInLayout() {
    }

    override fun initListeners() {
    }

    override fun initData() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        initData()
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    FragmentIntroductionScreen {
                        clearDb()
                        findNavController().navigate(
                            FragmentIntroductionDirections.actionLandingToLogin()
                        )
                    }
                }
            }
        }
        return binding.root
    }
    private fun clearDb(){
        val task = ClearDBTask()
        task.context = requireContext()
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
            }
            override fun onTaskFailure(reason: String, errorMessage: String) {
                Toast.makeText(
                    requireContext(),
                    "Could not clear your data. Please try after some time",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        task.setLoadingMessage("Clearing data")
        task.setShowLoading(true)
        task.execute()
    }
}