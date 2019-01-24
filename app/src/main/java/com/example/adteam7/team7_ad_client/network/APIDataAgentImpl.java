package com.example.adteam7.team7_ad_client.network;

import android.util.Log;

import com.example.adteam7.team7_ad_client.data.AdjustmentInfo;
import com.example.adteam7.team7_ad_client.data.AdjustmentItem;
import com.example.adteam7.team7_ad_client.data.DelegateDepHeadApiModel;
import com.example.adteam7.team7_ad_client.data.Employee;
import com.example.adteam7.team7_ad_client.data.ManageDepRep;
import com.example.adteam7.team7_ad_client.data.SessionManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by dodo
 **/
public class APIDataAgentImpl implements APIDataAgent {

  // static String host = "localhost";
  static String host = "192.168.1.100";
   // http://localhost/Team7API/Token
    static String baseURL;
    static String imageURL;
    static String tokenURL;
    //http://172.17.4.197/team7ad/Token
    SessionManager session = SessionManager.getInstance();


    static {
        //http://172.17.80.219/Team7API/Token
        baseURL = String.format("http://%s/team7ad/api/", host);
        tokenURL = String.format("http://%s/team7ad/Token", host);
        imageURL = String.format("http://%s/myserviceEmp/photo", host);
    }


    //region Kay Thi Swe Tun
    @Override
    public String login(String usname, String pass) {
        try {
            String id = usname;//URLEncoder.encode(usname);
            String pw = pass;//URLEncoder.encode(pass);
            Log.e(TAG, "login: "+id+" and " +pw);
            String credential = String.format("username=%s&password=%s&grant_type=password", id, pw);
            String result = JSONParser.postStream(tokenURL, false, credential);
            JSONObject res = new JSONObject(result);
            if (res.has("access_token"))
                JSONParser.access_token = res.getString("access_token");
            String userId = res.getString("userName");
            Log.e(TAG, "login: "+res.getString("access_token") );
            return userId;
        } catch (Exception e) {
            JSONParser.access_token = "";
            Log.e("Login", e.toString());
            return "fail";
        }
    }



    @Override
    public ManageDepRep delegateDepHeadGet() {
        try {
            String id = session.getUserid();

            String url = String.format("%s/%s/%s", baseURL,"managedepartmentRep", id);

            //String url=String.format("http://192.168.1.166/team7ad/api/managedepartmentRep/19fb3f0d-5859-4c63-979d-632213e67711");
            JSONParser.access_token=session.getToken();
          //  String res = JSONParser.getStream(url);
            JSONObject a=JSONParser.getJSONFromUrl(url);
            ManageDepRep rep=new ManageDepRep();

            rep.setDepartmentId(a.getString("DepartmentId"));
            rep.setDepartmentname(a.getString("DepartmentName"));
            rep.setDepartmentRepName(a.getString("DepartmentRepName"));
            rep.setDepartmentRepId(a.getString("DepartmentRepId"));


           String url2 = String.format("%s/%s/%s", baseURL,"managedepartmentEmp", id);
            JSONArray arr=JSONParser.getJSONArrayFromUrl(url2);
            List<Employee> list=new ArrayList<>();
            try {
                for (int i =0; i<arr.length(); i++) {
                    JSONObject b = arr.getJSONObject(i);
                    list.add(new Employee(b.getString("EName"),
                            b.getString("Empid"),b.getString("Email"), b.getString("phone")));
                }
            } catch (Exception e) {
                Log.e("Employee", "JSONArray error");
            }

          rep.setEmployees(list);
           // Log.e(TAG, "delegateDepHeadGet: Rep Name"+ deprep);
            return rep;

        } catch (Exception e) {
           JSONParser.access_token = "";
            Log.e("Login", e.toString());
            return null;
        }

       // return null;
    }

    //endregion

    @Override
    public String delegateDepHeadSet(ManageDepRep dep) {
        JSONObject jemp = new JSONObject();
        try {
            jemp.put("DepartmentId", dep.getDepartmentId());
            jemp.put("DepartmentName", dep.getDepartmentname());
            jemp.put("DepartmentRepName", dep.getDepartmentRepName());
            jemp.put("DepartmentRepId", dep.getDepartmentRepId());
        } catch (Exception e) {
        }

        String rr = JSONParser.postStream(baseURL + "/managedepartmentEmp", true, jemp.toString());
        Log.e(TAG, "delegateDepHeadSet: Show result" + rr);

        return rr;
    }

