package com.example.adteam7.team7_ad_client.network;

import android.util.Log;

import com.example.adteam7.team7_ad_client.data.DelegateDepHeadApiModel;
import com.example.adteam7.team7_ad_client.data.ManageDepRep;
import com.example.adteam7.team7_ad_client.data.SessionManager;
import com.google.gson.Gson;

import org.json.JSONObject;

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

    @Override
    public String login(String username, String password) {
        try {
            String id = username;//URLEncoder.encode(usname);
            String pw = password;//URLEncoder.encode(pass);
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
    public void getEmpList() {

    }

    @Override
    public ManageDepRep delegateDepHeadGet() {
        try {
            String id = session.getUserid();

            String url = String.format("%s%s", baseURL, id);
            JSONObject res = JSONParser.getJSONFromUrl(url);

            JSONObject depinfo=res.getJSONObject("depinfo");

            String deprep=depinfo.getString("DepartmentRepName");

            Log.e(TAG, "delegateDepHeadGet: Rep Name"+ deprep);
            return null;

        } catch (Exception e) {
            /*JSONParser.access_token = "";
            Log.e("Login", e.toString());
            return "fail";*/
        }

        return null;
    }
    @Override
    public void delegateDepHeadSet() {

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
}
