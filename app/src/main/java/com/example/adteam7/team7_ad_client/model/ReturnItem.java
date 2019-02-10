package com.example.adteam7.team7_ad_client.model;

import java.io.Serializable;

/**
 * Created by Kay Thi Swe Tun
 **/
public class ReturnItem implements Serializable {

    private String ItemId;
    private String Description;
    private int Quantity;
    private String Category;
    private String Location;

    public ReturnItem(String itemID, String description, int quantity, String category, String location) {
        ItemId = itemID;
        Description = description;
        Quantity = quantity;
        Category = category;
        Location = location;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemID) {
        ItemId = itemID;
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
