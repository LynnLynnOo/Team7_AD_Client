package com.example.adteam7.team7_ad_client.network;

import android.util.Log;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.data.ManageDepRep;

import org.json.JSONObject;

import java.net.URLEncoder;

import static android.content.ContentValues.TAG;

/**
 * Created by dodo
 **/
public class APIDataAgentImpl implements APIDataAgent {

  // static String host = "localhost";
   static String host = "192.168.1.166";
   // http://localhost/Team7API/Token
    static String baseURL;
    static String imageURL;
    static String tokenURL;
    //http://172.17.4.197/team7ad/Token

    static {
        //http://172.17.80.219/Team7API/Token
        baseURL = String.format("http://%s/Team7API/api/Employee", host);
        tokenURL = String.format("http://%s/team7ad/Token", host);
        imageURL = String.format("http://%s/myserviceEmp/photo", host);
    }

    @Override
    public boolean login(String usname,String pass) {
        try {
            String id = usname;//URLEncoder.encode(usname);
            String pw = pass;//URLEncoder.encode(pass);
            Log.e(TAG, "login: "+id+" and " +pw);
            String credential = String.format("username=%s&password=%s&grant_type=password", id, pw);
            String result = JSONParser.postStream(tokenURL, false, credential);
            JSONObject res = new JSONObject(result);
            if (res.has("access_token"))
                JSONParser.access_token = res.getString("access_token");
            Log.e(TAG, "login: "+res.getString("access_token") );
            return true;
        } catch (Exception e) {
            JSONParser.access_token = "";
            Log.e("Login", e.toString());
            return false;
        }
    }

    @Override
    public void getEmpList() {

    }

    @Override
    public ManageDepRep delegateDepHeadGet() {


        return null;
    }
    @Override
    public void delegateDepHeadSet() {

    }

    @Override
    public void assignDepRep() {

    }
}
