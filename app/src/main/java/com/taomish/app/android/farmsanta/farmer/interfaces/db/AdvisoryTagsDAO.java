package com.taomish.app.android.farmsanta.farmer.interfaces.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.taomish.app.android.farmsanta.farmer.models.db.advisory_tag.DB_AdvisoryTag;

import java.util.List;

@Dao
public interface AdvisoryTagsDAO {
    @Query("SELECT * FROM tbl_advisory_tag")
    List<DB_AdvisoryTag> getAll();

    @Query("SELECT * FROM tbl_advisory_tag WHERE uuid IN (:uuid)")
    List<DB_AdvisoryTag> loadAllByIds(String[] uuid);

    @Query("SELECT * FROM tbl_advisory_tag WHERE uuid = :uuid LIMIT 1")
    DB_AdvisoryTag findById(String uuid);

    @Query("SELECT * FROM tbl_advisory_tag WHERE global_indicator_uuid = :indicatorUuid")
    List<DB_AdvisoryTag> findByGlobalIndicator(String indicatorUuid);

    @Query("SELECT * FROM tbl_advisory_tag WHERE group_name = :name")
    List<DB_AdvisoryTag> findByGroupName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DB_AdvisoryTag> cropSpecificRecommendations);

    @Delete
    void delete(DB_AdvisoryTag cropSpecificRecommendation);

    @Query("DELETE FROM tbl_advisory_tag")
    void deleteAll();
}
