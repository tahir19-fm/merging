package com.taomish.app.android.farmsanta.farmer.helper.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taomish.app.android.farmsanta.farmer.models.db.crop_stage.Stage;
import com.taomish.app.android.farmsanta.farmer.models.db.cultivar.CropDuration;
import com.taomish.app.android.farmsanta.farmer.models.db.cultivar.HarvestMonth;
import com.taomish.app.android.farmsanta.farmer.models.db.cultivar.Photo;
import com.taomish.app.android.farmsanta.farmer.models.db.cultivar.SowingMonth;
import com.taomish.app.android.farmsanta.farmer.models.db.cultivar.YieldPotential;

import java.lang.reflect.Type;
import java.util.List;

public class CultivarConverter {
    @TypeConverter
    public static CropDuration fromCropDurationString(String value) {
        return new Gson().fromJson(value, CropDuration.class);
    }

    @TypeConverter
    public static String fromCropDuration(CropDuration duration) {
        return new Gson().toJson(duration);
    }

    @TypeConverter
    public static HarvestMonth fromHarvestMonthString(String value) {
        return new Gson().fromJson(value, HarvestMonth.class);
    }

    @TypeConverter
    public static String fromHarvestMonth(HarvestMonth month) {
        return new Gson().toJson(month);
    }

    @TypeConverter
    public static YieldPotential fromYieldPotentialString(String value) {
        return new Gson().fromJson(value, YieldPotential.class);
    }

    @TypeConverter
    public static String fromYieldPotential(YieldPotential potential) {
        return new Gson().toJson(potential);
    }

    @TypeConverter
    public static List<Photo> fromPhotoString(String value) {
        Type listType = new TypeToken<List<Photo>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromPhotoList(List<Photo> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static List<SowingMonth> fromSowingMonthString(String value) {
        Type listType = new TypeToken<List<SowingMonth>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromSowingMonthList(List<SowingMonth> list) {
        return new Gson().toJson(list);
    }
}
