package com.provider.beautician.model;

/**
 * Created by archive_infotech on 2/2/19.
 */

public class ListDataModel {
    private String header;
    private String subHeader;
    private int icon;
    private boolean checked;

    public ListDataModel(String header, int icon) {
        this.header = header;
        this.icon = icon;
    }

    public ListDataModel(String header, String subHeader, int icon) {
        this.header = header;
        this.subHeader = subHeader;
        this.icon = icon;
    }

    public ListDataModel(String header, String subHeader) {
        this.header = header;
        this.subHeader = subHeader;
    }

    public ListDataModel(String header, boolean checked) {
        this.header = header;
        this.checked = checked;
    }

    public ListDataModel(String header, String subHeader, boolean checked) {
        this.header = header;
        this.subHeader = subHeader;
        this.checked = checked;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSubHeader() {
        return subHeader;
    }

    public void setSubHeader(String subHeader) {
        this.subHeader = subHeader;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
