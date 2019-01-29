package com.example.adteam7.team7_ad_client.data;

import java.util.HashMap;

public class ReturntoWarehouseDetailApi extends HashMap<String,String> {

    public ReturntoWarehouseDetailApi(String RequestId,String Description, String ItemId, int Quantity, String DepartmentId, String Location) {

        put("RequestId", RequestId);
        put("Description", Description);
        put("ItemId", ItemId);
        put("Quantity", String.valueOf(Quantity));
        put("DepartmentId", DepartmentId);
        put("Location", Location);


    }

}
