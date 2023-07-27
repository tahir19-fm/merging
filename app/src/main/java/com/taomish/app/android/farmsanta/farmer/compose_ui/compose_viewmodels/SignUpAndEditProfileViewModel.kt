package com.taomish.app.android.farmsanta.farmer.compose_ui.compose_viewmodels

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.*
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_map_view.components.ImmutableList
import com.taomish.app.android.farmsanta.farmer.constants.UserConstants
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Crop
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Land
import com.taomish.app.android.farmsanta.farmer.models.api.gender.GenderItem
import com.taomish.app.android.farmsanta.farmer.models.api.master.*
import com.taomish.app.android.farmsanta.farmer.models.api.user.UserToken
import com.taomish.app.android.farmsanta.farmer.utils.*
import java.io.IOException

@Suppress("UNCHECKED_CAST", "unused")
class SignUpAndEditProfileViewModel : ViewModel() {

    var appPrefs: AppPrefs? = null
    val selectedFarmer: MutableState<Farmer?> = mutableStateOf(null)
    val isFetchingLocation = mutableStateOf(false)
    val profileBitmap = mutableStateOf<Bitmap?>(null)
    var isEditingFarm = false
    var selectedCountryLatLng: LatLng? = null
    val coordinates = mutableStateOf<ImmutableList<LatLng>>(ImmutableList(emptyList()))
    var selectedLandIndex = -1
    val genders = mutableStateListOf<String>()

    var didShowDialog by mutableStateOf(false)

    val searchText = mutableStateOf("")

    val allSelectedCrops = mutableStateListOf<CropMaster>()

    val allCrops = mutableStateOf<Map<String, List<CropMaster>>>(emptyMap())
    val lands = mutableListOf<Land>()
    val landCrops = mutableListOf<Crop>()
    var docs = mutableListOf<String>()

    val allCropDivisions = mutableStateListOf<GlobalIndicatorDTO>()
    val selectedUOMDto = mutableStateOf<UOMType?>(null)
    val selectedUOM = mutableStateOf("")
    val landUOMs = mutableStateListOf<UOMType>()
    val educationLevels = mutableListOf<GlobalIndicatorDTO>()
    val educationNames = mutableStateOf<List<String>>(emptyList())
    val waterSources = mutableStateListOf<String>()
    var farmLocation: String? = null
    var tabs = mutableStateListOf<String>()

    val selectedTerritoryDto = mutableStateOf<Territory?>(null)
    var territoriesDto = emptyList<Territory>()
    val territories = mutableStateListOf<String>()

    val selectedRegionDto = mutableStateOf<Region?>(null)
    var regionsDto = emptyList<Region>()
    val regions = mutableStateListOf<String>()

    val selectedCountyDto = mutableStateOf<County?>(null)
    var countiesDto = emptyList<County>()
    val counties = mutableStateListOf<String>()

    val selectedSubCountyDto = mutableStateOf<SubCounty?>(null)
    var subCountiesDto = emptyList<SubCounty>()
    val subCounties = mutableStateListOf<String>()

    val selectedVillageDto = mutableStateOf<Village?>(null)
    var villagesDto = emptyList<Village>()
    val villages = mutableStateListOf<String>()


    val states = mutableStateOf<List<String>>(emptyList())

    val selectedCrops = mutableStateListOf<String>()
    val selectedRegions = mutableListOf<String>()
    val selectedTerritories = mutableListOf<String>()

    val countryCode = mutableStateOf("")
    val mobileNumber = mutableStateOf("")
    val registrationNo = mutableStateOf("")
    val farmName = mutableStateOf("")
    val waterSourceDropDownExpanded = mutableStateOf(false)
    val waterSource = mutableStateOf("Water Source")
    val landArea = mutableStateOf("")

    val secondaryMobileNo = mutableStateOf("")
    val email = mutableStateOf("")
    val firstName = mutableStateOf("")
    val middleName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val selectedGender = mutableStateOf("")
    val education = mutableStateOf("")
    val educationDropDownExpanded = mutableStateOf(false)
    val address = mutableStateOf("")
    val regionDropDownExpanded = mutableStateOf(false)
    val region = mutableStateOf("")
    val pinCode = mutableStateOf("")
    val territoryDropDownExpanded = mutableStateOf(false)
    val territory = mutableStateOf("")
    val countyDropDownExpanded = mutableStateOf(false)
    val county = mutableStateOf("")
    val subCountyDropDownExpanded = mutableStateOf(false)
    val subCounty = mutableStateOf("")
    val villageDropDownExpanded = mutableStateOf(false)
    val village = mutableStateOf("")
    val dateOfBirth = mutableStateOf<String?>(null)
    val farmSize = mutableStateOf("")
    val crops = mutableStateOf<List<Crop>>(emptyList())
    private val farmerTag = mutableStateOf("")
    val uOMDropDownExpanded = mutableStateOf(false)

