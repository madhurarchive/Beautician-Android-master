package com.provider.beautician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailySalesModel {

    @SerializedName("transaction_summary")
    @Expose
    private List<DailySalesTransactionModel> transactionSummary = null;
    @SerializedName("cashmovement")
    @Expose
    private List<SalesSummaryModel> cashmovement = null;

    public List<DailySalesTransactionModel> getTransactionSummary() {
        return transactionSummary;
    }

    public void setTransactionSummary(List<DailySalesTransactionModel> transactionSummary) {
        this.transactionSummary = transactionSummary;
    }

    public List<SalesSummaryModel> getCashmovement() {
        return cashmovement;
    }

    public void setCashmovement(List<SalesSummaryModel> cashmovement) {
        this.cashmovement = cashmovement;
    }
}