    @Override
    public void assignDepRep() {

    }

    @Override
    public DelegateDepHeadApiModel delegateActingDepHeadGet() {
        try {
            String id = session.getUserid();

            //http://192.168.1.100/team7ad/api/
            String url = String.format("%sdepartmenthead/getdepartmenthead/%s", baseURL, id);
            String result = JSONParser.getStream(url);
            Log.i("Json", result);
            Gson gson = new Gson();
            return gson.fromJson(result, DelegateDepHeadApiModel.class);

//            String deprep=depinfo.getString("DepartmentRepName");
//
//            Log.e(TAG, "delegateDepHeadGet: Rep Name"+ deprep);
//            return null;

        } catch (Exception e) {
            Log.e("Login", e.toString());
        }
        return null;
    }

    @Override
    public String delegateActingDepHeadSet(DelegateDepHeadApiModel delHeadPost) {
        String status = "Error at saving.";
        try {
            String id = session.getUserid();
            delHeadPost.setUserId(id);
            //http://192.168.1.100/team7ad/api/
            String url = String.format("%sdepartmenthead/setdepartmenthead/", baseURL);
            Log.i("Url", url);
            Gson gson = new Gson();
            String json = gson.toJson(delHeadPost);
            Log.i("Json", json);
            String result = JSONParser.postStream(url, true, json);

            Log.i("PostResult", result);
            if (result != null || result != "")
                status = "Successfully saved.";

        } catch (Exception e) {
            Log.e("JsonPost", e.toString());
        }
        return status;
    }


    //region Zan Tun Khine


    //region Approve Reject PO


    //endregion


    //endregion


    //region Cheng Zongpei
    public List<String> adjustmentGetCategories(){
        String url = "http://192.168.1.75/webapi/adjustment/categories";
        JSONArray array = JSONParser.getJSONArrayFromUrl(url);
        List<String> result = new ArrayList<String>();
        try{
            for(int i=0;i<array.length();i++){
                result.add(array.getString(i));
            }
        }
        catch (Exception e){
            Log.e("error","Json get category error");
        }
        return result;
    }

    public List<AdjustmentItem> adjustmentGetItem(String category){
        String url =String.format("http://192.168.1.75/webapi/adjustment/items/%s",category);
        JSONArray array = JSONParser.getJSONArrayFromUrl(url);
        List<AdjustmentItem> result = new ArrayList<AdjustmentItem>();
        try{
            for(int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                result.add(new AdjustmentItem(object.getString("itemId"),object.getString("category"),object.getString("description"),object.getString("unitOfMeasure"),object.getInt("quantityWareHouse"),object.getDouble("price")));
            }
        }catch (Exception e){
            Log.e("error","Json get item error");
        }
        return result;
    }

    public AdjustmentItem adjustmentGetInfo(String itemId){
        String url = String.format("http://192.168.1.75/webapi/adjustment/item/%s",itemId);
        JSONObject object = JSONParser.getJSONFromUrl(url);
        AdjustmentItem result;
        try {
            result = new AdjustmentItem(object.getString("itemId"),object.getString("category"),object.getString("description"),object.getString("unitOfMeasure"),object.getInt("quantityWareHouse"),object.getDouble("price"));
        }catch (Exception e){
            result = new AdjustmentItem(null,null,null,null,0,0);
            Log.e("error","Json get info error");
        }
        return result;
    }

    public String adjustmentSet(List<AdjustmentInfo> adjustment){

        JSONArray array = new JSONArray();
        try{
            for(AdjustmentInfo info : adjustment){
                JSONObject object = new JSONObject();
                object.put("itemId",info.itemId);
                object.put("quantity",info.quantity);
                array.put(object);
            }
        }catch(Exception e){
            Log.e("error","Json save error");
        }

        String result = JSONParser.postStream("http://192.168.1.75/webapi/adjustment/save", true, array.toString());
        return result;
    }

    //endregion
}
