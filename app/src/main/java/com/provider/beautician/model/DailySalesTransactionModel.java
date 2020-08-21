package com.provider.beautician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailySalesTransactionModel {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("gross")
    @Expose
    private Integer gross;
    @SerializedName("refund")
    @Expose
    private String refund;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("sale_qty")
    @Expose
    private Integer saleQty;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getGross() {
        return gross;
    }

    public void setGross(Integer gross) {
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

    public Integer getSaleQty() {
        return saleQty;
    }

    public void setSaleQty(Integer saleQty) {
        this.saleQty = saleQty;
    }
}
