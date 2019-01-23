package com.example.adteam7.team7_ad_client.data;

public class PendingPO {

    //region Zan Tun Khine

    private String PONo;
    private String SupplierId;
    private String Status;
    private String OrderedBy; // Need to confirm
    private String Date;
    private Double Amount;
    private String ApprovedBy;

    public PendingPO(String PONo, String SupplierId, String Status, String OrderedBy, String Date, Double Amount, String ApprovedBy) {

        this.PONo = PONo;
        this.SupplierId = SupplierId;
        this.Status = Status;
        this.OrderedBy = OrderedBy;
        this.Date = Date;
        this.Amount = Amount;
        this.ApprovedBy = ApprovedBy;
    }

    public String getPONo() {
        return PONo;
    }

    public void setPONo(String PONo) {
        this.PONo = PONo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDate() {
        return Date;
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String getOrderedBy() {
        return OrderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        OrderedBy = orderedBy;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
    }

    //endregion

}
