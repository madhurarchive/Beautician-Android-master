package com.provider.beautician.model;

import android.graphics.drawable.Drawable;

public class ColorPickerModel {
    String id;
    String color;
    Drawable view;
    boolean isChecked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Drawable getView() {
        return view;
    }

    public void setView(Drawable view) {
        this.view = view;
    }
}
