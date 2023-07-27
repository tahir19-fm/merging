package com.taomish.app.android.farmsanta.farmer.interfaces.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.CategoryItem
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage

@Dao
interface ScoutingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<CategoryItem?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllScoutingFiled(categories: List<CropStage?>?)

    @Query("SELECT * FROM CropStage where groupName = :groupName")
    fun getScoutingDropDownField(groupName: String): LiveData<List<CropStage>?>?

    @Query("SELECT * FROM CategoryItem")
    fun getAll(): List<CategoryItem?>?
}