package com.provider.beautician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StaffLocationModel {
    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("saloon_id")
    @Expose
    private String saloonId;
    @SerializedName("busineess_name")
    @Expose
    private String busineessName;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("time_zone")
    @Expose
    private String timeZone;
    @SerializedName("time_format")
    @Expose
    private String timeFormat;
    @SerializedName("week_start")
    @Expose
    private String weekStart;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("modified_at")
    @Expose
    private String modifiedAt;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getSaloonId() {
        return saloonId;
    }

    public void setSaloonId(String saloonId) {
        this.saloonId = saloonId;
    }

    public String getBusineessName() {
        return busineessName;
    }

    public void setBusineessName(String busineessName) {
        this.busineessName = busineessName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(String weekStart) {
        this.weekStart = weekStart;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
