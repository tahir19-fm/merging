package com.taomish.app.android.farmsanta.farmer.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.taomish.app.android.farmsanta.farmer.models.db.cultivar.DB_Cultivar;

import java.util.List;

@Dao
public interface CultivarDAO {

    @Query("SELECT * FROM tbl_cultivar")
    List<DB_Cultivar> getAll();

    @Query("SELECT * FROM tbl_cultivar WHERE uuid IN (:uuid)")
    List<DB_Cultivar> loadAllByIds(String[] uuid);

    @Query("SELECT * FROM tbl_cultivar WHERE uuid = :uuid LIMIT 1")
    DB_Cultivar findById(String uuid);

    @Query("SELECT * FROM tbl_cultivar WHERE cultivar_name LIKE :name")
    List<DB_Cultivar> findByName(String name);

    @Query("SELECT * FROM tbl_cultivar WHERE crop LIKE :cropId")
    List<DB_Cultivar> findByCrop(String cropId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DB_Cultivar> cultivars);

    @Delete
    void delete(DB_Cultivar cultivar);
}
