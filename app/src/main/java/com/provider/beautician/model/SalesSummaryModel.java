package com.provider.beautician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesSummaryModel {
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("gross")
    @Expose
    private String gross;
    @SerializedName("refund")
    @Expose
    private String refund;
    @SerializedName("total")
    @Expose
    private String total;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getGross() {
        return gross;
    }

    public void setGross(String gross) {
        this.gross = gross;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
