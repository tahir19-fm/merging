package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SocialWallViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_other_farmer_profile.OtherFarmerProfileFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentOtherFarmerProfileBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
class OtherFarmerProfileFragment : FarmSantaBaseFragment() {

    private val socialWallViewModel: SocialWallViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root
        init()
        initData()
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    OtherFarmerProfileFragmentScreen(
                        farmer = socialWallViewModel.selectedOtherFarmer,
                        posts = socialWallViewModel.socialMessages.filter {
                            it?.createdBy == socialWallViewModel.selectedOtherFarmer?.createdBy
                        },
                        onLikeClicked = { },
                        onCommentClicked = { },
                        onReadMoreClicked = { }
                    ) {
                        goToPostDetails(it)
                    }
                }
            }
        }
        return root
    }

    override fun init() {

    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?
    ): View? {
        return null
    }

    override fun initViewsInLayout() {

    }

    override fun initListeners() {

    }

    override fun initData() {
    }

    private fun goToPostDetails(message: Message?) {
        DataHolder.getInstance().dataObject = message
        findNavController().navigate(R.id.action_other_farmer_to_view_post)
    }
}