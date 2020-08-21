package com.provider.beautician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("saloon_id")
    @Expose
    private String saloonId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("modified_at")
    @Expose
    private String modifiedAt;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("notiication_via")
    @Expose
    private String notiicationVia;
    @SerializedName("client_note")
    @Expose
    private String clientNote;
    @SerializedName("client_note_show_all_booking")
    @Expose
    private String clientNoteShowAllBooking;
    @SerializedName("accept_marketing_notification")
    @Expose
    private String acceptMarketingNotification;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("fb_social_id")
    @Expose
    private String fbSocialId;
    @SerializedName("apple_id")
    @Expose
    private String appleId;
    @SerializedName("google_social_id")
    @Expose
    private String googleSocialId;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("is_mobile_verified")
    @Expose
    private String isMobileVerified;
    @SerializedName("is_email_verified")
    @Expose
    private String isEmailVerified;
    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("birth_day")
    @Expose
    private String birthDay;
    @SerializedName("birth_year")
    @Expose
    private String birthYear;
    @SerializedName("android_token")
    @Expose
    private String androidToken;
    @SerializedName("ios_token")
    @Expose
    private String iosToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSaloonId() {
        return saloonId;
    }

    public void setSaloonId(String saloonId) {
        this.saloonId = saloonId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNotiicationVia() {
        return notiicationVia;
    }

    public void setNotiicationVia(String notiicationVia) {
        this.notiicationVia = notiicationVia;
    }

    public String getClientNote() {
        return clientNote;
    }

    public void setClientNote(String clientNote) {
        this.clientNote = clientNote;
    }

    public String getClientNoteShowAllBooking() {
        return clientNoteShowAllBooking;
    }

    public void setClientNoteShowAllBooking(String clientNoteShowAllBooking) {
        this.clientNoteShowAllBooking = clientNoteShowAllBooking;
    }

    public String getAcceptMarketingNotification() {
        return acceptMarketingNotification;
    }

    public void setAcceptMarketingNotification(String acceptMarketingNotification) {
        this.acceptMarketingNotification = acceptMarketingNotification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFbSocialId() {
        return fbSocialId;
    }

    public void setFbSocialId(String fbSocialId) {
        this.fbSocialId = fbSocialId;
    }

    public String getAppleId() {
        return appleId;
    }

    public void setAppleId(String appleId) {
        this.appleId = appleId;
    }

    public String getGoogleSocialId() {
        return googleSocialId;
    }

    public void setGoogleSocialId(String googleSocialId) {
        this.googleSocialId = googleSocialId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getIsMobileVerified() {
        return isMobileVerified;
    }

    public void setIsMobileVerified(String isMobileVerified) {
        this.isMobileVerified = isMobileVerified;
    }

    public String getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(String isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getAndroidToken() {
        return androidToken;
    }

    public void setAndroidToken(String androidToken) {
        this.androidToken = androidToken;
    }

    public String getIosToken() {
        return iosToken;
    }

    public void setIosToken(String iosToken) {
        this.iosToken = iosToken;
    }
}
