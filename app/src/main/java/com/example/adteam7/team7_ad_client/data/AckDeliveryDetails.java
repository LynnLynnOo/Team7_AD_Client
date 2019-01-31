package com.example.adteam7.team7_ad_client.data;

import java.util.HashMap;

public class AckDeliveryDetails extends HashMap<String, String> {

    //region Author Zan Tun Khine

    public AckDeliveryDetails(String ItemId, String Quantity, String Remarks, String UnitPrice, String DelOrderNo, String SupplierId, String AcceptedBy, String PONo) {
        put("ItemId", ItemId);
        put("Quantity", Quantity);
        put("Remarks", Remarks);
        put("UnitPrice", UnitPrice);
        put("DelOrderNo", DelOrderNo);
        put("SupplierId", SupplierId);
        put("AcceptedBy", AcceptedBy);
        put("PONo", PONo);
    }

    //endregion
}

