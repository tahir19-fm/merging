package com.taomish.app.android.farmsanta.farmer.models.view_model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.GetCurrentFarmerTask
import com.taomish.app.android.farmsanta.farmer.background.GetTerritoryTask
import com.taomish.app.android.farmsanta.farmer.background.SaveFarmerTask
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_weather.components.WEATHER_TODAY
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.disease.Disease
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.FarmScouting
import com.taomish.app.android.farmsanta.farmer.models.api.home.MarketDto
import com.taomish.app.android.farmsanta.farmer.models.api.home.Price
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster
import com.taomish.app.android.farmsanta.farmer.models.api.master.Territory
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage
import com.taomish.app.android.farmsanta.farmer.models.api.weather.WeatherData
import com.taomish.app.android.farmsanta.farmer.utils.MarketDataFormatter.getXValuesForMonth
import com.taomish.app.android.farmsanta.farmer.utils.MarketDataFormatter.getXValuesForWeek
import com.taomish.app.android.farmsanta.farmer.utils.MarketDataFormatter.getXValuesForYears
import com.taomish.app.android.farmsanta.farmer.utils.MarketDataFormatter.getYValues
import com.taomish.app.android.farmsanta.farmer.utils.postValue
import com.taomish.app.android.farmsanta.farmer.utils.showToast

@Suppress("UNCHECKED_CAST")
@SuppressLint("MutableCollectionMutableState")
class HomeViewModel : ViewModel() {

    private val mProfile: MutableLiveData<Farmer> = MutableLiveData()

    val searchText = MutableLiveData("")
    var weatherData by mutableStateOf<WeatherData?>(null)
    var weatherSelectedTag by mutableStateOf(WEATHER_TODAY)

    val selectedPrice = mutableStateOf<Price?>(null)
    var marketData by mutableStateOf<MarketDto?>(null)
    private val xValues = mutableStateOf<List<String>>(emptyList())
    private val yValues = mutableStateOf<List<Float>>(emptyList())
    private val pointsValue = mutableStateOf<List<Float>>(emptyList())
    private val periods = listOf("1 week", "1 month", "1 year", "5 year")
    val periodLabels = listOf("1W", "1M", "1Y", "5Y")
    val selectedPeriodIndex = mutableStateOf(0)

    fun getXValues(): List<String> = xValues.value
    fun getYValues(): List<Float> = yValues.value
    fun getPointsValues(): List<Float> = pointsValue.value

    val selectedPeriod: String
        get() = periods[selectedPeriodIndex.value]

    val talks = mutableStateOf<ArrayList<Message?>?>(null)
    val profiles = mutableStateOf<Map<String, String>>(emptyMap())
    val prices = mutableStateOf<List<Price>?>(null)
    val cropAdvisories = mutableStateOf<ArrayList<CropAdvisory>?>(null)
    var userPops = mutableStateOf<List<PopDto>?>(null, policy = neverEqualPolicy())
    var farmsScouting = mutableStateOf<List<FarmScouting?>?>(null)
    var myCrops = mutableStateListOf<CropMaster>()
    val growthStages = mutableStateListOf<CropStage>()
    val crops = mutableStateListOf<CropMaster>()
    val diseases = mutableStateListOf<Disease>()
    var selectedDisease by mutableStateOf<Disease?>(null)
    var selectedDiseaseCrop by mutableStateOf<CropMaster?>(null)
    val cropPops = mutableStateOf<List<PopDto>>(emptyList())

    fun getProfile(): LiveData<Farmer> {
        return mProfile
    }

    fun getPrices(): List<Price>? = prices.value
    fun getCropAdvisories(): ArrayList<CropAdvisory>? = cropAdvisories.value
    fun getUserPops(): List<PopDto>? = userPops.value
    fun getFarmsScouting(): List<FarmScouting?>? = farmsScouting.value
    fun getCropPops(): List<PopDto> = cropPops.value

    fun setProfile(profile: Farmer) {
        mProfile.postValue(profile)
    }

