
package com.taomish.app.android.farmsanta.farmer.models.api.farmer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder;
import com.taomish.app.android.farmsanta.farmer.models.api.master.CropMaster;

import java.util.List;

public class Farmer {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("createdTimestamp")
    @Expose
    private String createdTimestamp;
    @SerializedName("updatedTimestamp")
    @Expose
    private String updatedTimestamp;
    @SerializedName("tenantId")
    @Expose
    private String tenantId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("midName")
    @Expose
    private String midName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("farmerGroup")
    @Expose
    private String farmerGroup;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("subDistrict")
    @Expose
    private String subDistrict;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("crop1")
    @Expose
    private String crop1 = null;
    @SerializedName("crop2")
    @Expose
    private String crop2 = null;
    @SerializedName("crop3")
    @Expose
    private String crop3 = null;
    @SerializedName("documents")
    @Expose
    private List<String> documents = null;
    @SerializedName("lands")
    @Expose
    private List<Land> lands = null;
    @SerializedName("languageId")
    @Expose
    private int languageId = 0;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("territory")
    @Expose
    private List<String> territory;
    @SerializedName("region")
    @Expose
    private List<String> region;
    @SerializedName("dataSource")
    @Expose
    private String dataSource;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sequence")
    @Expose
    private Long sequence;
    @SerializedName("profileImage")
    @Expose
    private String profileImage = null;

    @SerializedName("farmertag")
    @Expose
    private String farmerTag;

    @SerializedName("hasSmartphone")
    @Expose
    private Boolean hasPhone;

    private String regionName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(String updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFarmerGroup() {
        return farmerGroup;
    }

    public void setFarmerGroup(String farmerGroup) {
        this.farmerGroup = farmerGroup;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCrop1() {
        return crop1;
    }

    public void setCrop1(String crop1) {
        this.crop1 = crop1;
    }

    public String getCrop2() {
        return crop2;
    }

    public void setCrop2(String crop2) {
        this.crop2 = crop2;
    }

    public String getCrop3() {
        return crop3;
    }

    public void setCrop3(String crop3) {
        this.crop3 = crop3;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    public List<Land> getLands() {
        return lands;
    }

    public void setLands(List<Land> lands) {
        this.lands = lands;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getTerritory() {
        return territory;
    }

    public void setTerritory(List<String> territory) {
        this.territory = territory;
    }

    public List<String> getRegion() {
        return region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getFarmerTag() {
        return farmerTag;
    }

    public void setFarmerTag(String farmerTag) {
        this.farmerTag = farmerTag;
    }

    public Boolean getHasPhone() {
        return hasPhone;
    }

    public void setHasPhone(Boolean hasPhone) {
        this.hasPhone = hasPhone;
    }

}
