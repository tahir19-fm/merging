package com.taomish.app.android.farmsanta.farmer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.GetMarketDataTask
import com.taomish.app.android.farmsanta.farmer.background.GetMarketPriceTask
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels.MarketAnalysisViewModel
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_market_analysis.MarketAnalysisFragmentScreen
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.MyApp
import com.taomish.app.android.farmsanta.farmer.databinding.FragmentHomeBinding
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.home.MarketDto
import com.taomish.app.android.farmsanta.farmer.models.api.home.Price
import com.taomish.app.android.farmsanta.farmer.utils.isNotEmptyOrNull
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

@Suppress("UNCHECKED_CAST")
class MarketAnalysisFragment : FarmSantaBaseFragment() {

    private val viewModel: MarketAnalysisViewModel by activityViewModels()
    private lateinit var mContext: Context

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
                    MarketAnalysisFragmentScreen(
                        viewModel = viewModel,
                        fetchMarketData = this@MarketAnalysisFragment::fetchMarketAnalysisData
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
        savedState: Bundle?
    ): View? {
        return null
    }

    override fun initViewsInLayout() {}

    override fun initListeners() {}

    override fun initData() {
        mContext = requireContext()
        fetchMarketPrice()
    }


    private fun fetchMarketPrice() {
        if (!DataHolder.getInstance().priceArrayList.isNullOrEmpty()) {
            viewModel.prices.postValue(DataHolder.getInstance().priceArrayList)
            viewModel.selectedPrice.postValue(viewModel.prices.value?.firstOrNull())
            fetchMarketAnalysisData()
            return
        }
        val task = GetMarketPriceTask()
        task.context = mContext
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    val list = data.toList() as List<Price>
                    viewModel.prices.postValue(ArrayList(list))
                    DataHolder.getInstance().priceArrayList = viewModel.prices.value
                    list.isNotEmptyOrNull {
                        viewModel.selectedPrice.postValue(it.firstOrNull())
                        fetchMarketAnalysisData()
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                showToast("reason: $reason \nerror: $errorMessage")
                viewModel.prices.postValue(ArrayList())
            }
        })
        task.execute()
    }

    private fun fetchMarketAnalysisData() {
        val task = GetMarketDataTask()
        task.context = mContext
        task.setShowLoading(true)
        task.setLoadingMessage(getString(R.string.loading_market_data))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is MarketDto) {
                    viewModel.marketData = data
                    viewModel.setXAndYValues()
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                viewModel.marketData = null
                showToast("Reason->$reason\nmessage: $errorMessage")
            }
        })

        task.execute(
            viewModel.selectedPrice.value?.commodityName,
            viewModel.selectedPrice.value?.commodityType,
            viewModel.selectedPeriod
        )
    }
}