
package com.taomish.app.android.farmsanta.farmer.models.api.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("subdivision")
    @Expose
    private Object subdivision;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Object subdivision) {
        this.subdivision = subdivision;
    }

}
