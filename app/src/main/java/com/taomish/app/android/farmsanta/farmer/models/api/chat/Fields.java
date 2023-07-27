
package com.taomish.app.android.farmsanta.farmer.models.api.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields {

    @SerializedName("farm_operation.original")
    @Expose
    private FarmOperationOriginal farmOperationOriginal;
    @SerializedName("farm_operation")
    @Expose
    private FarmOperation farmOperation;
    @SerializedName("crop_name.original")
    @Expose
    private CropNameOriginal cropNameOriginal;
    @SerializedName("crop_name")
    @Expose
    private CropName cropName;
    @SerializedName("userId")
    @Expose
    private UserId userId;

    public FarmOperationOriginal getFarmOperationOriginal() {
        return farmOperationOriginal;
    }

    public void setFarmOperationOriginal(FarmOperationOriginal farmOperationOriginal) {
        this.farmOperationOriginal = farmOperationOriginal;
    }

    public FarmOperation getFarmOperation() {
        return farmOperation;
    }

    public void setFarmOperation(FarmOperation farmOperation) {
        this.farmOperation = farmOperation;
    }

    public CropNameOriginal getCropNameOriginal() {
        return cropNameOriginal;
    }

    public void setCropNameOriginal(CropNameOriginal cropNameOriginal) {
        this.cropNameOriginal = cropNameOriginal;
    }

    public CropName getCropName() {
        return cropName;
    }

    public void setCropName(CropName cropName) {
        this.cropName = cropName;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

}
