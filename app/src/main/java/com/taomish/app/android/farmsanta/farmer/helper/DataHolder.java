package com.taomish.app.android.farmsanta.farmer.helper;

import android.location.Location;

import com.taomish.app.android.farmsanta.farmer.models.api.cultivar.Cultivar;
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Farmer;
import com.taomish.app.android.farmsanta.farmer.models.api.gender.GenderItem;
import com.taomish.app.android.farmsanta.farmer.models.api.home.News;
import com.taomish.app.android.farmsanta.farmer.models.api.home.Price;
import com.taomish.app.android.farmsanta.farmer.models.api.master.County;
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster;
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO;
import com.taomish.app.android.farmsanta.farmer.models.api.master.Language;
import com.taomish.app.android.farmsanta.farmer.models.api.master.Region;
import com.taomish.app.android.farmsanta.farmer.models.api.master.SubCounty;
import com.taomish.app.android.farmsanta.farmer.models.api.master.Territory;
import com.taomish.app.android.farmsanta.farmer.models.api.master.Village;
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message;
import com.taomish.app.android.farmsanta.farmer.models.api.notification.AdvisoryTag;
import com.taomish.app.android.farmsanta.farmer.models.api.notification.CropAdvisory;
import com.taomish.app.android.farmsanta.farmer.models.api.pop.PopDto;
import com.taomish.app.android.farmsanta.farmer.models.api.weather.WeatherData;
import com.taomish.app.android.farmsanta.farmer.models.db.global_indicator.DB_GlobalIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.collections.MapsKt;

public class DataHolder {
    private static DataHolder instance;

    private Object dataObject;
    private Farmer selectedFarmer;
    private WeatherData weatherAll;

    private ArrayList<News> newsArrayList;
    private ArrayList<Price> priceArrayList;
    private ArrayList<PopDto> popDtoArrayList;
    private ArrayList<CropMaster> cropArrayList;
    private ArrayList<Cultivar> cultivarArrayList;
    private ArrayList<CropAdvisory> cropAdvisoryArrayList;
    private ArrayList<GlobalIndicatorDTO> waterSourceList;
    private ArrayList<GlobalIndicatorDTO> cultivarTypeList;
    private ArrayList<GlobalIndicatorDTO> cultivarDurationList;
    private List<DB_GlobalIndicator> allGlobalIndicators;
    private final List<Region> allRegions = new ArrayList<>();
    private final List<Territory> allTerritories = new ArrayList<>();
    private Map<String, Territory> territoryMap = MapsKt.emptyMap();
    private Map<String, Region> regionsMap = MapsKt.emptyMap();
    private final List<Language> languages = new ArrayList<>();
    private final List<CropMaster> myCrops = new ArrayList<>();
    private Map<String, CropMaster> cropMasterMap = MapsKt.emptyMap();
    private Map<String, AdvisoryTag> advisoryTagMap;
    private Map<String, GlobalIndicatorDTO> growthStageMap = MapsKt.emptyMap();
    private final List<County> allCounties = new ArrayList<>();
    private final List<SubCounty> allSubCounties = new ArrayList<>();
    private final List<Village> allVillages = new ArrayList<>();

    private List<String> cropTrendingTagArrayList;
    private Location mLastKnownLocation;
    private int scoutingImagesCount = 0;
    private int advisoryCount = 0;
    private int selectedScoutingIndex = -1;
    private int stageId = -1;

    private ArrayList<Message> userMessageArrayList;
    public boolean isListShown = true;
    private List<GenderItem> genderItemList = new ArrayList<>();


    public ArrayList<Message> getUserMessageArrayList() {
        return userMessageArrayList;
    }

    public void setUserMessageArrayList(ArrayList<Message> userMessageArrayList) {
        this.userMessageArrayList = userMessageArrayList;
    }

    private DataHolder() {
    }

    public static DataHolder getInstance() {
        if (instance == null)
            instance = new DataHolder();

        return instance;
    }

    public static DataHolder clearInstance() {
        instance = new DataHolder();
        return instance;
    }

    public Object getDataObject() {
        return dataObject;
    }

    public void setDataObject(Object dataObject) {
        this.dataObject = dataObject;
    }