    fun validateMobile(): Boolean {
        val cCode = appPrefs?.countryCode ?: countryCode.value
        val mobileNumber = if (mobileNumber.value.startsWith(cCode))
            mobileNumber.value.removePrefix(cCode)
        else
            mobileNumber.value
        return mobileNumber.length >= 8
    }

    fun validateLocation(): Boolean {
        return address.isNotEmpty() && selectedTerritoryDto.value != null &&
                selectedRegionDto.value != null
    }

    fun validateFarmDetails(context: Context): Boolean {
        var result = true
        if (farmName.isEmpty()) {
            context.showToast(R.string.valid_farm_name_required)
            result = false
        }
        if (landArea.isEmpty()) {
            context.showToast(R.string.valid_land_area_required)
            result = false
        }
        if (waterSource.value == "Water Source") {
            context.showToast(R.string.valid_water_source_required)
            result = false
        }
        if (coordinates.value.isEmpty()) {
            context.showToast(R.string.valid_land_boundries_required)
            result = false
        }
        return result
    }


    fun loadLand(context: Context) {
        if (selectedLandIndex >= 0 && selectedLandIndex < lands.size) {
            lands[selectedLandIndex].apply {
                registrationNo.postValue(registrationNumber ?: "")
                farmName.postValue(landName ?: "")
                landArea.postValue(area.unit.toString())
                selectedUOM.postValue(area.uom ?: "")
                this@SignUpAndEditProfileViewModel.waterSource.postValue(waterSource ?: "")
                this@SignUpAndEditProfileViewModel.farmLocation = farmLocation
                coordinates?.let { coords ->
                    this@SignUpAndEditProfileViewModel.coordinates
                        .postValue(ImmutableList(coords.map { LatLng(it.latitude, it.longitude) }))
                }
                landCrops.clear()
                crops?.let(landCrops::addAll)
                docs.clear()
                documents?.let(docs::addAll)
                this@SignUpAndEditProfileViewModel.coordinates.value.getCenterCoordinate()?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                    farmLocation = it.getLocationName(context)
                }
            }
        }
    }


    fun validateFarmer(context: Context): Boolean {
        if (!validateMobile()) {
            context.showToast(R.string.valid_mobile_no_required)
            return false
        }
        if (email.value.trim().isNotEmpty() && !email.value.isEmail()) {
            context.showToast(R.string.email_validation)
            return false
        }
        if (firstName.isEmpty() || firstName.value.length < 3) {
            context.showToast(R.string.valid_name_required)
            return false
        }
        if (lastName.isEmpty() || lastName.value.isEmpty()) {
            context.showToast(R.string.valid_last_name_required)
            return false
        }
        if (selectedGender.isEmpty()) {
            context.showToast(R.string.valid_gender_required)
            return false
        }
        if (territory.isEmpty()) {
            context.showToast(R.string.valid_territory_required)
            return false
        }
        if (region.isEmpty()) {
            context.showToast(R.string.valid_region_required)
            return false
        }
        return true
    }


    fun loadFarmerDetails(context: Context) {
        val cCode =
            (if (selectedFarmer.value?.countryCode.isNullOrEmpty()) AppPrefs(context).countryCode
            else selectedFarmer.value?.countryCode)?.removePrefix("+") ?: ""
        mobileNumber.postValue(
            selectedFarmer.value?.mobile?.removePrefix(cCode) ?: ""
        )
        countryCode.postValue(cCode)
        firstName.postValue(selectedFarmer.value?.firstName ?: "")
        lastName.postValue(selectedFarmer.value?.lastName ?: "")
        selectedGender.postValue(selectedFarmer.value?.gender ?: "")
        education.postValue(selectedFarmer.value?.education ?: "")
        address.postValue(selectedFarmer.value?.address ?: "")
        email.postValue(selectedFarmer.value?.email ?: "")
        fetchGenders(context)
        fetchTerritories(context)
        selectedFarmer.value?.territory?.let { territoriesUuids ->
            territoriesDto.associateBy { it.uuid }.let { map ->
                val territoriesTexts = mutableListOf<String>()
                selectedTerritories.clear()
                territoriesUuids.forEach { uuid ->
                    map[uuid]?.let { t ->
                        t.territoryName?.let(territoriesTexts::add)
                        selectedTerritoryDto.postValue(t)
                        fetchRegionsByTerritory(context, true)
                    }
                }
                territory.postValue(territoriesTexts.joinToString())
            }
        }

        county.postValue(selectedFarmer.value?.district ?: "")
        subCounty.postValue(selectedFarmer.value?.subDistrict ?: "")
        village.postValue(selectedFarmer.value?.village ?: "")
        pinCode.postValue(selectedFarmer.value?.pin ?: "")
        farmerTag.postValue(selectedFarmer.value?.farmerTag ?: "")
        selectedTerritories.clear()
        selectedTerritories.addAll(selectedFarmer.value?.territory.orEmpty())
        selectedRegions.clear()
        selectedRegions.addAll(selectedFarmer.value?.region.orEmpty())
        region.postValue(selectedFarmer.value?.regionName ?: "")
        dateOfBirth.postValue(
            DateUtil().getDateMonthYearFormat(selectedFarmer.value?.dateOfBirth ?: "")
        )
        mapLandDetails()
    }

    fun mapLandDetails() {
        selectedFarmer.value?.lands?.forEach { land ->
            if (lands.find { it.landId == land?.landId } == null) {
                land?.let(lands::add)
            }
        }
    }


    fun clearValues() {
        mobileNumber.postValue("")
        secondaryMobileNo.postValue("")
        firstName.postValue("")
        middleName.postValue("")
        lastName.postValue("")
        selectedGender.postValue("")
        education.postValue("")
        educationDropDownExpanded.postValue(false)
        address.postValue("")
        territory.postValue("")
        region.postValue("")
        pinCode.postValue("")
        village.postValue("")
        dateOfBirth.postValue("")
        farmSize.postValue("")
        farmerTag.postValue("")
        allSelectedCrops.clear()
        lands.clear()
    }


    fun getFarmer(context: Context): Farmer {
        val currentProfile = DataHolder.getInstance().selectedFarmer
        val farmer = Farmer()
        farmer.address = address.value
        farmer.country = territory.value
        val cCode =
            (if (selectedFarmer.value?.countryCode.isNullOrEmpty()) AppPrefs(context).countryCode
            else selectedFarmer.value?.countryCode)?.removePrefix("+") ?: ""
        farmer.countryCode = cCode
        farmer.createdBy = selectedFarmer.value?.uuid ?: "6065e7c8-5eb3-46cb-b57a-c754cff64766"
        farmer.createdTimestamp = System.currentTimeMillis().toString()
        farmer.crop1 = allSelectedCrops.elementAtOrNull(0)?.uuid
        farmer.crop2 = allSelectedCrops.elementAtOrNull(1)?.uuid
        farmer.crop3 = allSelectedCrops.elementAtOrNull(2)?.uuid
        farmer.dataSource = ""
        farmer.dateOfBirth = DateUtil().getApiFormat(
            dateOfBirth.value,
            if (dateOfBirth.value?.contains("-") == true) "dd-MM-yyyy" else "dd MMM, yyyy"
        )
        farmer.district = county.value
        farmer.documents = docs
        farmer.education = education.value
        farmer.email = email.value
        farmer.farmerGroup = currentProfile?.farmerGroup
        farmer.farmerTag = farmerTag.value
        farmer.firstName = firstName.value
        farmer.gender = selectedGender.value
        farmer.hasPhone = true
        farmer.id = currentProfile?.id ?: 0
        farmer.lands = lands
        farmer.languageId = try {
            AppPrefs(context).languageId.toInt()
        } catch (e: Exception) {
            1
        }
        farmer.lastName = lastName.value
        farmer.midName = middleName.value
        val mobileNo = (AppPrefs(context).countryCode + mobileNumber.value).removePrefix("+")
        farmer.mobile = mobileNo
        farmer.state = this.region.value
        farmer.pin = pinCode.value
        farmer.profileImage = selectedFarmer.value?.profileImage ?: farmer.profileImage
        farmer.region = selectedRegions.ifEmpty { currentProfile?.region.orEmpty() }
        farmer.role = UserConstants.ROLE_FARMER
        farmer.sequence = 0
        farmer.status = UserConstants.STATUS_ACTIVE
        farmer.subDistrict = subCounty.value
        farmer.tenantId = ""
        farmer.territory = selectedTerritories.ifEmpty { currentProfile?.territory.orEmpty() }
        farmer.updatedBy = ""
        farmer.updatedTimestamp = System.currentTimeMillis().toString()
        farmer.userId = currentProfile?.userId ?: ""
        farmer.userName = currentProfile?.userName ?: ""
        farmer.uuid = currentProfile?.uuid ?: ""
        farmer.village = village.value
        return farmer
    }

    fun setUserDetails(
        context: Context,
        goToHome: (UserToken) -> Unit,
    ) {
        val farmer = getFarmer(context)
        if (profileBitmap.value != null) {
            try {
                uploadUserImage(
                    farmer = farmer,
                    context = context,
                    goToHome = { goToHome(it) }
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            saveUserProfileService(
                farmer,
                context,
                goToHome = { goToHome(it) }
            )
        }
    }


    @Throws(IOException::class)
    fun uploadUserImage(
        farmer: Farmer,
        context: Context,
        goToHome: (UserToken) -> Unit,
    ) {
        val profilePicFile = FileUtil()
            .getFileFromBitmap(
                profileBitmap.value,
                context,
                "profile_pic"
            )
        val task = UploadProfilePicTask(profilePicFile)
        task.context = context
        task.setLoadingMessage(context.getString(R.string.loading))
        task.isCancelable = false
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is String) {
                    val docs: MutableList<String> = ArrayList()
                    docs.add(data)
                    farmer.documents = docs
                    farmer.profileImage = data
                }
                saveUserProfileService(farmer, context, goToHome)
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {
                context.showToast("${context.getString(R.string.reason)}: $reason")
                saveUserProfileService(farmer, context, goToHome)
            }
        })
        task.execute()
    }

    fun saveUserProfileService(profile: Farmer, context: Context, goToHome: (UserToken) -> Unit) {
        val task = SaveFarmerDetailsTask(profile)
        task.context = context
        task.setLoadingMessage(context.getString(R.string.save_farmer_loading_msg))
        task.isCancelable = false
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                goToHome(data as UserToken)
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }


    fun fetchTerritories(context: Context) {
        if (DataHolder.getInstance().allTerritories.isNotEmpty()) {
            territoriesDto = DataHolder.getInstance().allTerritories.toList()
            territories.clear()
            territories.addAll(territoriesDto.map { it.territoryName ?: "" })
            return
        }
        val task = GetTerritoryTask()
        task.context = context
        task.setShowLoading(true)
        task.setLoadingMessage(context.getString(R.string.territories_loading_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Array<*>) {
                    val list = data.toList() as List<Territory>?
                    if (!list.isNullOrEmpty()) {
                        clearLocationApiData(TERRITORY)
                        territoriesDto = list
                        territories.addAll(list.map { it.territoryName ?: "" })
                        DataHolder.getInstance().setAllTerritories(list.toTypedArray())
                    }
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {}
        })
        task.execute()
    }

    fun fetchRegionsByTerritory(context: Context, selectFarmerRegion: Boolean = false) {
        val task = GetRegionsByTerritoryTask()
        task.context = context
        task.setShowLoading(true)
        task.setLoadingMessage(context.getString(R.string.regions_loading_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is Array<*>) {
                    val list = data.toList() as List<Region>?
                    if (!list.isNullOrEmpty()) {
                        clearLocationApiData(REGION)
                        regionsDto = list
                        regions.addAll(regionsDto.map { it.regionName })
                        if (selectFarmerRegion) {
                            selectedFarmer.value?.region?.let { regionsUuids ->
                                DataHolder.getInstance().regionsMap?.let { map ->
                                    val regionsText = mutableListOf<String>()
                                    regionsUuids.forEach { uuid ->
                                        map[uuid]?.let { r ->
                                            r.regionName?.let { regionsText.add(it) }
                                            selectedRegionDto.postValue(r)
                                            fetchCountiesByRegion(context, true)
                                        }
                                    }
                                    region.postValue(regionsText.joinToString())
                                }
                            }
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {
                context.showToast("${context.getString(R.string.message)}: $errorMessage")
            }
        })
        task.execute(selectedTerritoryDto.value?.uuid)
    }

    fun fetchCountiesByRegion(context: Context, selectFarmerCounty: Boolean = false) {
        val task = GetCountiesByRegionTask()
        task.context = context
        task.setShowLoading(false)
        task.setLoadingMessage(context.getString(R.string.regions_counties_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is Array<*>) {
                    val list = data.toList() as List<County>?
                    if (!list.isNullOrEmpty()) {
                        clearLocationApiData(COUNTY)
                        countiesDto = list
                        counties.addAll(countiesDto.map { it.countyName })
                        if (selectFarmerCounty) {
                            countiesDto.find { it.countyName == selectedFarmer.value?.district }
                                ?.let {
                                    selectedCountyDto.value = it
                                    county.value = it.countyName
                                    fetchSubCountiesByCounty(context, true)
                                }
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {}
        })
        task.execute(selectedRegionDto.value?.uuid)
    }


    fun fetchSubCountiesByCounty(context: Context, selectFarmerSubCounty: Boolean = false) {
        val task = GetSubCountiesByCountyTask()
        task.context = context
        task.setShowLoading(true)
        task.setLoadingMessage(context.getString(R.string.regions_sub_counties_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is Array<*>) {
                    val list = data.toList() as List<SubCounty>?
                    if (!list.isNullOrEmpty()) {
                        clearLocationApiData(SUB_COUNTY)
                        subCountiesDto = list
                        subCounties.addAll(subCountiesDto.map { it.subcountyName })
                        if (selectFarmerSubCounty) {
                            subCountiesDto.find { it.subcountyName == selectedFarmer.value?.subDistrict }
                                ?.let {
                                    selectedSubCountyDto.value = it
                                    subCounty.value = it.subcountyName
                                    fetchVillagesBySubCounty(context, true)
                                }
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {}
        })
        task.execute(selectedCountyDto.value?.id.toString())
    }


    fun fetchVillagesBySubCounty(context: Context, selectFarmerVillage: Boolean = false) {
        val task = GetVillagesBySubCountyTask()
        task.context = context
        task.setShowLoading(true)
        task.setLoadingMessage(context.getString(R.string.loading_villages_msg))
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is Array<*>) {
                    val list = data.toList() as List<Village>?
                    if (!list.isNullOrEmpty()) {
                        clearLocationApiData(VILLAGE)
                        villagesDto = list
                        villages.addAll(villagesDto.mapNotNull { it.villageName })
                        if (selectFarmerVillage) {
                            villagesDto.find { it.villageName == selectedFarmer.value?.village }
                                ?.let {
                                    selectedVillageDto.value = it
                                    village.value = it.villageName ?: ""
                                }
                        }
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {}
        })
        task.execute(selectedSubCountyDto.value?.id.toString())
    }


    fun fetchGenders(context: Context) {
        val task = GetGenderTask()
        task.context = context
        task.setShowLoading(true)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any?) {
                if (data is Array<*>) {
                    val list = data.toList() as List<GenderItem>?
                    if (!list.isNullOrEmpty()) {
                        genders.clear()
                        genders.addAll(list.map { it.name ?: "" })
                        list.find { it.name?.lowercase() == selectedFarmer.value?.gender?.lowercase() }
                            ?.let {
                                selectedGender.value = it.name ?: ""
                            }
                    }
                }
            }

            override fun onTaskFailure(reason: String?, errorMessage: String?) {}
        })
        task.execute()
    }


    fun clearAddressFields() {
        region.postValue("")
        regions.clear()
        selectedRegionDto.postValue(null)
        county.postValue("")
        counties.clear()
        selectedCountyDto.postValue(null)
        village.postValue("")
        subCounties.clear()
        selectedSubCountyDto.postValue(null)
    }

    fun clearFarmFields() {
        registrationNo.postValue("")
        farmName.postValue("")
        landArea.postValue("")
        waterSource.postValue("")
        coordinates.postValue(ImmutableList(emptyList()))
    }

    private fun clearLocationApiData(level: Int) {
        if (level <= TERRITORY) {
            territory.postValue("")
            territories.clear()
            territoriesDto = emptyList()
            selectedTerritoryDto.postValue(null)
        }
        if (level <= REGION) {
            region.postValue("")
            regions.clear()
            regionsDto = emptyList()
            selectedRegionDto.postValue(null)
        }
        if (level <= COUNTY) {
            county.postValue("")
            counties.clear()
            countiesDto = emptyList()
            selectedCountyDto.postValue(null)
        }
        if (level <= SUB_COUNTY) {
            subCounty.postValue("")
            subCounties.clear()
            subCountiesDto = emptyList()
            selectedSubCountyDto.postValue(null)
        }
        if (level <= VILLAGE) {
            village.postValue("")
            villagesDto = emptyList()
            villages.clear()
            selectedVillageDto.postValue(null)
        }
    }


    private companion object {
        const val TERRITORY = 1
        const val REGION = 2
        const val COUNTY = 3
        const val SUB_COUNTY = 4
        const val VILLAGE = 5
    }


}