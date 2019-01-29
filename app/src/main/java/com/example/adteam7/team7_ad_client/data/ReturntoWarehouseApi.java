package com.example.adteam7.team7_ad_client.data;

import android.util.Log;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.network.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

    public class ReturntoWarehouseApi extends HashMap<String, String> {


        static String host = "172.17.101.59";
        static String baseURL;

        static {
            baseURL = String.format("http://%s/team7ad/api", host);
        }

        public ReturntoWarehouseApi(String RequestId, String ItemId, String Description, int Quantity, String DepartmentName, String Location)
        {

            put("RequestId", RequestId);
            put("ItemId", ItemId);
            put("Description", Description);
            put("Quantity", String.valueOf(Quantity));
            put("DepartmentName", DepartmentName);
            put("Location", Location);

        }

       public String getRequestId()
       {
           return get("RequestId");
       }


       public String getDepartmentName(){
            return get("DepartmentName");
       }

        public static List<ReturntoWarehouseApi> GetItemList() {
            String url = String.format("%s/returntowarehouse/getitemlist", baseURL);
            List<ReturntoWarehouseApi> listreturn = new ArrayList<>();
            try {
                JSONArray a = JSONParser.getJSONArrayFromUrl(url);
                for (int i = 0; i < a.length(); i++) {
                    JSONObject b = a.getJSONObject(i);
                    listreturn.add(new ReturntoWarehouseApi(
                            b.getString("RequestId"),
                            b.getString("ItemId"),
                            b.getString("Description"),
                            b.getInt("Quantity"),
                            b.getString("DepartmentName"),
                            b.getString("location")));
                }
            } catch (Exception e) {
                Log.e("ReturntoWarehouse", "JSONArray error");
            }
            return (listreturn);
        }


        public static void ReturnedFunction(ReturntoWarehouseApi rtw, int btn) {
            JSONObject jpo = new JSONObject();

            try {
                jpo.put("RequestId", rtw.get("RequestId"));
                jpo.put("ItemId",rtw.get("ItemId"));
                jpo.put("Description",rtw.get("Description"));
                jpo.put("Quantity",rtw.get("Quantity"));
                jpo.put("DepartmentName",rtw);
                jpo.put("Location", rtw.get("Location"));

            } catch (Exception e) {
                Log.e("ReturntoWarehouse", "Error");
            }

            if (btn == R.id.button)
                JSONParser.postStream1(baseURL + "/returntowarehouse/return", jpo.toString());


        }
        //endregion
    }




