package com.taomish.app.android.farmsanta.farmer.interfaces.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.taomish.app.android.farmsanta.farmer.models.api.master.Region
import com.taomish.app.android.farmsanta.farmer.models.api.master.Territory

@Dao
interface MasterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTerritory(territories: List<Territory>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRegion(territories: List<Region>)

    @Query("SELECT * FROM Territory")
    fun getAllTerritories(): LiveData<List<Territory>>

    @Query("SELECT * FROM Region")
    fun getAllRegions(): LiveData<List<Region>>
}