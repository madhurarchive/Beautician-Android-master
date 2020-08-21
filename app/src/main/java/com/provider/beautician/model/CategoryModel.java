package com.provider.beautician.model;

public class CategoryModel {
    private String categoryName;
    private String price;
    private String time;

    public CategoryModel(String categoryName, String price, String time) {
        this.categoryName = categoryName;
        this.price = price;
        this.time = time;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
