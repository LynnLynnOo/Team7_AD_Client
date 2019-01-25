package com.example.adteam7.team7_ad_client.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dodo
 **/
public class DisbursementSationeryItem implements Serializable {

    private String DisbursementNo;
    private String DepartmentId;
    private String ItemId;
    private String Description;
    private int Quantity;
    private int receivedQty;

    public DisbursementSationeryItem(String disbursementNo, String departmentId, String itemId, String description, int quantity) {
        DisbursementNo = disbursementNo;
        DepartmentId = departmentId;
        ItemId = itemId;
        Description = description;
        Quantity = quantity;
    }

    public int getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(int receivedQty) {
        this.receivedQty = receivedQty;
    }

    public DisbursementSationeryItem(String disbursementNo, String departmentId, String itemId, String description, int quantity, int receivedQty) {
        DisbursementNo = disbursementNo;
        DepartmentId = departmentId;
        ItemId = itemId;
        Description = description;
        Quantity = quantity;
        this.receivedQty = receivedQty;
    }

    public DisbursementSationeryItem() {
    }

    public String getDisbursementNo() {
        return DisbursementNo;
    }

    public void setDisbursementNo(String disbursementNo) {
        DisbursementNo = disbursementNo;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
