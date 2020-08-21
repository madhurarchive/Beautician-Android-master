package com.provider.beautician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDataModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("budget_price")
    @Expose
    private String budgetPrice;
    @SerializedName("average_rating")
    @Expose
    private String averageRating;
    @SerializedName("total_rating")
    @Expose
    private String totalRating;
    @SerializedName("review_count")
    @Expose
    private String reviewCount;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("cover_image")
    @Expose
    private String coverImage;
    @SerializedName("social_fb_id")
    @Expose
    private String socialFbId;
    @SerializedName("social_google_id")
    @Expose
    private String socialGoogleId;
    @SerializedName("opening_time")
    @Expose
    private String openingTime;
    @SerializedName("closing_time")
    @Expose
    private String closingTime;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("is_mail_verified")
    @Expose
    private String isMailVerified;
    @SerializedName("is_phone_verified")
    @Expose
    private String isPhoneVerified;
    @SerializedName("has_home_service")
    @Expose
    private String hasHomeService;
    @SerializedName("has_saloon_service")
    @Expose
    private String hasSaloonService;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("modified_at")
    @Expose
    private String modifiedAt;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBudgetPrice() {
        return budgetPrice;
    }

    public void setBudgetPrice(String budgetPrice) {
        this.budgetPrice = budgetPrice;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getSocialFbId() {
        return socialFbId;
    }

    public void setSocialFbId(String socialFbId) {
        this.socialFbId = socialFbId;
    }

    public String getSocialGoogleId() {
        return socialGoogleId;
    }

    public void setSocialGoogleId(String socialGoogleId) {
        this.socialGoogleId = socialGoogleId;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getIsMailVerified() {
        return isMailVerified;
    }

    public void setIsMailVerified(String isMailVerified) {
        this.isMailVerified = isMailVerified;
    }

    public String getIsPhoneVerified() {
        return isPhoneVerified;
    }

    public void setIsPhoneVerified(String isPhoneVerified) {
        this.isPhoneVerified = isPhoneVerified;
    }

    public String getHasHomeService() {
        return hasHomeService;
    }

    public void setHasHomeService(String hasHomeService) {
        this.hasHomeService = hasHomeService;
    }

    public String getHasSaloonService() {
        return hasSaloonService;
    }

    public void setHasSaloonService(String hasSaloonService) {
        this.hasSaloonService = hasSaloonService;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