    fun fetchTerritory(context: Context) {
        val task = GetTerritoryTask()
        task.context = context
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    val list = data.toList() as List<Territory>?
                    if (!list.isNullOrEmpty()) {
                        DataHolder.getInstance().setAllTerritories(list.toTypedArray())
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                context.showToast(context.getString(R.string.fetch_territories_error_msg))
            }
        })
        task.execute()
    }

    fun getWeatherDescription(): String {
        return if (weatherSelectedTag == WEATHER_TODAY) {
            weatherData?.weatherDetails?.current?.weather?.firstOrNull()?.description
        } else {
            weatherData?.weatherDetails?.daily?.drop(1)?.firstOrNull()?.weather?.firstOrNull()?.description
        } ?: ""
    }

    fun getTemperature(): Double {
        return if (weatherSelectedTag == WEATHER_TODAY) {
            weatherData?.weatherDetails?.current?.temp
        } else {
            weatherData?.weatherDetails?.daily?.drop(1)?.firstOrNull()?.temp?.max
        } ?: 0.0
    }

    fun getWindSpeed(): Double {
        return if (weatherSelectedTag == WEATHER_TODAY) {
            weatherData?.weatherDetails?.current?.windSpeed
        } else {
            weatherData?.weatherDetails?.daily?.drop(1)?.firstOrNull()?.windSpeed
        } ?: 0.0
    }

    fun getHumidity(): Double {
        return if (weatherSelectedTag == WEATHER_TODAY) {
            weatherData?.weatherDetails?.current?.humidity
        } else {
            weatherData?.weatherDetails?.daily?.drop(1)?.firstOrNull()?.humidity?.toDouble()
        } ?: 0.0
    }

    fun getWindDirection(): String {
        return if (weatherSelectedTag == WEATHER_TODAY) {
            com.taomish.app.android.farmsanta.farmer.utils.getWindDirection(
                weatherData?.weatherDetails?.current?.windDeg?.toInt() ?: -1
            )
        } else {
            com.taomish.app.android.farmsanta.farmer.utils.getWindDirection(
                weatherData?.weatherDetails?.daily?.drop(1)?.firstOrNull()?.windDeg ?: -1
            )
        }
    }


    fun applyCropChanges(context: Context, onComplete: () -> Unit) {
        mProfile.value?.crop1 = myCrops.elementAtOrNull(0)?.uuid
        mProfile.value?.crop2 = myCrops.elementAtOrNull(1)?.uuid
        mProfile.value?.crop3 = myCrops.elementAtOrNull(2)?.uuid
        saveUserProfileService(context = context, onComplete = onComplete)
    }

    private fun saveUserProfileService(context: Context, onComplete: () -> Unit) {
        val task = SaveFarmerTask(mProfile.value!!)
        task.context = context
        task.setLoadingMessage(context.getString(R.string.saving_profile))
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                getCurrentUser(context, onComplete)
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                context.showToast(reason)
                onComplete()
            }
        })
        task.execute()
    }

    private fun getCurrentUser(context: Context, onComplete: () -> Unit) {
        val task = GetCurrentFarmerTask()
        task.context = context
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Farmer) {
                    DataHolder.getInstance().selectedFarmer = data
                    mProfile.postValue(data)
                    onComplete()
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })

        task.execute()
    }

    fun setXAndYValues() {
        when (selectedPeriodIndex.value) {
            0 -> xValues.postValue(marketData?.list?.getXValuesForWeek().orEmpty())
            1 -> xValues.postValue(marketData?.list?.getXValuesForMonth().orEmpty())
            2, 3 -> xValues.postValue(marketData?.list?.getXValuesForYears().orEmpty())
        }
        yValues.postValue(marketData?.list?.getYValues().orEmpty())
        pointsValue.postValue(
            marketData?.list?.map { it.price?.value?.toFloat() ?: 0F }.orEmpty()
        )
        Log.d("HomeViewModel", "setXAndYValues: ${pointsValue.value}")
    }


}