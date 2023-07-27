package com.taomish.app.android.farmsanta.farmer.models.api.home;

public class AllFarmSanta {
    private String text;
    private int iconId;
    private String textColor;
    private String count;

    public String getText() {
        return text;
    }

    public AllFarmSanta setText(String text) {
        this.text = text;
        return this;
    }

    public int getIconId() {
        return iconId;
    }

    public AllFarmSanta setIconId(int iconId) {
        this.iconId = iconId;
        return this;
    }

    public String getTextColor() {
        return textColor;
    }

    public AllFarmSanta setTextColor(String textColor) {
        this.textColor = textColor;
        return this;
    }
    public String getCount() {
        return count;
    }

    public AllFarmSanta setCount(String count) {
        this.count = count;
        return this;
    }

}
