package com.taomish.app.android.farmsanta.farmer.helper.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taomish.app.android.farmsanta.farmer.models.db.crop.Photo;

import java.lang.reflect.Type;
import java.util.List;

public class CropConverter {
    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Photo> fromPhotoString(String value) {
        Type listType = new TypeToken<List<Photo>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromPhotoList(List<Photo> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
