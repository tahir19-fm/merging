package com.taomish.app.android.farmsanta.farmer.models.api.message;

import java.util.Date;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message implements Comparable<Message> {

    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdTimestamp")
    @Expose
    private String createdTimestamp;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("links")
    @Expose
    private List<String> links = null;
    @SerializedName("parentId")
    @Expose
    private String parentId;
    @SerializedName("regions")
    @Expose
    private List<String> regions = null;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("tenantId")
    @Expose
    private String tenantId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("updatedTimestamp")
    @Expose
    private String updatedTimestamp;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("selfLike")
    @Expose
    private Integer selfLike;
    @SerializedName("languageId")
    @Expose
    private Integer languageId;
    @SerializedName("agronomyManager")
    @Expose
    private Boolean agronomyManager;
    @SerializedName("superAdmin")
    @Expose
    private Boolean superAdmin;

    private Boolean isMyPost;

    private Boolean isBookMarked;

    public Date createdDate;

    public Integer getSelfLike() {
        return selfLike;
    }

    public void setSelfLike(Integer selfLike) {
        this.selfLike = selfLike;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(String updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int compareTo(Message o) {
        return createdDate.compareTo(o.createdDate);
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Boolean getMyPost() {
        return isMyPost;
    }

    public void setMyPost(Boolean myPost) {
        isMyPost = myPost;
    }

    public Boolean getBookMarked() {
        return isBookMarked;
    }

    public void setBookMarked(Boolean bookMarked) {
        isBookMarked = bookMarked;
    }

    public Boolean getAgronomyManager() {
        return agronomyManager;
    }

    public void setAgronomyManager(Boolean agronomyManager) {
        this.agronomyManager = agronomyManager;
    }

    public Boolean getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }
}
