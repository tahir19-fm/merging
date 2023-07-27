package com.taomish.app.android.farmsanta.farmer.controller;

import android.content.Context;

import androidx.room.Room;

import com.taomish.app.android.farmsanta.farmer.interfaces.db.MasterDao;
import com.taomish.app.android.farmsanta.farmer.interfaces.db.PostTagDao;
import com.taomish.app.android.farmsanta.farmer.libs.room_db.RoomDB;
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.CategoryItem;
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage;
import com.taomish.app.android.farmsanta.farmer.models.db.crop.DB_Crop;
import com.taomish.app.android.farmsanta.farmer.models.db.crop_stage.DB_CropStage;
import com.taomish.app.android.farmsanta.farmer.models.db.cultivar.DB_Cultivar;
import com.taomish.app.android.farmsanta.farmer.models.db.global_indicator.DB_GlobalIndicator;
import com.taomish.app.android.farmsanta.farmer.models.db.soil_health.DB_CropRecommendation;
import com.taomish.app.android.farmsanta.farmer.models.db.soil_health.DB_SoilHealth;

import java.util.ArrayList;
import java.util.List;

public class DBController {

    private final RoomDB db;

    public DBController(Context context) {
        db = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, "db_farmer")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public void clearDb() {
        if (db != null)
            db.clearAllTables();
    }

    public RoomDB getDb() {
        return db;
    }

    public List<DB_SoilHealth> getAllSoilHealth() {
        return db.soilHealthDAO().getAll();
    }

    public boolean saveAllSoilHealth(ArrayList<DB_SoilHealth> list) {
        db.soilHealthDAO().insertAll(list);
        return true;
    }

    public DB_SoilHealth getSoilHealth(String uuid) {
        return db.soilHealthDAO().findById(uuid);
    }

    public List<DB_Crop> getAllCrop() {
        return db.cropDAO().getAll();
    }

    public List<CategoryItem> getAllCategories() {
        return db.scoutingDAO().getAll();
    }

    public boolean saveAllCat(List<CategoryItem> list) {
        db.scoutingDAO().insertAll(list);
        return true;
    }

    public boolean saveAllCrop(ArrayList<DB_Crop> list) {
        db.cropDAO().insertAll(list);
        return true;
    }

    public boolean saveAllCropRecommendations(ArrayList<DB_CropRecommendation> list) {
        db.cropRecommendationDAO().insertAll(list);
        return true;
    }

    public List<DB_CropRecommendation> getAllCropRecommendation() {
        return db.cropRecommendationDAO().getAll();
    }

    public List<DB_CropRecommendation> getCropRecommendationBySoilHealth(String soilHealthCard) {
        return db.cropRecommendationDAO().findBySoilCard(soilHealthCard);
    }

    public DB_CropRecommendation getCropRecommendationById(String uuid) {
        return db.cropRecommendationDAO().findById(uuid);
    }

    public boolean deleteAllCropRecommendation() {
        db.cropRecommendationDAO().deleteAll();
        return true;
    }

    public DB_Crop getCrop(String uuid) {
        return db.cropDAO().findById(uuid);
    }

    public List<DB_CropStage> getAllCropStages() {
        return db.cropStageDAO().getAll();
    }

    public boolean saveAllCropStages(ArrayList<DB_CropStage> list) {
        db.cropStageDAO().insertAll(list);
        return true;
    }

    public List<DB_CropStage> getCropStage(String cropId) {
        return db.cropStageDAO().findByCropId(cropId);
    }

    public List<DB_GlobalIndicator> getAllGlobalIndicators() {
        return db.globalIndicatorDAO().getAll();
    }

    public boolean saveAllGlobalIndicators(ArrayList<DB_GlobalIndicator> list) {
        db.globalIndicatorDAO().insertAll(list);
        return true;
    }

    public List<DB_GlobalIndicator> getIndicatorByGroup(String groupName) {
        return db.globalIndicatorDAO().findByGroup(groupName);
    }

    public List<DB_Cultivar> getAllCultivars() {
        return db.cultivarDAO().getAll();
    }

    public boolean saveAllCultivars(ArrayList<DB_Cultivar> list) {
        db.cultivarDAO().insertAll(list);
        return true;
    }

    public void saveAllScoutingField(List<CropStage> list) {
        db.scoutingDAO().insertAllScoutingFiled(list);
    }

    public List<DB_Cultivar> getCultivarByCrop(String cropId) {
        return db.cultivarDAO().findByCrop(cropId);
    }

    public PostTagDao getPostTagDao() {
        return db.postTagDao();
    }

    public DB_Cultivar getCultivarById(String uuid) {
        return db.cultivarDAO().findById(uuid);
    }

    public MasterDao getMasterDao() {
        return db.masterDao();
    }
}