    public Farmer getSelectedFarmer() {
        return selectedFarmer;
    }

    public void setSelectedFarmer(Farmer selectedFarmer) {
        this.selectedFarmer = selectedFarmer;
        setFarmerRegion(selectedFarmer);
    }

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    public ArrayList<News> getNewsArrayList() {
        return newsArrayList;
    }

    public void setNewsArrayList(ArrayList<News> newsArrayList) {
        this.newsArrayList = newsArrayList;
    }

    public Map<String, CropMaster> getCropMasterMap() {
        return cropMasterMap;
    }

    public void setCropMasterMap() {
        if (cropArrayList != null) {
            Map<String, CropMaster> map = new HashMap<>();
            for (CropMaster crop : cropArrayList) {
                map.put(crop.getUuid(), crop);
            }
            this.cropMasterMap = map;
        }
    }


    public ArrayList<Price> getPriceArrayList() {
        return priceArrayList;
    }

    public void setPriceArrayList(ArrayList<Price> priceArrayList) {
        this.priceArrayList = priceArrayList;
    }

    public ArrayList<PopDto> getPopDtoArrayList() {
        return popDtoArrayList;
    }

    public void setPopDtoArrayList(ArrayList<PopDto> popDtoArrayList) {
        this.popDtoArrayList = popDtoArrayList;
    }

    public ArrayList<CropMaster> getCropArrayList() {
        return cropArrayList;
    }

    public void setCropArrayList(ArrayList<CropMaster> cropArrayList) {
        this.cropArrayList = cropArrayList;
        setCropMasterMap();
    }

    public ArrayList<CropAdvisory> getCropAdvisoryArrayList() {
        return cropAdvisoryArrayList;
    }

    public void setCropAdvisoryArrayList(ArrayList<CropAdvisory> cropAdvisoryArrayList) {
        this.cropAdvisoryArrayList = cropAdvisoryArrayList;
    }

    public Map<String, AdvisoryTag> getAdvisoryTagMap() {
        return advisoryTagMap;
    }

    public void setAdvisoryTagMap(List<AdvisoryTag> advisoryTagList) {
        Map<String, AdvisoryTag> map = new HashMap<>();
        for (AdvisoryTag tag : advisoryTagList) {
            map.put(tag.getUuid(), tag);
        }
        this.advisoryTagMap = map;
    }

    public Map<String, GlobalIndicatorDTO> getGrowthStagesMap() {
        return growthStageMap;
    }

    public void setGrowthStagesMap(ArrayList<GlobalIndicatorDTO> growthStages) {
        Map<String, GlobalIndicatorDTO> map = new HashMap<>();
        for (GlobalIndicatorDTO crop : growthStages) {
            map.put(crop.getUuid(), crop);
        }
        this.growthStageMap = map;
    }

    public WeatherData getWeatherAll() {
        return weatherAll;
    }

    public void setWeatherAll(WeatherData weatherAll) {
        this.weatherAll = weatherAll;
    }

    public ArrayList<GlobalIndicatorDTO> getWaterSourceList() {
        return waterSourceList;
    }

    public void setWaterSourceList(ArrayList<GlobalIndicatorDTO> waterSourceList) {
        this.waterSourceList = waterSourceList;
    }

    public ArrayList<GlobalIndicatorDTO> getCultivarTypeList() {
        return cultivarTypeList;
    }

    public void setCultivarTypeList(ArrayList<GlobalIndicatorDTO> cultivarTypeList) {
        this.cultivarTypeList = cultivarTypeList;
    }

    public ArrayList<GlobalIndicatorDTO> getCultivarDurationList() {
        return cultivarDurationList;
    }

    public void setCultivarDurationList(ArrayList<GlobalIndicatorDTO> cultivarDurationList) {
        this.cultivarDurationList = cultivarDurationList;
    }

    public int getScoutingImagesCount() {
        return scoutingImagesCount;
    }

    public void setScoutingImagesCount(int scoutingImagesCount) {
        this.scoutingImagesCount = scoutingImagesCount;
    }

    public int getAdvisoryCount() {
        return advisoryCount;
    }

    public void setAdvisoryCount(int advisoryCount) {
        this.advisoryCount = advisoryCount;
    }

