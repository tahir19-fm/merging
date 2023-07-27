package com.taomish.app.android.farmsanta.farmer.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.taomish.app.android.farmsanta.farmer.models.db.crop_stage.DB_CropStage;

import java.util.List;

@Dao
public interface CropStageDAO {

    @Query("SELECT * FROM tbl_crop_stage")
    List<DB_CropStage> getAll();

    @Query("SELECT * FROM tbl_crop_stage WHERE uuid IN (:uuid)")
    List<DB_CropStage> loadAllByIds(String[] uuid);

    @Query("SELECT * FROM tbl_crop_stage WHERE uuid = :uuid LIMIT 1")
    DB_CropStage findById(String uuid);

    @Query("SELECT * FROM tbl_crop_stage WHERE crop_name LIKE :cropName")
    DB_CropStage findByName(String cropName);

    @Query("SELECT * FROM tbl_crop_stage WHERE crop LIKE :cropId")
    List<DB_CropStage> findByCropId(String cropId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DB_CropStage> cropStages);

    @Delete
    void delete(DB_CropStage cropStage);
}
