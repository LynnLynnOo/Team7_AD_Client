package com.example.adteam7.team7_ad_client.data;

import java.util.Date;

/**
 * Created by dodo
 **/
public class DisbursementSationeryItem {

    private String TransactionId;
    private String ItemId;
    private String ItemName;
    private String Category;
    private int Quantity;
      private String TransactionRef;
      private Date TransactionDate;

    public DisbursementSationeryItem(String transactionId, String itemId, String itemName, String category, int quantity, String transactionRef, Date transactionDate) {
        TransactionId = transactionId;
        ItemId = itemId;
        ItemName = itemName;
        Category = category;
        Quantity = quantity;
        TransactionRef = transactionRef;
        TransactionDate = transactionDate;
    }

    public DisbursementSationeryItem() {
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

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getTransactionRef() {
        return TransactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        TransactionRef = transactionRef;
    }

    public Date getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        TransactionDate = transactionDate;
    }
}
