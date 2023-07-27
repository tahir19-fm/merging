package com.taomish.app.android.farmsanta.farmer.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.taomish.app.android.farmsanta.farmer.models.db.soil_health.DB_CropRecommendation;

import java.util.List;

@Dao
public interface CropRecommendationDAO {
    @Query("SELECT * FROM tbl_crop_recommendation")
    List<DB_CropRecommendation> getAll();

    @Query("SELECT * FROM tbl_crop_recommendation WHERE uuid IN (:uuid)")
    List<DB_CropRecommendation> loadAllByIds(String[] uuid);

    @Query("SELECT * FROM tbl_crop_recommendation WHERE uuid = :uuid LIMIT 1")
    DB_CropRecommendation findById(String uuid);

    @Query("SELECT * FROM tbl_crop_recommendation WHERE soil_card = :soilCard")
    List<DB_CropRecommendation> findBySoilCard(String soilCard);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DB_CropRecommendation> cropSpecificRecommendations);

    @Delete
    void delete(DB_CropRecommendation cropSpecificRecommendation);

    @Query("DELETE FROM tbl_crop_recommendation")
    void deleteAll();
}
