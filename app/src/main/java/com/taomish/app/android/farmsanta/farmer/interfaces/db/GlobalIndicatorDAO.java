package com.taomish.app.android.farmsanta.farmer.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.taomish.app.android.farmsanta.farmer.models.db.crop_stage.DB_CropStage;
import com.taomish.app.android.farmsanta.farmer.models.db.global_indicator.DB_GlobalIndicator;

import java.util.List;

@Dao
public interface GlobalIndicatorDAO {

    @Query("SELECT * FROM tbl_global_indicator")
    List<DB_GlobalIndicator> getAll();

    @Query("SELECT * FROM tbl_global_indicator WHERE uuid IN (:uuid)")
    List<DB_GlobalIndicator> loadAllByIds(String[] uuid);

    @Query("SELECT * FROM tbl_global_indicator WHERE uuid = :uuid LIMIT 1")
    DB_GlobalIndicator findById(String uuid);

    @Query("SELECT * FROM tbl_global_indicator WHERE name LIKE :name")
    DB_GlobalIndicator findByName(String name);

    @Query("SELECT * FROM tbl_global_indicator WHERE group_name LIKE :group")
    List<DB_GlobalIndicator> findByGroup(String group);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DB_GlobalIndicator> globalIndicators);

    @Delete
    void delete(DB_GlobalIndicator globalIndicator);
}
