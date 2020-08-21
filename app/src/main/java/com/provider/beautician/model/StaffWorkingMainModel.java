package com.provider.beautician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StaffWorkingMainModel {
    @SerializedName("staff_id")
    @Expose
    private String staffId;
    @SerializedName("saloon_id")
    @Expose
    private String saloonId;
    @SerializedName("staff_name")
    @Expose
    private String staffName;
    @SerializedName("staff_image")
    @Expose
    private String staffImage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("create_at")
    @Expose
    private String createAt;
    @SerializedName("modified_at")
    @Expose
    private String modifiedAt;
    @SerializedName("data")
    @Expose
    private List<StaffWorkingMainDataModel> data = null;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getSaloonId() {
        return saloonId;
    }

    public void setSaloonId(String saloonId) {
        this.saloonId = saloonId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffImage() {
        return staffImage;
    }

    public void setStaffImage(String staffImage) {
        this.staffImage = staffImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<StaffWorkingMainDataModel> getData() {
        return data;
    }

    public void setData(List<StaffWorkingMainDataModel> data) {
        this.data = data;
    }
}
