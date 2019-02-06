package com.example.adteam7.team7_ad_client.data;

/**
 * Created by dodo
 **/
public class ReturnItemPostBack {

    private String RequestId;
    private String ItemId;
    private int Quantity;

    public ReturnItemPostBack(String requestId, String itemId, int quantity) {
        RequestId = requestId;
        ItemId = itemId;
        Quantity = quantity;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
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
}
