
package com.taomish.app.android.farmsanta.farmer.models.api.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FarmOperationOriginal {

    @SerializedName("stringValue")
    @Expose
    private String stringValue;
    @SerializedName("kind")
    @Expose
    private String kind;

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

}
