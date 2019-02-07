package com.example.adteam7.team7_ad_client.data;

import android.util.Log;

import com.example.adteam7.team7_ad_client.network.JSONParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReturntoWarehouseApi implements Serializable {


        //Region: Lynn Lynn Oo

        static String host = "172.17.148.100";
        static String baseURL;

        static {
            baseURL = String.format("http://%s/team7ad/api", host);
        }

    private String RequestId;
    private String DepartmentName;
    private List<ReturnItem> itemViewModels;

    public ReturntoWarehouseApi(String requestId, String departmentName, List<ReturnItem> detaillist) {
        RequestId = requestId;
        DepartmentName = departmentName;
        this.itemViewModels = detaillist;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public List<ReturnItem> getItemViewModels() {
        return itemViewModels;
    }

    public void setDetaillist(List<ReturnItem> detaillist) {
        this.itemViewModels = detaillist;
    }



    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        ReturntoWarehouseApi.host = host;
    }

    public static String getBaseURL() {
        return baseURL;
    }

    public static void setBaseURL(String baseURL) {
        ReturntoWarehouseApi.baseURL = baseURL;
    }

    public static List<ReturntoWarehouseApi> GetItemList() {
        String url = String.format("%s/returntowarehouse/getitemlist", baseURL);
        List<ReturntoWarehouseApi> listreturn = new ArrayList<>();
        try {
            String a = JSONParser.getStream(url);

            Gson gson = new Gson();


            Type type = new TypeToken<ArrayList<ReturntoWarehouseApi>>() {
            }.getType();
            List<ReturntoWarehouseApi> list = gson.fromJson(a, type);

            return list;


        } catch (Exception e) {
            Log.e("ReturntoWarehouse", "JSONArray error");
        }
        return (listreturn);
    }



        //endregion
    }




