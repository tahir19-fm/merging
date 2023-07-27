package com.taomish.app.android.farmsanta.farmer.models.api.master;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Territory {

	@NonNull
	@PrimaryKey
	@SerializedName("uuid")
	private String uuid = "";

	@SerializedName("createdBy")
	private String createdBy;

	@SerializedName("updatedBy")
	private String updatedBy;

	@SerializedName("createdTimestamp")
	private String createdTimestamp;

	@SerializedName("updatedTimestamp")
	private String updatedTimestamp;

	@SerializedName("tenantId")
	private String tenantId;

	@SerializedName("territoryName")
	private String territoryName;

	@SerializedName("countryName")
	private String countryName;

	@SerializedName("description")
	private String description;

	@SerializedName("languages")
	private List<String> languages;

	@SerializedName("status")
	private String status;

	@SerializedName("regionLabel")
	private String regionLabel;

	@SerializedName("countyLabel")
	private String countyLabel;

	@SerializedName("subcountyLabel")
	private String subcountyLabel;

	@SerializedName("villageLabel")
	private String villageLabel;

	@SerializedName("phoneCode")
	private int phoneCode;

	public String getRegionLabel() {
		return regionLabel;
	}

	public void setRegionLabel(String regionLabel) {
		this.regionLabel = regionLabel;
	}

	public String getCountyLabel() {
		return countyLabel;
	}

	public void setCountyLabel(String countyLabel) {
		this.countyLabel = countyLabel;
	}

	public String getSubcountyLabel() {
		return subcountyLabel;
	}

	public void setSubcountyLabel(String subcountyLabel) {
		this.subcountyLabel = subcountyLabel;
	}

	public String getVillageLabel() {
		return villageLabel;
	}

	public void setVillageLabel(String villageLabel) {
		this.villageLabel = villageLabel;
	}

	public int getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(int phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getTerritoryName() {
		return territoryName;
	}

	public String getUpdatedBy(){
		return updatedBy;
	}

	public List<String> getLanguages(){
		return languages;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public String getCreatedTimestamp(){
		return createdTimestamp;
	}

	public String getTenantId(){
		return tenantId;
	}

	public String getDescription(){
		return description;
	}

	public String getCountryName(){
		return countryName;
	}

	public String getUpdatedTimestamp(){
		return updatedTimestamp;
	}

	@NonNull
	public String getUuid(){
		return uuid;
	}

	public String getStatus(){
		return status;
	}

	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public void setUpdatedTimestamp(String updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public void setUuid(@NonNull String uuid) {
		this.uuid = uuid;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}