package com.example.adteam7.team7_ad_client.network;

import android.util.Log;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.data.Employee;
import com.example.adteam7.team7_ad_client.data.ManageDepRep;
import com.example.adteam7.team7_ad_client.data.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
    SessionManager session = SessionManager.getInstance();


    static {
        //http://172.17.80.219/Team7API/Token
        baseURL = String.format("http://%s/team7ad/api", host);
        tokenURL = String.format("http://%s/team7ad/Token", host);
        imageURL = String.format("http://%s/myserviceEmp/photo", host);



    }

    @Override
    public String login(String usname,String pass) {
        try {


            String id = usname;//URLEncoder.encode(usname);
            String pw = pass;//URLEncoder.encode(pass);
            Log.e(TAG, "login: "+id+" and " +pw);
            String credential = String.format("username=%s&password=%s&grant_type=password", id, pw);
            String result = JSONParser.postStream(tokenURL, false, credential);
            JSONObject res = new JSONObject(result);
            if (res.has("access_token"))
                JSONParser.access_token = res.getString("access_token");
             String usid=res.getString("userName");
            Log.e(TAG, "login: "+res.getString("access_token") );
            return usid;
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

            //String url = String.format("%s/%s/%s", baseURL,"managedepartmentRep", id);

            String url=String.format("http://192.168.1.166/team7ad/api/managedepartmentRep/19fb3f0d-5859-4c63-979d-632213e67711");
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
    @Override
    public void delegateDepHeadSet() {

    }

    @Override
    public void assignDepRep() {

    }
}
