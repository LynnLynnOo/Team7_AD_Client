package com.example.adteam7.team7_ad_client.data;

public class PendingPODetails {

    //region From TransactionDetail

    private String ItemId;
    private int Quantity;
    private String Remarks;
    private String TransactionRef;
    private Double UnitPrice;

    //endregion

    //region From Pending PO

    private String PONo;
    private String SupplierId;
    private String Status;
    private String OrderedBy;
    private String Date;
    private Double Amount;
    private Double UnitAmount;

    //endregion

    public PendingPODetails(String ItemId, int Quantity, String Remarks, String TransactionRef, Double UnitPrice, String PONo, String SupplierId, String Status, String OrderedBy, String Date, Double Amount, Double UnitAmount) {
        this.ItemId = ItemId;
        this.Quantity = Quantity;
        this.Remarks = Remarks;
        this.TransactionRef = TransactionRef;
        this.UnitPrice = UnitPrice;
        this.PONo = PONo;
        this.SupplierId = SupplierId;
        this.Status = Status;
        this.OrderedBy = OrderedBy;
        this.Date = Date;
        this.Amount = Amount;
        this.UnitAmount = UnitAmount;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getTransactionRef() {
        return TransactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        TransactionRef = transactionRef;
    }

    public Double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getPONo() {
        return PONo;
    }

    public void setPONo(String PONo) {
        this.PONo = PONo;
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOrderedBy() {
        return OrderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        OrderedBy = orderedBy;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public Double getUnitAmount() {
        return UnitAmount;
    }

    public void setUnitAmount(Double unitAmount) {
        UnitAmount = unitAmount;
    }

}

