package com.provider.beautician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StaffWorkingMainDataModel {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("data")
    @Expose
    private List<StaffWorkingDataModel> data = null;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StaffWorkingDataModel> getData() {
        return data;
    }

    public void setData(List<StaffWorkingDataModel> data) {
        this.data = data;
    }
}
