package com.taomish.app.android.farmsanta.farmer.datamodel;

public class FilterData {
    private final String title;
    public int image;
    public FilterData(String title, int image) {
        this.title = title;
        this.image=image;
    }
    public String getTitle() {
        return title;
    }
    public int getImage() {
        return image;
    }


}
