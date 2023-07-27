package com.taomish.app.android.farmsanta.farmer.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.taomish.app.android.farmsanta.farmer.models.db.crop.DB_Crop;

import java.util.List;

@Dao
public interface CropDAO {

    @Query("SELECT * FROM tbl_crop")
    List<DB_Crop> getAll();

    @Query("SELECT * FROM tbl_crop WHERE uuid IN (:uuid)")
    List<DB_Crop> loadAllByIds(String[] uuid);

    @Query("SELECT * FROM tbl_crop WHERE uuid = :uuid LIMIT 1")
    DB_Crop findById(String uuid);

    @Query("SELECT * FROM tbl_crop WHERE crop_name LIKE :cropName LIMIT 1")
    DB_Crop findByName(String cropName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DB_Crop> soilHealths);

    @Delete
    void delete(DB_Crop soilHealth);
}
