package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseActivity
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.SignUpAndEditProfileViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view.MapViewFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view.components.ImmutableList
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Coordinate
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Land
import com.taomish.app.android.farmsanta.farmer.models.view_model.LocationRequestResult
import com.taomish.app.android.farmsanta.farmer.utils.*


@Suppress("UNCHECKED_CAST")
class MapViewFragment : FarmSantaBaseFragment() {

    private val viewModel: SignUpAndEditProfileViewModel by activityViewModels()
    private lateinit var mContext: Context
    private var viewLand: Land? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root
        init()
        initData()
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MyApp {
                    MapViewFragmentScreen(
                        viewModel = viewModel,
                        viewLand = viewLand,
                        onDone = { onDone() }
                    )
                }
            }
        }
        return root
    }

    override fun init() {}

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedState: Bundle?,
    ): View? {
        return null
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        mContext = requireContext()
        val args = MapViewFragmentArgs.fromBundle(
            arguments ?: Bundle()
        )
        args.selectedLandIndex.let {
            DataHolder.getInstance().selectedFarmer?.let { farmer ->
                farmer.lands?.getOrNull(it)?.let {
                    viewLand = it
                    viewModel.coordinates.postValue(ImmutableList(it.coordinates.map { c -> c.toLatLng() }))
                }
            }
        }

        if (viewModel.selectedLandIndex >= 0) {
            DataHolder.getInstance().selectedFarmer?.let { farmer ->
                farmer.lands?.getOrNull(viewModel.selectedLandIndex)?.let {
                    viewLand = it
                    viewModel.coordinates.postValue(ImmutableList(it.coordinates.map { c -> c.toLatLng() }))
                }
            }
        }

        DataHolder.getInstance().lastKnownLocation?.let {
            viewModel.selectedCountryLatLng = LatLng(it.latitude, it.longitude)
        }
        if (DataHolder.getInstance().lastKnownLocation == null) {
            if (requireActivity() is FarmSantaBaseActivity) {
                (requireActivity() as FarmSantaBaseActivity).requestForLocation()
                (requireActivity() as FarmSantaBaseActivity)
                    .locationDataMutableLiveData.observe(viewLifecycleOwner) { (location): LocationRequestResult.LocationData ->
                        DataHolder.getInstance().lastKnownLocation = location
                    }
            }
        }

    }

    private fun onDone() {
        if (viewModel.selectedLandIndex > 0 && viewModel.selectedLandIndex < viewModel.lands.size) {
            viewModel.lands[viewModel.selectedLandIndex].apply {
                coordinates = viewModel.coordinates.value.mapIndexed { i, latLng ->
                    Coordinate().apply {
                        index = i
                        latitude = latLng.latitude
                        longitude = latLng.longitude
                    }
                }
                viewModel.coordinates.value.getCenterCoordinate()?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                    viewModel.farmLocation = it.getLocationName(mContext)
                }
            }
        }
        findNavController().popBackStack()
    }
}