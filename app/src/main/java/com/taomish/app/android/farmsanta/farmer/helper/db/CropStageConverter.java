package com.taomish.app.android.farmsanta.farmer.helper.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taomish.app.android.farmsanta.farmer.models.db.crop_stage.Stage;

import java.lang.reflect.Type;
import java.util.List;

public class CropStageConverter {
    @TypeConverter
    public static List<Stage> fromStageString(String value) {
        Type listType = new TypeToken<List<Stage>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromStageList(List<Stage> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
