package com.taomish.app.android.farmsanta.farmer.helper.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taomish.app.android.farmsanta.farmer.models.db.soil_health.Parameter;

import java.lang.reflect.Type;
import java.util.List;

public class SoilHealthConverter {
    @TypeConverter
    public static List<Parameter> fromString(String value) {
        Type listType = new TypeToken<List<Parameter>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Parameter> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
