package com.example.adteam7.team7_ad_client.data;

import android.util.Log;

import com.example.adteam7.team7_ad_client.network.JSONParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

    public class ReturntoWarehouseApi {


        static String host = "172.17.81.182";
        static String baseURL;

        static {
            baseURL = String.format("http://%s/team7ad/api", host);
        }

        String RequestId,  ItemId,  Description;
        int Quantity;
        String Department,Location;


        public ReturntoWarehouseApi(String requestId, String itemId, String description, int quantity, String department, String location)
         {
            RequestId = requestId;
            ItemId = itemId;
            Description = description;
            Quantity = quantity;
            Department = department;
            Location = location;
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

        public String getDepartment() {
            return Department;
        }

        public void setDepartment(String department) {
            Department = department;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public static List<ReturntoWarehouseApi> GetItemList() {
            String url = String.format("%s/returntowarehouse/getitemlist", baseURL);
            List<ReturntoWarehouseApi> listreturn = new ArrayList<>();
            try {
                String a = JSONParser.getStream(url);

                Gson gson=new Gson();


                Type type=new TypeToken<ArrayList<ReturntoWarehouseApi>>(){}.getType();
                List<ReturntoWarehouseApi> list=gson.fromJson(a,type);

                return list;


            } catch (Exception e) {
                Log.e("ReturntoWarehouse", "JSONArray error");
            }
            return (listreturn);
        }


       /* public static void ReturnedFunction(ReturntoWarehouseApi rtw, int btn) {
            JSONObject jpo = new JSONObject();

            try {
                jpo.put("RequestId", rtw.get("RequestId"));
                jpo.put("ItemId",rtw.get("ItemId"));
                jpo.put("Description",rtw.get("Description"));
                jpo.put("Quantity",rtw.get("Quantity"));
                jpo.put("Department",rtw);
                jpo.put("Location", rtw.get("Location"));

            } catch (Exception e) {
                Log.e("ReturntoWarehouse", "Error");
            }

            if (btn == R.id.button){

            }
                //JSONParser.postStream(baseURL + "/returntowarehouse/return", jpo.toString());


        }*/
        //endregion
    }




