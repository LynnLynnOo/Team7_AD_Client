package com.example.adteam7.team7_ad_client.data;

import android.util.Log;

import com.example.adteam7.team7_ad_client.network.JSONParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReturntoWarehouse extends HashMap<String, String> {
    static String host = "172.17.101.59";
    static String baseURL;

    static {
        baseURL = String.format("http://%s/Team7AD/api/returntowarehouse/getitemlist", host);
    }

    public ReturntoWarehouse(String RequestId, String Description, int Quantity, String DepartmentId, String Location) {
        put("RequestId", RequestId);
        put("Description", Description);
        put("Quantity", String.valueOf(Quantity));
        put("DepartmentId", DepartmentId);
        put("LocationId", Location);
    }

    public static List<String> Readlist() {
        List<String> list = new ArrayList<String>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL);
            for (int i = 0; i < a.length(); i++) {
                String b = a.getString(i);
                list.add(b);
            }
        } catch (Exception e) {
            Log.e("StationeryRequest", "JSONArray error");
        }
        return (list);
    }


}



