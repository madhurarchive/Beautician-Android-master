package com.provider.beautician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StaffMemberDetailModel {
    @SerializedName("staff_id")
    @Expose
    private String staffId;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("staff_title")
    @Expose
    private String staffTitle;
    @SerializedName("staff_notes")
    @Expose
    private String staffNotes;
    @SerializedName("staff_start_date")
    @Expose
    private String staffStartDate;
    @SerializedName("staff_end_date")
    @Expose
    private String staffEndDate;
    @SerializedName("staff_appointment_color")
    @Expose
    private String staffAppointmentColor;
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
    @SerializedName("service_commission")
    @Expose
    private String serviceCommission;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStaffTitle() {
        return staffTitle;
    }

    public void setStaffTitle(String staffTitle) {
        this.staffTitle = staffTitle;
    }

    public String getStaffNotes() {
        return staffNotes;
    }

    public void setStaffNotes(String staffNotes) {
        this.staffNotes = staffNotes;
    }

    public String getStaffStartDate() {
        return staffStartDate;
    }

    public void setStaffStartDate(String staffStartDate) {
        this.staffStartDate = staffStartDate;
    }

    public String getStaffEndDate() {
        return staffEndDate;
    }

    public void setStaffEndDate(String staffEndDate) {
        this.staffEndDate = staffEndDate;
    }

    public String getStaffAppointmentColor() {
        return staffAppointmentColor;
    }

    public void setStaffAppointmentColor(String staffAppointmentColor) {
        this.staffAppointmentColor = staffAppointmentColor;
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

    public String getServiceCommission() {
        return serviceCommission;
    }

    public void setServiceCommission(String serviceCommission) {
        this.serviceCommission = serviceCommission;
    }
}
