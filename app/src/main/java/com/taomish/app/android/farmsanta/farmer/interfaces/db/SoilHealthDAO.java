package com.taomish.app.android.farmsanta.farmer.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.taomish.app.android.farmsanta.farmer.models.db.soil_health.DB_SoilHealth;

import java.util.List;

@Dao
public interface SoilHealthDAO {

    @Query("SELECT * FROM tbl_soil_health")
    List<DB_SoilHealth> getAll();

    @Query("SELECT * FROM tbl_soil_health WHERE uuid IN (:uuid)")
    List<DB_SoilHealth> loadAllByIds(String[] uuid);

    @Query("SELECT * FROM tbl_soil_health WHERE uuid = :uuid LIMIT 1")
    DB_SoilHealth findById(String uuid);

    @Query("SELECT * FROM tbl_soil_health WHERE lab_name LIKE :labName LIMIT 1")
    DB_SoilHealth findByName(String labName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DB_SoilHealth> soilHealths);

    @Delete
    void delete(DB_SoilHealth soilHealth);
}