    public ArrayList<Cultivar> getCultivarArrayList() {
        return cultivarArrayList;
    }

    public void setCultivarArrayList(ArrayList<Cultivar> cultivarArrayList) {
        this.cultivarArrayList = cultivarArrayList;
    }

    public Location getLastKnownLocation() {
        return mLastKnownLocation;
    }

    public void setLastKnownLocation(Location mLastKnownLocation) {
        this.mLastKnownLocation = mLastKnownLocation;
    }

    public int getSelectedScoutingIndex() {
        return selectedScoutingIndex;
    }

    public void setSelectedScoutingIndex(int selectedScoutingIndex) {
        this.selectedScoutingIndex = selectedScoutingIndex;
    }

    public List<String> getCropTrendingTagArrayList() {
        return cropTrendingTagArrayList;
    }

    public void setCropTrendingTagArrayList(List<String> cropTrendingTagArrayList) {
        this.cropTrendingTagArrayList = cropTrendingTagArrayList;
    }

    public List<DB_GlobalIndicator> getAllGlobalIndicators() {
        return allGlobalIndicators;
    }

    public void setAllGlobalIndicators(List<DB_GlobalIndicator> allGlobalIndicators) {
        this.allGlobalIndicators = allGlobalIndicators;
    }

    public void setRegions(Region[] data) {
        Collections.addAll(allRegions, data);
        setRegionsMap(data);
        setFarmerRegion(selectedFarmer);
    }

    public List<Region> getAllRegions() {
        return allRegions;
    }

    public void setFarmerRegion(Farmer selectedFarmer) {
        if (selectedFarmer != null) {
            if (allRegions != null && selectedFarmer.getRegion() != null) {
                for (String region : selectedFarmer.getRegion()) {
                    for (Region region1 : allRegions) {
                        if (region1.getUuid().equals(region)) {
                            String userRegion = "";
                            if (userRegion.isEmpty()) {
                                userRegion += region1.getRegionName();
                            } else {
                                userRegion += ", " + region1.getRegionName();
                            }
                            selectedFarmer.setRegionName(userRegion);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void setAllTerritories(Territory[] data) {
        allTerritories.clear();
        Collections.addAll(allTerritories, data);
        setTerritoryMap(data);
    }

    public List<Language> getAllLanguages() {
        return languages;
    }

    public void setLanguages(Language[] data) {
        languages.clear();
        Collections.addAll(languages, data);
    }

    public List<CropMaster> getMyCrops() {
        return myCrops;
    }

    public void setMyCrops(CropMaster[] data) {
        myCrops.clear();
        Collections.addAll(myCrops, data);
    }

    public List<Territory> getAllTerritories() {
        return allTerritories;
    }

    public void setGenderItemList(List<GenderItem> genderItemList) {
        this.genderItemList = genderItemList;
    }

    public List<GenderItem> getGenderItemList() {
        return genderItemList;
    }

    public ArrayList<String> getGenderList() {
        ArrayList<String> dataList = new ArrayList<>();
        for (GenderItem item : genderItemList) {
            dataList.add(item.getName());
        }
        return dataList;
    }

    public Map<String, Territory> getTerritoryMap() {
        return territoryMap;
    }

    public void setTerritoryMap(Territory[] territories) {
        Map<String, Territory> map = new HashMap<>();
        for (Territory territory : territories) {
            map.put(territory.getUuid(), territory);
        }
        this.territoryMap = map;
    }

    public Map<String, Region> getRegionsMap() {
        return regionsMap;
    }

    public void setRegionsMap(Region[] regions) {
        Map<String, Region> map = new HashMap<>();
        for (Region region : regions) {
            map.put(region.getUuid(), region);
        }
        this.regionsMap = map;
    }

    public List<SubCounty> getAllSubCounties() {
        return allSubCounties;
    }

    public void setAllSubCounties(SubCounty[] subCounties) {
        allSubCounties.clear();
        Collections.addAll(allSubCounties, subCounties);
    }

    public List<Village> getAllVillages() {
        return allVillages;
    }

    public void setAllVillages(Village[] villages) {
        allVillages.clear();
        Collections.addAll(allVillages, villages);
    }
}
