package com.provider.beautician.model;

public class StaffModel {
    private String staffId;
    private String staffName;
    private boolean isChecked = false;

    public StaffModel() {

    }

    public StaffModel(String staffId, String staffName) {
        this.staffId = staffId;
        this.staffName = staffName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
