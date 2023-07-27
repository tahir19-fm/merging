package com.taomish.app.android.farmsanta.farmer.libs.room_db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.taomish.app.android.farmsanta.farmer.helper.db.CropConverter;
import com.taomish.app.android.farmsanta.farmer.helper.db.CropStageConverter;
import com.taomish.app.android.farmsanta.farmer.helper.db.CultivarConverter;
import com.taomish.app.android.farmsanta.farmer.helper.db.SoilHealthConverter;
import com.taomish.app.android.farmsanta.farmer.interfaces.db.CropDAO;
import com.taomish.app.android.farmsanta.farmer.interfaces.db.CropRecommendationDAO;
import com.taomish.app.android.farmsanta.farmer.interfaces.db.CropStageDAO;
import com.taomish.app.android.farmsanta.farmer.interfaces.db.CultivarDAO;
import com.taomish.app.android.farmsanta.farmer.interfaces.db.GlobalIndicatorDAO;
import com.taomish.app.android.farmsanta.farmer.interfaces.db.MasterDao;
import com.taomish.app.android.farmsanta.farmer.interfaces.db.PostTagDao;
import com.taomish.app.android.farmsanta.farmer.interfaces.db.ScoutingDAO;
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.CategoryConverter;
import com.taomish.app.android.farmsanta.farmer.models.api.farmscout.CategoryItem;
import com.taomish.app.android.farmsanta.farmer.models.api.master.Region;
import com.taomish.app.android.farmsanta.farmer.models.api.master.Territory;
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage;
import com.taomish.app.android.farmsanta.farmer.models.api.tags.TrendingTags;
import com.taomish.app.android.farmsanta.farmer.models.db.crop.DB_Crop;
import com.taomish.app.android.farmsanta.farmer.models.db.crop_stage.DB_CropStage;
import com.taomish.app.android.farmsanta.farmer.models.db.cultivar.DB_Cultivar;
import com.taomish.app.android.farmsanta.farmer.models.db.global_indicator.DB_GlobalIndicator;
import com.taomish.app.android.farmsanta.farmer.models.db.soil_health.DB_CropRecommendation;
import com.taomish.app.android.farmsanta.farmer.models.db.soil_health.DB_SoilHealth;
import com.taomish.app.android.farmsanta.farmer.interfaces.db.SoilHealthDAO;

@Database(entities = {
        DB_SoilHealth.class,
        DB_Crop.class,
        DB_CropStage.class,
        DB_GlobalIndicator.class,
        DB_Cultivar.class,
        DB_CropRecommendation.class,
        CategoryItem.class,
        TrendingTags.class,
        CropStage.class,
        Region.class,
        Territory.class
}, version = 7)
@TypeConverters({SoilHealthConverter.class, CropConverter.class, CropStageConverter.class, CultivarConverter.class, CategoryConverter.class})
public abstract class RoomDB extends RoomDatabase {
    public abstract SoilHealthDAO soilHealthDAO();
    public abstract CropDAO cropDAO();
    public abstract CropStageDAO cropStageDAO();
    public abstract GlobalIndicatorDAO globalIndicatorDAO();
    public abstract CultivarDAO cultivarDAO();
    public abstract CropRecommendationDAO cropRecommendationDAO();
    public abstract ScoutingDAO scoutingDAO();
    public abstract PostTagDao postTagDao();
    public abstract MasterDao masterDao();
}


