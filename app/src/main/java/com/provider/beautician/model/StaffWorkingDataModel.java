package com.provider.beautician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StaffWorkingDataModel {
    @SerializedName("hour_id")
    @Expose
    private String hourId;
    @SerializedName("first_shift_start")
    @Expose
    private String firstShiftStart;
    @SerializedName("first_shift_end")
    @Expose
    private String firstShiftEnd;
    @SerializedName("second_shift_start")
    @Expose
    private String secondShiftStart;
    @SerializedName("second_shift_end")
    @Expose
    private String secondShiftEnd;
    @SerializedName("shift_date")
    @Expose
    private String shiftDate;
    @SerializedName("repeat_type")
    @Expose
    private String repeatType;
    @SerializedName("end_repeat")
    @Expose
    private String endRepeat;
    @SerializedName("end_repeat_date")
    @Expose
    private String endRepeatDate;
    @SerializedName("saloon_id")
    @Expose
    private String saloonId;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("staff_id")
    @Expose
    private String staffId;
    @SerializedName("create_at")
    @Expose
    private String createAt;
    @SerializedName("modified_at")
    @Expose
    private String modifiedAt;

    public StaffWorkingDataModel(){}

    public String getHourId() {
        return hourId;
    }

    public void setHourId(String hourId) {
        this.hourId = hourId;
    }

    public String getFirstShiftStart() {
        return firstShiftStart;
    }

    public void setFirstShiftStart(String firstShiftStart) {
        this.firstShiftStart = firstShiftStart;
    }

    public String getFirstShiftEnd() {
        return firstShiftEnd;
    }

    public void setFirstShiftEnd(String firstShiftEnd) {
        this.firstShiftEnd = firstShiftEnd;
    }

    public String getSecondShiftStart() {
        return secondShiftStart;
    }

    public void setSecondShiftStart(String secondShiftStart) {
        this.secondShiftStart = secondShiftStart;
    }

    public String getSecondShiftEnd() {
        return secondShiftEnd;
    }

    public void setSecondShiftEnd(String secondShiftEnd) {
        this.secondShiftEnd = secondShiftEnd;
    }

    public String getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(String shiftDate) {
        this.shiftDate = shiftDate;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public String getEndRepeat() {
        return endRepeat;
    }

    public void setEndRepeat(String endRepeat) {
        this.endRepeat = endRepeat;
    }

    public String getEndRepeatDate() {
        return endRepeatDate;
    }

    public void setEndRepeatDate(String endRepeatDate) {
        this.endRepeatDate = endRepeatDate;
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

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
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
}
