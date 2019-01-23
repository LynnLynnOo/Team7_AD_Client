package com.example.adteam7.team7_ad_client.data;

import java.util.HashMap;

public class AdjustmentItem extends HashMap<String, String> {

    public AdjustmentItem(String itemId, String category, String itemDescription, int quantity, int currentStock, double amount) {
        put("itemId", itemId);
        put("category", category);
        put("itemDescription", itemDescription);
        put("quantity", String.valueOf(quantity));
        put("currentStock", String.valueOf(currentStock));
        put("amount", String.valueOf(amount));

    }

}
