package com.example.adteam7.team7_ad_client.data;

import android.util.Log;

import com.example.adteam7.team7_ad_client.network.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StationeryRequest extends HashMap<String, String> {
    static String host = "172.23.46.156";
    static String baseURL;

    static {
        baseURL = String.format("http://%s/Team7ADProject/api/StationeryRequest", host);
    }

    public StationeryRequest(String RequestId, String RequestedBy, String ApprovedBy, String DepartmentId, String Status, String RequestDate) {
        put("RequestId", RequestId);
        put("RequestedBy", RequestedBy);
        put("ApprovedBy", ApprovedBy);
        put("DepartmentId", DepartmentId);
        put("Status", Status);
        put("RequestDate", RequestDate);
    }

    public static List<String> ReadStationeryRequest1() {
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

    public static StationeryRequest ReadStationeryRequest(String requestId) {
        try {
            JSONObject a = JSONParser.getJSONFromUrl(String.format("%s/%s", baseURL, requestId));
            StationeryRequest e = new StationeryRequest(
                    a.getString("RequestId"),
                    a.getString("RequestedBy"),
                    a.getString("ApprovedBy"),
                    a.getString("DepartmentId"),
                    a.getString("RequestDate"),
                    a.getString("Status")
            );
            return e;
        } catch (Exception e) {
            Log.e("StationeryRequest", "JSONArray error");
        }
        return (null);
    }

    public static List<StationeryRequest> ReadStationeryRequest2() {
        List<StationeryRequest> list = new ArrayList<StationeryRequest>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "/brief");
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new StationeryRequest(
                        b.getString("RequestId"),
                        b.getString("RequestedBy"), "", "",
                        b.getString("RequestDate"),
                        b.getString("Status")));
            }
        } catch (Exception e) {
            Log.e("StationeryRequest", "JSONArray error");
        }
        return (list);
    }

}

