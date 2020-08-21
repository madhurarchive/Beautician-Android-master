
package com.provider.beautician.CommonBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonArrayResponse<T> {

    @SerializedName("RESULT")
    @Expose
    private String rESULT;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("TotalAdCount")
    @Expose
    private String TotalAdCount;

    @SerializedName("Data")
    public List<T> Data;

    @SerializedName("Active_Count")
    @Expose
    private String activeCount;

    @SerializedName("InActive_Count")
    @Expose
    private String inActiveCount;

    @SerializedName("redirectUrl")
    @Expose
    private String redirectUrl;


    @SerializedName("ref")
    @Expose
    private String ref;

    @SerializedName("top_ads_list")
    @Expose
    public List<T> topAdsList;


    public List<T> getTopAdsList() {
        return topAdsList;
    }

    public void setTopAdsList(List<T> topAdsList) {
        this.topAdsList = topAdsList;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }


    public String getRESULT() {
        return rESULT;
    }

    public void setRESULT(String rESULT) {
        this.rESULT = rESULT;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return Data;
    }

    public void setData(List<T> data) {
        this.Data = data;
    }

    public String getTotalAdCount() {
        return TotalAdCount;
    }

    public void setTotalAdCount(String totalAdCount) {
        TotalAdCount = totalAdCount;
    }

    public String getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(String activeCount) {
        this.activeCount = activeCount;
    }

    public String getInActiveCount() {
        return inActiveCount;
    }

    public void setInActiveCount(String inActiveCount) {
        this.inActiveCount = inActiveCount;
    }
}
