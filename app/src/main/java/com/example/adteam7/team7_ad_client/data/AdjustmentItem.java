package com.example.adteam7.team7_ad_client.data;

import java.util.HashMap;

public class AdjustmentItem {

    public String itemId;

    public String category;

    public String description;

    public String unitOfMeasure;

    public int quantityWareHouse;

    public double price;

    public int quantity;

    public AdjustmentItem(String itemId, String category, String description, String unitOfMeasure, int quantityWareHouse, double price) {
        this.itemId = itemId;
        this.category = category;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
        this.quantityWareHouse = quantityWareHouse;
        this.price = price;
    }
}
