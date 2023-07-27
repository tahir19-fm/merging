package com.taomish.app.android.farmsanta.farmer.models.api.places;

public class FarmPlaces {
    public CharSequence placeId;
    public CharSequence description;

    public FarmPlaces(CharSequence placeId, CharSequence description) {
        this.placeId = placeId;
        this.description = description;
    }

    @Override
    public String toString() {
        return description.toString();
    }

}
