package com.provider.beautician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClosedDateModel {

    @SerializedName("close_id")
    @Expose
    private String closeId;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_data")
    @Expose
    private String toData;
    @SerializedName("saloon_id")
    @Expose
    private String saloonId;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("dates_array")
    @Expose
    private String datesArray;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("create_at")
    @Expose
    private String createAt;
    @SerializedName("modified_at")
    @Expose
    private String modifiedAt;
    @SerializedName("day_count")
    @Expose
    private Integer dayCount;

    public String getCloseId() {
        return closeId;
    }

    public void setCloseId(String closeId) {
        this.closeId = closeId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToData() {
        return toData;
    }

    public void setToData(String toData) {
        this.toData = toData;
    }

    public String getSaloonId() {
        return saloonId;
    }

    public void setSaloonId(String saloonId) {
        this.saloonId = saloonId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getDatesArray() {
        return datesArray;
    }

    public void setDatesArray(String datesArray) {
        this.datesArray = datesArray;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }
}
