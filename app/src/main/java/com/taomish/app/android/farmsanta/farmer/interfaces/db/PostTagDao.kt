package com.taomish.app.android.farmsanta.farmer.interfaces.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.taomish.app.android.farmsanta.farmer.models.api.tags.TrendingTags

@Dao
interface PostTagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTrendingTags(trending: TrendingTags)

    @Query("SELECT * FROM TrendingTags")
    fun getAllTrendingTags(): LiveData<TrendingTags>
}