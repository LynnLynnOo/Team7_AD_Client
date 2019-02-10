package com.example.adteam7.team7_ad_client.model;

public class RequestTransactionDetailApiModel {
    private String TransactionId;
    private String ItemId;
    private String Quantity;
    private String TransactionRef;
    private String UnitPrice;

    public RequestTransactionDetailApiModel(String transactionId, String itemId, String quantity, String transactionRef, String unitPrice) {
        TransactionId = transactionId;
        ItemId = itemId;
        Quantity = quantity;
        TransactionRef = transactionRef;
        UnitPrice = unitPrice;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTransactionRef() {
        return TransactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        TransactionRef = transactionRef;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }
}
