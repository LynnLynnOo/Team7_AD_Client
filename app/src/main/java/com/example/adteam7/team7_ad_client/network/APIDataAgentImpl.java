package com.example.adteam7.team7_ad_client.network;

import android.util.Log;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.AckDeliveryDetails;
import com.example.adteam7.team7_ad_client.data.AckDisbursement;
import com.example.adteam7.team7_ad_client.data.AdjustmentInfo;
import com.example.adteam7.team7_ad_client.data.AdjustmentItem;
import com.example.adteam7.team7_ad_client.data.DelegateDepHeadApiModel;
import com.example.adteam7.team7_ad_client.data.Disbursement;
import com.example.adteam7.team7_ad_client.data.DisbursementSationeryItem;
import com.example.adteam7.team7_ad_client.data.Employee;
import com.example.adteam7.team7_ad_client.data.ManageDepRep;
import com.example.adteam7.team7_ad_client.data.PendingPO;
import com.example.adteam7.team7_ad_client.data.PendingPODetails;
import com.example.adteam7.team7_ad_client.data.ReturnItem;
import com.example.adteam7.team7_ad_client.data.SessionManager;
import com.example.adteam7.team7_ad_client.data.SetRetrievalApiModel;
import com.example.adteam7.team7_ad_client.data.StationeryRequestApiModel;
import com.example.adteam7.team7_ad_client.data.StationeryRetrievalApiModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by dodo
 **/
public class APIDataAgentImpl implements APIDataAgent {

  // static String host = "localhost";
   static String host = "192.168.1.71";
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
    public String login(String usname,String pass) {
        try {
            String id = usname;//URLEncoder.encode(usname);
            String pw = pass;//URLEncoder.encode(pass);
            Log.e(TAG, "login: "+id+" and " +pw);
            String credential = String.format("username=%s&password=%s&grant_type=password", id, pw);
            String result = JSONParser.postStream(tokenURL, false, credential);
            JSONObject res = new JSONObject(result);
            String userId="";
            if (res.has("access_token")) {
                JSONParser.access_token = res.getString("access_token");

                userId= res.getString("userName");
                Log.e(TAG, "login: " + res.getString("access_token"));
                SessionManager sessionManager=SessionManager.getInstance();
                if(res.has("roleName1")) {
                    sessionManager.setUserRole(res.getString("roleName0"), res.getString("roleName1"));
                }else{
                    sessionManager.setUserRole(res.getString("roleName0"), "");
                }
            }
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

    @Override
    public String assignDepRep(ManageDepRep dep) {
        JSONObject jemp = new JSONObject();
        try {
            jemp.put("DepartmentId", dep.getDepartmentId());
            jemp.put("DepartmentName", dep.getDepartmentname());
            jemp.put("DepartmentRepName", dep.getDepartmentRepName());
            jemp.put("DepartmentRepId", dep.getDepartmentRepId());
        } catch (Exception e) {
        }

        String rr=JSONParser.postStream(baseURL+"/managedepartmentEmp",true,jemp.toString());
        Log.e(TAG, "delegateDepHeadSet: Show result"+rr );

        return rr;
    }



   /* @Override
    public String delegateDepHeadSet(ManageDepRep dep) {

    }*/

    @Override
    public List<Disbursement> getDisbbyDept() {
        try {
            String url = String.format("%sclerk/disbnolist", baseURL);
            String result=JSONParser.getStream(url);
            // Log.i("Json", result);

            //String result=[{DisbursementNo":"DACCT00001","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-01-02T00:00:00","Status":"Acknowledged","OTP":"A001"},{"DisbursementNo":"DACCT00010","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-02-13T00:00:00","Status":"Acknowledged","OTP":"A010"},{"DisbursementNo":"DACCT00018","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-03-20T00:00:00","Status":"Acknowledged","OTP":"A018"},{"DisbursementNo":"DACCT00028","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-04-24T00:00:00","Status":"Acknowledged","OTP":"A028"},{"DisbursementNo":"DACCT00037","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-06-05T00:00:00","Status":"Acknowledged","OTP":"A037"},{"DisbursementNo":"DACCT00054","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-08-14T00:00:00","Status":"Acknowledged","OTP":"A054"},{"DisbursementNo":"DACCT00064","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-09-25T00:00:00","Status":"Acknowledged","OTP":"A064"},{"DisbursementNo":"DACCT00073","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-10-30T00:00:00","Status":"Acknowledged","OTP":"A073"},{"DisbursementNo":"DACCT00082","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-12-04T00:00:00","Status":"Acknowledged","OTP":"A082"},{"DisbursementNo":"DACCT00091","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-01-08T00:00:00","Status":"Acknowledged","OTP":"A091"},{"DisbursementNo":"DACCT00100","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-02-19T00:00:00","Status":"Acknowledged","OTP":"A100"},{"DisbursementNo":"DACCT00109","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-03-26T00:00:00","Status":"Acknowledged","OTP":"A109"},{"DisbursementNo":"DACCT00118","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-04-30T00:00:00","Status":"Acknowledged","OTP":"A118"},{"DisbursementNo":"DACCT00126","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-06-04T00:00:00","Status":"Acknowledged","OTP":"A126"},{"DisbursementNo":"DACCT00136","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-07-16T00:00:00","Status":"Acknowledged","OTP":"A136"},{"DisbursementNo":"DACCT00153","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-09-24T00:00:00","Status":"Acknowledged","OTP":"A153"},{"DisbursementNo":"DACCT00162","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-10-29T00:00:00","Status":"Acknowledged","OTP":"A162"},{"DisbursementNo":"DACCT00172","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-12-10T00:00:00","Status":"Acknowledged","OTP":"A172"},{"DisbursementNo":"DBUSI00002","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-01-09T00:00:00","Status":"Acknowledged","OTP":"A002"},{"DisbursementNo":"DBUSI00011","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-02-13T00:00:00","Status":"Acknowledged","OTP":"A011"},{"DisbursementNo":"DBUSI00019","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-03-20T00:00:00","Status":"Acknowledged","OTP":"A019"},{"DisbursementNo":"DBUSI00029","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-05-01T00:00:00","Status":"Acknowledged","OTP":"A029"},{"DisbursementNo":"DBUSI00038","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-06-05T00:00:00","Status":"Acknowledged","OTP":"A038"},{"DisbursementNo":"DBUSI00046","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-07-10T00:00:00","Status":"Acknowledged","OTP":"A046"},{"DisbursementNo":"DBUSI00055","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-08-14T00:00:00","Status":"Acknowledged","OTP":"A055"},{"DisbursementNo":"DBUSI00065","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-09-25T00:00:00","Status":"Acknowledged","OTP":"A065"},{"DisbursementNo":"DBUSI00074","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-10-30T00:00:00","Status":"Acknowledged","OTP":"A074"},{"DisbursementNo":"DBUSI00083","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-12-04T00:00:00","Status":"Acknowledged","OTP":"A083"},{"DisbursementNo":"DBUSI00092","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-01-15T00:00:00","Status":"Acknowledged","OTP":"A092"},{"DisbursementNo":"DBUSI00101","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-02-19T00:00:00","Status":"Acknowledged","OTP":"A101"},{"DisbursementNo":"DBUSI00110","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-03-26T00:00:00","Status":"Acknowledged","OTP":"A110"},{"DisbursementNo":"DBUSI00119","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-05-07T00:00:00","Status":"Acknowledged","OTP":"A119"},{"DisbursementNo":"DBUSI00127","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-06-11T00:00:00","Status":"Acknowledged","OTP":"A127"},{"DisbursementNo":"DBUSI00137","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-07-16T00:00:00","Status":"Acknowledged","OTP":"A137"},{"DisbursementNo":"DBUSI00145","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-08-27T00:00:00","Status":"Acknowledged","OTP":"A145"},{"DisbursementNo":"DBUSI00154","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-10-01T00:00:00","Status":"Acknowledged","OTP":"A154"},{"DisbursementNo":"DBUSI00163","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-11-05T00:00:00","Status":"Acknowledged","OTP":"A163"},{"DisbursementNo":"DBUSI00173","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-12-17T00:00:00","Status":"Acknowledged","OTP":"A173"},{"DisbursementNo":"DCOMS00003","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-01-09T00:00:00","Status":"Acknowledged","OTP":"A003"},{"DisbursementNo":"DCOMS00012","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-02-20T00:00:00","Status":"Acknowledged","OTP":"A012"},{"DisbursementNo":"DCOMS00020","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-03-27T00:00:00","Status":"Acknowledged","OTP":"A020"},{"DisbursementNo":"DCOMS00030","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-05-08T00:00:00","Status":"Acknowledged","OTP":"A030"},{"DisbursementNo":"DCOMS00039","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-06-12T00:00:00","Status":"Acknowledged","OTP":"A039"},{"DisbursementNo":"DCOMS00047","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-07-17T00:00:00","Status":"Acknowledged","OTP":"A047"},{"DisbursementNo":"DCOMS00056","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-08-21T00:00:00","Status":"Acknowledged","OTP":"A056"},{"DisbursementNo":"DCOMS00066","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-10-02T00:00:00","Status":"Acknowledged","OTP":"A066"},{"DisbursementNo":"DCOMS00075","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-11-06T00:00:00","Status":"Acknowledged","OTP":"A075"},{"DisbursementNo":"DCOMS00084","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-12-11T00:00:00","Status":"Acknowledged","OTP":"A084"},{"DisbursementNo":"DCOMS00093","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-01-15T00:00:00","Status":"Acknowledged","OTP":"A093"},{"DisbursementNo":"DCOMS00102","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-02-26T00:00:00","Status":"Acknowledged","OTP":"A102"},{"DisbursementNo":"DCOMS00111","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-04-02T00:00:00","Status":"Acknowledged","OTP":"A111"},{"DisbursementNo":"DCOMS00120","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-05-07T00:00:00","Status":"Acknowledged","OTP":"A120"},{"DisbursementNo":"DCOMS00128","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-06-11T00:00:00","Status":"Acknowledged","OTP":"A128"},{"DisbursementNo":"DCOMS00138","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-07-23T00:00:00","Status":"Acknowledged","OTP":"A138"},{"DisbursementNo":"DCOMS00146","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-08-27T00:00:00","Status":"Acknowledged","OTP":"A146"},{"DisbursementNo":"DCOMS00155","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-10-01T00:00:00","Status":"Acknowledged","OTP":"A155"},{"DisbursementNo":"DCOMS00164","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-11-05T00:00:00","Status":"Acknowledged","OTP":"A164"},{"DisbursementNo":"DCOMS00174","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-12-17T00:00:00","Status":"Acknowledged","OTP":"A174"},{"DisbursementNo":"DECON00004","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-01-16T00:00:00","Status":"Acknowledged","OTP":"A004"},{"DisbursementNo":"DECON00013","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-02-20T00:00:00","Status":"Acknowledged","OTP":"A013"},{"DisbursementNo":"DECON00021","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-03-27T00:00:00","Status":"Acknowledged","OTP":"A021"},{"DisbursementNo":"DECON00031","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-05-08T00:00:00","Status":"Acknowledged","OTP":"A031"},{"DisbursementNo":"DECON00040","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-06-12T00:00:00","Status":"Acknowledged","OTP":"A040"},{"DisbursementNo":"DECON00048","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-07-17T00:00:00","Status":"Acknowledged","OTP":"A048"},{"DisbursementNo":"DECON00057","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-08-28T00:00:00","Status":"Acknowledged","OTP":"A057"},{"DisbursementNo":"DECON00067","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-10-02T00:00:00","Status":"Acknowledged","OTP":"A067"},{"DisbursementNo":"DECON00076","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-11-06T00:00:00","Status":"Acknowledged","OTP":"A076"},{"DisbursementNo":"DECON00085","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-12-18T00:00:00","Status":"Acknowledged","OTP":"A085"},{"DisbursementNo":"DECON00094","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-01-22T00:00:00","Status":"Acknowledged","OTP":"A094"},{"DisbursementNo":"DECON00103","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-02-26T00:00:00","Status":"Acknowledged","OTP":"A103"},{"DisbursementNo":"DECON00112","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-04-09T00:00:00","Status":"Acknowledged","OTP":"A112"},{"DisbursementNo":"DECON00121","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-05-14T00:00:00","Status":"Acknowledged","OTP":"A121"},{"DisbursementNo":"DECON00129","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-06-18T00:00:00","Status":"Acknowledged","OTP":"A129"},{"DisbursementNo":"DECON00139","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-07-30T00:00:00","Status":"Acknowledged","OTP":"A139"},{"DisbursementNo":"DECON00147","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-09-03T00:00:00","Status":"In Transit","OTP":"A147"},{"DisbursementNo":"DECON00156","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-10-08T00:00:00","Status":"Acknowledged","OTP":"A156"},{"DisbursementNo":"DECON00165","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-11-12T00:00:00","Status":"In Transit","OTP":"A165"},{"DisbursementNo":"DECON00175","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-12-24T00:00:00","Status":"Acknowledged","OTP":"A175"},{"DisbursementNo":"DENGL00005","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-01-23T00:00:00","Status":"Acknowledged","OTP":"A005"},{"DisbursementNo":"DENGL00022","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-04-03T00:00:00","Status":"Acknowledged","OTP":"A022"},{"DisbursementNo":"DENGL00032","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-05-15T00:00:00","Status":"Acknowledged","OTP":"A032"},{"DisbursementNo":"DENGL00041","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-06-19T00:00:00","Status":"Acknowledged","OTP":"A041"},{"DisbursementNo":"DENGL00049","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-07-24T00:00:00","Status":"Acknowledged","OTP":"A049"},{"DisbursementNo":"DENGL00058","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-08-28T00:00:00","Status":"Acknowledged","OTP":"A058"},{"DisbursementNo":"DENGL00068","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-10-09T00:00:00","Status":"Acknowledged","OTP":"A068"},{"DisbursementNo":"DENGL00077","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-11-13T00:00:00","Status":"Acknowledged","OTP":"A077"},{"DisbursementNo":"DENGL00086","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-12-18T00:00:00","Status":"Acknowledged","OTP":"A086"},{"DisbursementNo":"DENGL00095","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-01-22T00:00:00","Status":"Acknowledged","OTP":"A095"},{"DisbursementNo":"DENGL00104","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-03-05T00:00:00","Status":"Acknowledged","OTP":"A104"},{"DisbursementNo":"DENGL00113","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-04-09T00:00:00","Status":"Acknowledged","OTP":"A113"},{"DisbursementNo":"DENGL00122","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-05-14T00:00:00","Status":"Acknowledged","OTP":"A122"},{"DisbursementNo":"DENGL00130","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-06-18T00:00:00","Status":"Acknowledged","OTP":"A130"},{"DisbursementNo":"DENGL00140","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-07-30T00:00:00","Status":"Acknowledged","OTP":"A140"},{"DisbursementNo":"DENGL00148","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-09-03T00:00:00","Status":"In Transit","OTP":"A148"},{"DisbursementNo":"DENGL00157","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-10-08T00:00:00","Status":"Acknowledged","OTP":"A157"},{"DisbursementNo":"DENGL00166","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-11-19T00:00:00","Status":"In Transit","OTP":"A166"},{"DisbursementNo":"DENGL00176","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-12-24T00:00:00","Status":"Acknowledged","OTP":"A176"},{"DisbursementNo":"DFINC00006","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-01-23T00:00:00","Status":"Acknowledged","OTP":"A006"},{"DisbursementNo":"DFINC00014","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-02-27T00:00:00","Status":"Acknowledged","OTP":"A014"},{"DisbursementNo":"DFINC00023","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-04-10T00:00:00","Status":"Acknowledged","OTP":"A023"},{"DisbursementNo":"DFINC00033","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-05-15T00:00:00","Status":"Acknowledged","OTP":"A033"},{"DisbursementNo":"DFINC00042","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-06-19T00:00:00","Status":"Acknowledged","OTP":"A042"},{"DisbursementNo":"DFINC00050","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-07-31T00:00:00","Status":"Acknowledged","OTP":"A050"},{"DisbursementNo":"DFINC00059","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-09-04T00:00:00","Status":"Acknowledged","OTP":"A059"},{"DisbursementNo":"DFINC00069","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-10-09T00:00:00","Status":"Acknowledged","OTP":"A069"},{"DisbursementNo":"DFINC00078","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-11-20T00:00:00","Status":"Acknowledged","OTP":"A078"},{"DisbursementNo":"DFINC00087","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-12-25T00:00:00","Status":"Acknowledged","OTP":"A087"},{"DisbursementNo":"DFINC00105","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-03-12T00:00:00","Status":"Acknowledged","OTP":"A105"},{"DisbursementNo":"DFINC00114","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-04-16T00:00:00","Status":"Acknowledged","OTP":"A114"},{"DisbursementNo":"DFINC00123","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-05-21T00:00:00","Status":"Acknowledged","OTP":"A123"},{"DisbursementNo":"DFINC00131","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-06-25T00:00:00","Status":"Acknowledged","OTP":"A131"},{"DisbursementNo":"DFINC00141","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-08-06T00:00:00","Status":"Acknowledged","OTP":"A141"},{"DisbursementNo":"DFINC00149","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-09-10T00:00:00","Status":"Acknowledged","OTP":"A149"},{"DisbursementNo":"DFINC00158","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-10-15T00:00:00","Status":"Acknowledged","OTP":"A158"},{"DisbursementNo":"DFINC00167","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-11-19T00:00:00","Status":"In Transit","OTP":"A167"},{"DisbursementNo":"DFINC00177","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-12-31T00:00:00","Status":"Acknowledged","OTP":"A177"},{"DisbursementNo":"DHUMA00007","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-01-30T00:00:00","Status":"Acknowledged","OTP":"A007"},{"DisbursementNo":"DHUMA00015","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-03-06T00:00:00","Status":"Acknowledged","OTP":"A015"},{"DisbursementNo":"DHUMA00024","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-04-10T00:00:00","Status":"Acknowledged","OTP":"A024"},{"DisbursementNo":"DHUMA00034","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-05-22T00:00:00","Status":"Acknowledged","OTP":"A034"},{"DisbursementNo":"DHUMA00043","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-06-26T00:00:00","Status":"Acknowledged","OTP":"A043"},{"DisbursementNo":"DHUMA00051","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-07-31T00:00:00","Status":"Acknowledged","OTP":"A051"},{"DisbursementNo":"DHUMA00060","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-09-04T00:00:00","Status":"Acknowledged","OTP":"A060"},{"DisbursementNo":"DHUMA00070","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-10-16T00:00:00","Status":"Acknowledged","OTP":"A070"},{"DisbursementNo":"DHUMA00079","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-11-20T00:00:00","Status":"Acknowledged","OTP":"A079"},{"DisbursementNo":"DHUMA00088","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-12-25T00:00:00","Status":"Acknowledged","OTP":"A088"},{"DisbursementNo":"DHUMA00096","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-01-29T00:00:00","Status":"Acknowledged","OTP":"A096"},{"DisbursementNo":"DHUMA00106","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-03-12T00:00:00","Status":"Acknowledged","OTP":"A106"},{"DisbursementNo":"DHUMA00115","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-04-16T00:00:00","Status":"Acknowledged","OTP":"A115"},{"DisbursementNo":"DHUMA00124","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-05-21T00:00:00","Status":"Acknowledged","OTP":"A124"},{"DisbursementNo":"DHUMA00132","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-07-02T00:00:00","Status":"Acknowledged","OTP":"A132"},{"DisbursementNo":"DHUMA00142","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-08-06T00:00:00","Status":"Acknowledged","OTP":"A142"},{"DisbursementNo":"DHUMA00150","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-09-10T00:00:00","Status":"In Transit","OTP":"A150"},{"DisbursementNo":"DHUMA00159","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-10-22T00:00:00","Status":"Acknowledged","OTP":"A159"},{"DisbursementNo":"DHUMA00168","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-11-26T00:00:00","Status":"In Transit","OTP":"A168"},{"DisbursementNo":"DINTA00008","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-01-30T00:00:00","Status":"Acknowledged","OTP":"A008"},{"DisbursementNo":"DINTA00016","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-03-13T00:00:00","Status":"Acknowledged","OTP":"A016"},{"DisbursementNo":"DINTA00025","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-04-17T00:00:00","Status":"Acknowledged","OTP":"A025"},{"DisbursementNo":"DINTA00035","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-05-22T00:00:00","Status":"Acknowledged","OTP":"A035"},{"DisbursementNo":"DINTA00044","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-07-03T00:00:00","Status":"Acknowledged","OTP":"A044"},{"DisbursementNo":"DINTA00052","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-08-07T00:00:00","Status":"Acknowledged","OTP":"A052"},{"DisbursementNo":"DINTA00061","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-09-11T00:00:00","Status":"Acknowledged","OTP":"A061"},{"DisbursementNo":"DINTA00071","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-10-23T00:00:00","Status":"Acknowledged","OTP":"A071"},{"DisbursementNo":"DINTA00080","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-11-27T00:00:00","Status":"Acknowledged","OTP":"A080"},{"DisbursementNo":"DINTA00089","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-01-01T00:00:00","Status":"Acknowledged","OTP":"A089"},{"DisbursementNo":"DINTA00097","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-02-05T00:00:00","Status":"Acknowledged","OTP":"A097"},{"DisbursementNo":"DINTA00107","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-03-19T00:00:00","Status":"Acknowledged","OTP":"A107"},{"DisbursementNo":"DINTA00116","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-04-23T00:00:00","Status":"Acknowledged","OTP":"A116"},{"DisbursementNo":"DINTA00133","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-07-02T00:00:00","Status":"Acknowledged","OTP":"A133"},{"DisbursementNo":"DINTA00143","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-08-13T00:00:00","Status":"Acknowledged","OTP":"A143"},{"DisbursementNo":"DINTA00151","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-09-17T00:00:00","Status":"Acknowledged","OTP":"A151"},{"DisbursementNo":"DINTA00160","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-10-22T00:00:00","Status":"Acknowledged","OTP":"A160"},{"DisbursementNo":"DINTA00169","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-11-26T00:00:00","Status":"Acknowledged","OTP":"A169"},{"DisbursementNo":"DSOSC00009","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-02-06T00:00:00","Status":"Acknowledged","OTP":"A009"},{"DisbursementNo":"DSOSC00017","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-03-13T00:00:00","Status":"Acknowledged","OTP":"A017"},{"DisbursementNo":"DSOSC00026","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-04-17T00:00:00","Status":"Acknowledged","OTP":"A026"},{"DisbursementNo":"DSOSC00036","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-05-29T00:00:00","Status":"Acknowledged","OTP":"A036"},{"DisbursementNo":"DSOSC00045","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-07-03T00:00:00","Status":"Acknowledged","OTP":"A045"},{"DisbursementNo":"DSOSC00053","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-08-07T00:00:00","Status":"Acknowledged","OTP":"A053"},{"DisbursementNo":"DSOSC00062","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-09-11T00:00:00","Status":"Acknowledged","OTP":"A062"},{"DisbursementNo":"DSOSC00072","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-10-23T00:00:00","Status":"Acknowledged","OTP":"A072"},{"DisbursementNo":"DSOSC00081","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-11-27T00:00:00","Status":"Acknowledged","OTP":"A081"},{"DisbursementNo":"DSOSC00090","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-01-01T00:00:00","Status":"Acknowledged","OTP":"A090"},{"DisbursementNo":"DSOSC00098","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-02-12T00:00:00","Status":"Acknowledged","OTP":"A098"},{"DisbursementNo":"DSOSC00108","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-03-19T00:00:00","Status":"Acknowledged","OTP":"A108"},{"DisbursementNo":"DSOSC00117","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-04-23T00:00:00","Status":"Acknowledged","OTP":"A117"},{"DisbursementNo":"DSOSC00125","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-06-04T00:00:00","Status":"Acknowledged","OTP":"A125"},{"DisbursementNo":"DSOSC00134","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-07-09T00:00:00","Status":"Acknowledged","OTP":"A134"},{"DisbursementNo":"DSOSC00144","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-08-13T00:00:00","Status":"Acknowledged","OTP":"A144"},{"DisbursementNo":"DSOSC00152","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-09-24T00:00:00","Status":"In Transit","OTP":"A152"},{"DisbursementNo":"DSOSC00161","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-10-29T00:00:00","Status":"Acknowledged","OTP":"A161"},{"DisbursementNo":"DSOSC00170","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-12-03T00:00:00","Status":"Acknowledged","OTP":"A170"},{"DisbursementNo":"DSTAT00027","DepartmentId":"STAT","DepartmentName":"StationeryStore","AcknowledgedBy":"5042e8ca-ef83-4458-a78c-07e4f6ba0d1d","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-04-24T00:00:00","Status":"Acknowledged","OTP":"A027"},{"DisbursementNo":"DSTAT00063","DepartmentId":"STAT","DepartmentName":"StationeryStore","AcknowledgedBy":"5042e8ca-ef83-4458-a78c-07e4f6ba0d1d","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-09-18T00:00:00","Status":"Acknowledged","OTP":"A063"},{"DisbursementNo":"DSTAT00099","DepartmentId":"STAT","DepartmentName":"StationeryStore","AcknowledgedBy":"5042e8ca-ef83-4458-a78c-07e4f6ba0d1d","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-02-12T00:00:00","Status":"Acknowledged","OTP":"A099"},{"DisbursementNo":"DSTAT00135","DepartmentId":"STAT","DepartmentName":"StationeryStore","AcknowledgedBy":"5042e8ca-ef83-4458-a78c-07e4f6ba0d1d","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-07-09T00:00:00","Status":"Acknowledged","OTP":"A135"},{"DisbursementNo":"DSTAT00171","DepartmentId":"STAT","DepartmentName":"StationeryStore","AcknowledgedBy":"5042e8ca-ef83-4458-a78c-07e4f6ba0d1d","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-12-03T00:00:00","Status":"Acknowledged","OTP":"A171"}]';

            Gson gson =new Gson();

            Type type=new TypeToken<ArrayList<Disbursement>>(){}.getType();
            List<Disbursement> disbursementList=gson.fromJson(result,type);

            return disbursementList;

        } catch (Exception e) {
            Log.e("Disbursement ", e.toString());
        }
        return null;
    }

    @Override
    public List<DisbursementSationeryItem> getDisbDetail(String disbno) {
        try {
            String url = String.format("%sclerk/disbnolist/%s", baseURL,disbno);
            String result=JSONParser.getStream(url);

            //String result=[{DisbursementNo":"DACCT00001","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-01-02T00:00:00","Status":"Acknowledged","OTP":"A001"},{"DisbursementNo":"DACCT00010","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-02-13T00:00:00","Status":"Acknowledged","OTP":"A010"},{"DisbursementNo":"DACCT00018","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-03-20T00:00:00","Status":"Acknowledged","OTP":"A018"},{"DisbursementNo":"DACCT00028","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-04-24T00:00:00","Status":"Acknowledged","OTP":"A028"},{"DisbursementNo":"DACCT00037","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-06-05T00:00:00","Status":"Acknowledged","OTP":"A037"},{"DisbursementNo":"DACCT00054","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-08-14T00:00:00","Status":"Acknowledged","OTP":"A054"},{"DisbursementNo":"DACCT00064","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-09-25T00:00:00","Status":"Acknowledged","OTP":"A064"},{"DisbursementNo":"DACCT00073","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-10-30T00:00:00","Status":"Acknowledged","OTP":"A073"},{"DisbursementNo":"DACCT00082","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-12-04T00:00:00","Status":"Acknowledged","OTP":"A082"},{"DisbursementNo":"DACCT00091","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-01-08T00:00:00","Status":"Acknowledged","OTP":"A091"},{"DisbursementNo":"DACCT00100","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-02-19T00:00:00","Status":"Acknowledged","OTP":"A100"},{"DisbursementNo":"DACCT00109","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-03-26T00:00:00","Status":"Acknowledged","OTP":"A109"},{"DisbursementNo":"DACCT00118","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-04-30T00:00:00","Status":"Acknowledged","OTP":"A118"},{"DisbursementNo":"DACCT00126","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-06-04T00:00:00","Status":"Acknowledged","OTP":"A126"},{"DisbursementNo":"DACCT00136","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-07-16T00:00:00","Status":"Acknowledged","OTP":"A136"},{"DisbursementNo":"DACCT00153","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-09-24T00:00:00","Status":"Acknowledged","OTP":"A153"},{"DisbursementNo":"DACCT00162","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-10-29T00:00:00","Status":"Acknowledged","OTP":"A162"},{"DisbursementNo":"DACCT00172","DepartmentId":"ACCT","DepartmentName":"Accountancy","AcknowledgedBy":"c2d0092b-764d-4e95-a50b-8962eedb8701","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-12-10T00:00:00","Status":"Acknowledged","OTP":"A172"},{"DisbursementNo":"DBUSI00002","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-01-09T00:00:00","Status":"Acknowledged","OTP":"A002"},{"DisbursementNo":"DBUSI00011","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-02-13T00:00:00","Status":"Acknowledged","OTP":"A011"},{"DisbursementNo":"DBUSI00019","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-03-20T00:00:00","Status":"Acknowledged","OTP":"A019"},{"DisbursementNo":"DBUSI00029","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-05-01T00:00:00","Status":"Acknowledged","OTP":"A029"},{"DisbursementNo":"DBUSI00038","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-06-05T00:00:00","Status":"Acknowledged","OTP":"A038"},{"DisbursementNo":"DBUSI00046","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-07-10T00:00:00","Status":"Acknowledged","OTP":"A046"},{"DisbursementNo":"DBUSI00055","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-08-14T00:00:00","Status":"Acknowledged","OTP":"A055"},{"DisbursementNo":"DBUSI00065","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-09-25T00:00:00","Status":"Acknowledged","OTP":"A065"},{"DisbursementNo":"DBUSI00074","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-10-30T00:00:00","Status":"Acknowledged","OTP":"A074"},{"DisbursementNo":"DBUSI00083","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-12-04T00:00:00","Status":"Acknowledged","OTP":"A083"},{"DisbursementNo":"DBUSI00092","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-01-15T00:00:00","Status":"Acknowledged","OTP":"A092"},{"DisbursementNo":"DBUSI00101","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-02-19T00:00:00","Status":"Acknowledged","OTP":"A101"},{"DisbursementNo":"DBUSI00110","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-03-26T00:00:00","Status":"Acknowledged","OTP":"A110"},{"DisbursementNo":"DBUSI00119","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-05-07T00:00:00","Status":"Acknowledged","OTP":"A119"},{"DisbursementNo":"DBUSI00127","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-06-11T00:00:00","Status":"Acknowledged","OTP":"A127"},{"DisbursementNo":"DBUSI00137","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-07-16T00:00:00","Status":"Acknowledged","OTP":"A137"},{"DisbursementNo":"DBUSI00145","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-08-27T00:00:00","Status":"Acknowledged","OTP":"A145"},{"DisbursementNo":"DBUSI00154","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-10-01T00:00:00","Status":"Acknowledged","OTP":"A154"},{"DisbursementNo":"DBUSI00163","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-11-05T00:00:00","Status":"Acknowledged","OTP":"A163"},{"DisbursementNo":"DBUSI00173","DepartmentId":"BUSI","DepartmentName":"Business","AcknowledgedBy":"b8a75aa3-921e-43d4-9cce-5d23d16b3b5a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-12-17T00:00:00","Status":"Acknowledged","OTP":"A173"},{"DisbursementNo":"DCOMS00003","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-01-09T00:00:00","Status":"Acknowledged","OTP":"A003"},{"DisbursementNo":"DCOMS00012","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-02-20T00:00:00","Status":"Acknowledged","OTP":"A012"},{"DisbursementNo":"DCOMS00020","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-03-27T00:00:00","Status":"Acknowledged","OTP":"A020"},{"DisbursementNo":"DCOMS00030","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-05-08T00:00:00","Status":"Acknowledged","OTP":"A030"},{"DisbursementNo":"DCOMS00039","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-06-12T00:00:00","Status":"Acknowledged","OTP":"A039"},{"DisbursementNo":"DCOMS00047","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-07-17T00:00:00","Status":"Acknowledged","OTP":"A047"},{"DisbursementNo":"DCOMS00056","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-08-21T00:00:00","Status":"Acknowledged","OTP":"A056"},{"DisbursementNo":"DCOMS00066","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-10-02T00:00:00","Status":"Acknowledged","OTP":"A066"},{"DisbursementNo":"DCOMS00075","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-11-06T00:00:00","Status":"Acknowledged","OTP":"A075"},{"DisbursementNo":"DCOMS00084","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-12-11T00:00:00","Status":"Acknowledged","OTP":"A084"},{"DisbursementNo":"DCOMS00093","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-01-15T00:00:00","Status":"Acknowledged","OTP":"A093"},{"DisbursementNo":"DCOMS00102","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-02-26T00:00:00","Status":"Acknowledged","OTP":"A102"},{"DisbursementNo":"DCOMS00111","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-04-02T00:00:00","Status":"Acknowledged","OTP":"A111"},{"DisbursementNo":"DCOMS00120","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-05-07T00:00:00","Status":"Acknowledged","OTP":"A120"},{"DisbursementNo":"DCOMS00128","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-06-11T00:00:00","Status":"Acknowledged","OTP":"A128"},{"DisbursementNo":"DCOMS00138","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-07-23T00:00:00","Status":"Acknowledged","OTP":"A138"},{"DisbursementNo":"DCOMS00146","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-08-27T00:00:00","Status":"Acknowledged","OTP":"A146"},{"DisbursementNo":"DCOMS00155","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-10-01T00:00:00","Status":"Acknowledged","OTP":"A155"},{"DisbursementNo":"DCOMS00164","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-11-05T00:00:00","Status":"Acknowledged","OTP":"A164"},{"DisbursementNo":"DCOMS00174","DepartmentId":"COMS","DepartmentName":"Computer Science","AcknowledgedBy":"3c586171-51b2-484b-96d4-7ca640bc7476","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-12-17T00:00:00","Status":"Acknowledged","OTP":"A174"},{"DisbursementNo":"DECON00004","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-01-16T00:00:00","Status":"Acknowledged","OTP":"A004"},{"DisbursementNo":"DECON00013","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-02-20T00:00:00","Status":"Acknowledged","OTP":"A013"},{"DisbursementNo":"DECON00021","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-03-27T00:00:00","Status":"Acknowledged","OTP":"A021"},{"DisbursementNo":"DECON00031","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-05-08T00:00:00","Status":"Acknowledged","OTP":"A031"},{"DisbursementNo":"DECON00040","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-06-12T00:00:00","Status":"Acknowledged","OTP":"A040"},{"DisbursementNo":"DECON00048","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-07-17T00:00:00","Status":"Acknowledged","OTP":"A048"},{"DisbursementNo":"DECON00057","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-08-28T00:00:00","Status":"Acknowledged","OTP":"A057"},{"DisbursementNo":"DECON00067","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-10-02T00:00:00","Status":"Acknowledged","OTP":"A067"},{"DisbursementNo":"DECON00076","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-11-06T00:00:00","Status":"Acknowledged","OTP":"A076"},{"DisbursementNo":"DECON00085","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-12-18T00:00:00","Status":"Acknowledged","OTP":"A085"},{"DisbursementNo":"DECON00094","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-01-22T00:00:00","Status":"Acknowledged","OTP":"A094"},{"DisbursementNo":"DECON00103","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-02-26T00:00:00","Status":"Acknowledged","OTP":"A103"},{"DisbursementNo":"DECON00112","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-04-09T00:00:00","Status":"Acknowledged","OTP":"A112"},{"DisbursementNo":"DECON00121","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-05-14T00:00:00","Status":"Acknowledged","OTP":"A121"},{"DisbursementNo":"DECON00129","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-06-18T00:00:00","Status":"Acknowledged","OTP":"A129"},{"DisbursementNo":"DECON00139","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-07-30T00:00:00","Status":"Acknowledged","OTP":"A139"},{"DisbursementNo":"DECON00147","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-09-03T00:00:00","Status":"In Transit","OTP":"A147"},{"DisbursementNo":"DECON00156","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-10-08T00:00:00","Status":"Acknowledged","OTP":"A156"},{"DisbursementNo":"DECON00165","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-11-12T00:00:00","Status":"In Transit","OTP":"A165"},{"DisbursementNo":"DECON00175","DepartmentId":"ECON","DepartmentName":"Economics","AcknowledgedBy":"a0e21bdc-6201-4c2b-8b13-54d82636e41b","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-12-24T00:00:00","Status":"Acknowledged","OTP":"A175"},{"DisbursementNo":"DENGL00005","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-01-23T00:00:00","Status":"Acknowledged","OTP":"A005"},{"DisbursementNo":"DENGL00022","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-04-03T00:00:00","Status":"Acknowledged","OTP":"A022"},{"DisbursementNo":"DENGL00032","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-05-15T00:00:00","Status":"Acknowledged","OTP":"A032"},{"DisbursementNo":"DENGL00041","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-06-19T00:00:00","Status":"Acknowledged","OTP":"A041"},{"DisbursementNo":"DENGL00049","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-07-24T00:00:00","Status":"Acknowledged","OTP":"A049"},{"DisbursementNo":"DENGL00058","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-08-28T00:00:00","Status":"Acknowledged","OTP":"A058"},{"DisbursementNo":"DENGL00068","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-10-09T00:00:00","Status":"Acknowledged","OTP":"A068"},{"DisbursementNo":"DENGL00077","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-11-13T00:00:00","Status":"Acknowledged","OTP":"A077"},{"DisbursementNo":"DENGL00086","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-12-18T00:00:00","Status":"Acknowledged","OTP":"A086"},{"DisbursementNo":"DENGL00095","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-01-22T00:00:00","Status":"Acknowledged","OTP":"A095"},{"DisbursementNo":"DENGL00104","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-03-05T00:00:00","Status":"Acknowledged","OTP":"A104"},{"DisbursementNo":"DENGL00113","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-04-09T00:00:00","Status":"Acknowledged","OTP":"A113"},{"DisbursementNo":"DENGL00122","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-05-14T00:00:00","Status":"Acknowledged","OTP":"A122"},{"DisbursementNo":"DENGL00130","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-06-18T00:00:00","Status":"Acknowledged","OTP":"A130"},{"DisbursementNo":"DENGL00140","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-07-30T00:00:00","Status":"Acknowledged","OTP":"A140"},{"DisbursementNo":"DENGL00148","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-09-03T00:00:00","Status":"In Transit","OTP":"A148"},{"DisbursementNo":"DENGL00157","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-10-08T00:00:00","Status":"Acknowledged","OTP":"A157"},{"DisbursementNo":"DENGL00166","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-11-19T00:00:00","Status":"In Transit","OTP":"A166"},{"DisbursementNo":"DENGL00176","DepartmentId":"ENGL","DepartmentName":"English","AcknowledgedBy":"93010c73-462d-46b2-b38d-b1bc6f399f6a","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-12-24T00:00:00","Status":"Acknowledged","OTP":"A176"},{"DisbursementNo":"DFINC00006","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-01-23T00:00:00","Status":"Acknowledged","OTP":"A006"},{"DisbursementNo":"DFINC00014","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-02-27T00:00:00","Status":"Acknowledged","OTP":"A014"},{"DisbursementNo":"DFINC00023","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-04-10T00:00:00","Status":"Acknowledged","OTP":"A023"},{"DisbursementNo":"DFINC00033","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-05-15T00:00:00","Status":"Acknowledged","OTP":"A033"},{"DisbursementNo":"DFINC00042","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-06-19T00:00:00","Status":"Acknowledged","OTP":"A042"},{"DisbursementNo":"DFINC00050","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-07-31T00:00:00","Status":"Acknowledged","OTP":"A050"},{"DisbursementNo":"DFINC00059","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-09-04T00:00:00","Status":"Acknowledged","OTP":"A059"},{"DisbursementNo":"DFINC00069","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-10-09T00:00:00","Status":"Acknowledged","OTP":"A069"},{"DisbursementNo":"DFINC00078","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-11-20T00:00:00","Status":"Acknowledged","OTP":"A078"},{"DisbursementNo":"DFINC00087","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-12-25T00:00:00","Status":"Acknowledged","OTP":"A087"},{"DisbursementNo":"DFINC00105","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-03-12T00:00:00","Status":"Acknowledged","OTP":"A105"},{"DisbursementNo":"DFINC00114","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-04-16T00:00:00","Status":"Acknowledged","OTP":"A114"},{"DisbursementNo":"DFINC00123","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-05-21T00:00:00","Status":"Acknowledged","OTP":"A123"},{"DisbursementNo":"DFINC00131","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-06-25T00:00:00","Status":"Acknowledged","OTP":"A131"},{"DisbursementNo":"DFINC00141","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-08-06T00:00:00","Status":"Acknowledged","OTP":"A141"},{"DisbursementNo":"DFINC00149","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-09-10T00:00:00","Status":"Acknowledged","OTP":"A149"},{"DisbursementNo":"DFINC00158","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-10-15T00:00:00","Status":"Acknowledged","OTP":"A158"},{"DisbursementNo":"DFINC00167","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-11-19T00:00:00","Status":"In Transit","OTP":"A167"},{"DisbursementNo":"DFINC00177","DepartmentId":"FINC","DepartmentName":"Finance","AcknowledgedBy":"c2d2de31-f94e-4e53-bc9f-64ebf3ad999c","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-12-31T00:00:00","Status":"Acknowledged","OTP":"A177"},{"DisbursementNo":"DHUMA00007","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-01-30T00:00:00","Status":"Acknowledged","OTP":"A007"},{"DisbursementNo":"DHUMA00015","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-03-06T00:00:00","Status":"Acknowledged","OTP":"A015"},{"DisbursementNo":"DHUMA00024","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-04-10T00:00:00","Status":"Acknowledged","OTP":"A024"},{"DisbursementNo":"DHUMA00034","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-05-22T00:00:00","Status":"Acknowledged","OTP":"A034"},{"DisbursementNo":"DHUMA00043","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-06-26T00:00:00","Status":"Acknowledged","OTP":"A043"},{"DisbursementNo":"DHUMA00051","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-07-31T00:00:00","Status":"Acknowledged","OTP":"A051"},{"DisbursementNo":"DHUMA00060","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-09-04T00:00:00","Status":"Acknowledged","OTP":"A060"},{"DisbursementNo":"DHUMA00070","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-10-16T00:00:00","Status":"Acknowledged","OTP":"A070"},{"DisbursementNo":"DHUMA00079","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-11-20T00:00:00","Status":"Acknowledged","OTP":"A079"},{"DisbursementNo":"DHUMA00088","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-12-25T00:00:00","Status":"Acknowledged","OTP":"A088"},{"DisbursementNo":"DHUMA00096","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-01-29T00:00:00","Status":"Acknowledged","OTP":"A096"},{"DisbursementNo":"DHUMA00106","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-03-12T00:00:00","Status":"Acknowledged","OTP":"A106"},{"DisbursementNo":"DHUMA00115","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-04-16T00:00:00","Status":"Acknowledged","OTP":"A115"},{"DisbursementNo":"DHUMA00124","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-05-21T00:00:00","Status":"Acknowledged","OTP":"A124"},{"DisbursementNo":"DHUMA00132","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-07-02T00:00:00","Status":"Acknowledged","OTP":"A132"},{"DisbursementNo":"DHUMA00142","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-08-06T00:00:00","Status":"Acknowledged","OTP":"A142"},{"DisbursementNo":"DHUMA00150","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2018-09-10T00:00:00","Status":"In Transit","OTP":"A150"},{"DisbursementNo":"DHUMA00159","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-10-22T00:00:00","Status":"Acknowledged","OTP":"A159"},{"DisbursementNo":"DHUMA00168","DepartmentId":"HUMA","DepartmentName":"Humanities","AcknowledgedBy":"0468dd23-2d20-4fae-b20c-8add2bce2d57","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-11-26T00:00:00","Status":"In Transit","OTP":"A168"},{"DisbursementNo":"DINTA00008","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-01-30T00:00:00","Status":"Acknowledged","OTP":"A008"},{"DisbursementNo":"DINTA00016","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-03-13T00:00:00","Status":"Acknowledged","OTP":"A016"},{"DisbursementNo":"DINTA00025","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-04-17T00:00:00","Status":"Acknowledged","OTP":"A025"},{"DisbursementNo":"DINTA00035","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2017-05-22T00:00:00","Status":"Acknowledged","OTP":"A035"},{"DisbursementNo":"DINTA00044","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-07-03T00:00:00","Status":"Acknowledged","OTP":"A044"},{"DisbursementNo":"DINTA00052","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-08-07T00:00:00","Status":"Acknowledged","OTP":"A052"},{"DisbursementNo":"DINTA00061","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-09-11T00:00:00","Status":"Acknowledged","OTP":"A061"},{"DisbursementNo":"DINTA00071","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-10-23T00:00:00","Status":"Acknowledged","OTP":"A071"},{"DisbursementNo":"DINTA00080","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-11-27T00:00:00","Status":"Acknowledged","OTP":"A080"},{"DisbursementNo":"DINTA00089","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-01-01T00:00:00","Status":"Acknowledged","OTP":"A089"},{"DisbursementNo":"DINTA00097","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-02-05T00:00:00","Status":"Acknowledged","OTP":"A097"},{"DisbursementNo":"DINTA00107","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-03-19T00:00:00","Status":"Acknowledged","OTP":"A107"},{"DisbursementNo":"DINTA00116","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-04-23T00:00:00","Status":"Acknowledged","OTP":"A116"},{"DisbursementNo":"DINTA00133","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2018-07-02T00:00:00","Status":"Acknowledged","OTP":"A133"},{"DisbursementNo":"DINTA00143","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-08-13T00:00:00","Status":"Acknowledged","OTP":"A143"},{"DisbursementNo":"DINTA00151","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-09-17T00:00:00","Status":"Acknowledged","OTP":"A151"},{"DisbursementNo":"DINTA00160","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-10-22T00:00:00","Status":"Acknowledged","OTP":"A160"},{"DisbursementNo":"DINTA00169","DepartmentId":"INTA","DepartmentName":"Internal Audit","AcknowledgedBy":"d98fc888-6091-4687-9fb3-21d9a23f2baa","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-11-26T00:00:00","Status":"Acknowledged","OTP":"A169"},{"DisbursementNo":"DSOSC00009","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-02-06T00:00:00","Status":"Acknowledged","OTP":"A009"},{"DisbursementNo":"DSOSC00017","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-03-13T00:00:00","Status":"Acknowledged","OTP":"A017"},{"DisbursementNo":"DSOSC00026","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-04-17T00:00:00","Status":"Acknowledged","OTP":"A026"},{"DisbursementNo":"DSOSC00036","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2017-05-29T00:00:00","Status":"Acknowledged","OTP":"A036"},{"DisbursementNo":"DSOSC00045","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-07-03T00:00:00","Status":"Acknowledged","OTP":"A045"},{"DisbursementNo":"DSOSC00053","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2017-08-07T00:00:00","Status":"Acknowledged","OTP":"A053"},{"DisbursementNo":"DSOSC00062","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-09-11T00:00:00","Status":"Acknowledged","OTP":"A062"},{"DisbursementNo":"DSOSC00072","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2017-10-23T00:00:00","Status":"Acknowledged","OTP":"A072"},{"DisbursementNo":"DSOSC00081","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"a8b8e62b-3c69-4a29-9ab3-91e96252aa51","Date":"2017-11-27T00:00:00","Status":"Acknowledged","OTP":"A081"},{"DisbursementNo":"DSOSC00090","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-01-01T00:00:00","Status":"Acknowledged","OTP":"A090"},{"DisbursementNo":"DSOSC00098","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-02-12T00:00:00","Status":"Acknowledged","OTP":"A098"},{"DisbursementNo":"DSOSC00108","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"db570c6f-bd42-4561-b73c-5faa23f819d3","Date":"2018-03-19T00:00:00","Status":"Acknowledged","OTP":"A108"},{"DisbursementNo":"DSOSC00117","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-04-23T00:00:00","Status":"Acknowledged","OTP":"A117"},{"DisbursementNo":"DSOSC00125","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-06-04T00:00:00","Status":"Acknowledged","OTP":"A125"},{"DisbursementNo":"DSOSC00134","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-07-09T00:00:00","Status":"Acknowledged","OTP":"A134"},{"DisbursementNo":"DSOSC00144","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-08-13T00:00:00","Status":"Acknowledged","OTP":"A144"},{"DisbursementNo":"DSOSC00152","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"4e858936-0926-4bde-9a5f-76129ab96941","Date":"2018-09-24T00:00:00","Status":"In Transit","OTP":"A152"},{"DisbursementNo":"DSOSC00161","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-10-29T00:00:00","Status":"Acknowledged","OTP":"A161"},{"DisbursementNo":"DSOSC00170","DepartmentId":"SOSC","DepartmentName":"Social Sciences","AcknowledgedBy":"47befd79-42fa-442b-9a4c-082def8496f7","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-12-03T00:00:00","Status":"Acknowledged","OTP":"A170"},{"DisbursementNo":"DSTAT00027","DepartmentId":"STAT","DepartmentName":"StationeryStore","AcknowledgedBy":"5042e8ca-ef83-4458-a78c-07e4f6ba0d1d","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-04-24T00:00:00","Status":"Acknowledged","OTP":"A027"},{"DisbursementNo":"DSTAT00063","DepartmentId":"STAT","DepartmentName":"StationeryStore","AcknowledgedBy":"5042e8ca-ef83-4458-a78c-07e4f6ba0d1d","DisbursedBy":"55b6143d-9d04-4bb5-8267-967602ac8405","Date":"2017-09-18T00:00:00","Status":"Acknowledged","OTP":"A063"},{"DisbursementNo":"DSTAT00099","DepartmentId":"STAT","DepartmentName":"StationeryStore","AcknowledgedBy":"5042e8ca-ef83-4458-a78c-07e4f6ba0d1d","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-02-12T00:00:00","Status":"Acknowledged","OTP":"A099"},{"DisbursementNo":"DSTAT00135","DepartmentId":"STAT","DepartmentName":"StationeryStore","AcknowledgedBy":"5042e8ca-ef83-4458-a78c-07e4f6ba0d1d","DisbursedBy":"55e3b8b7-61e5-4fb3-aa66-33540173bb51","Date":"2018-07-09T00:00:00","Status":"Acknowledged","OTP":"A135"},{"DisbursementNo":"DSTAT00171","DepartmentId":"STAT","DepartmentName":"StationeryStore","AcknowledgedBy":"5042e8ca-ef83-4458-a78c-07e4f6ba0d1d","DisbursedBy":"8714115e-b650-49fe-b6da-193d2eefd259","Date":"2018-12-03T00:00:00","Status":"Acknowledged","OTP":"A171"}]';

            Gson gson =new Gson();

            Type type=new TypeToken<ArrayList<DisbursementSationeryItem>>(){}.getType();
            List<DisbursementSationeryItem> disbursementitemList=gson.fromJson(result,type);

            return disbursementitemList;

        } catch (Exception e) {
            Log.e("Disbursement detail", e.toString());
        }
        return null;
    }

    @Override
    public String voidDisbursement(String disbno) {

        try {
            String url = String.format("%sclerk/voiddisb/%s", baseURL, disbno);

            String result = JSONParser.getStream(url);

            return result;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public String ackDisbursement(List<DisbursementSationeryItem> items) {

        try {
            String id = session.getUserid();
            AckDisbursement ackDisbursement=new AckDisbursement();
            ackDisbursement.setDisbursedBy(id);

            String url = String.format("%sclerk/acknowledgement", baseURL);
            ackDisbursement.setAckList(items);
            Gson gson = new Gson();
            String json = gson.toJson(ackDisbursement);

            String result = JSONParser.postStream(url, true, json);

            Log.e(TAG, "ackDisbursement: from API "+result );
            return result;

        } catch (Exception e) {
            Log.e("JsonPost", e.toString());
        }


        return null;
    }

    @Override
    public String returnSingleItem(ReturnItem item) {


       // api/returntowarehouse/return
        return null;
    }

    //endregion


    // region Author: Teh Li Heng for Delegate Department Head
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
            if (result != null && result != "")
                status = result;

        } catch (Exception e) {
            Log.e("JsonPost", e.toString());
        }
        return status;
    }
    //endregion


    //region Author:Gao Jiaxue Approve Stationery Request
    @Override
    public StationeryRequestApiModel GetStationeryRequest(String requestId) {
        String url = String.format("%s/%s/%s/%s", baseURL, "stationeryrequest", "getselected", requestId); //url to controller
        String result = JSONParser.getStream(url);
        Log.i("Json", result);
        Gson gson = new Gson();

        return gson.fromJson(result, StationeryRequestApiModel.class);
    }

    @Override
    public List<StationeryRequestApiModel> ReadStationeryRequest() {
        String id = session.getUserid();
        List<StationeryRequestApiModel> list = new ArrayList<StationeryRequestApiModel>();
        String url = String.format("%s%s/%s/%s", baseURL, "stationeryrequest", "getall", id);
        JSONArray a = JSONParser.getJSONArrayFromUrl(url);
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new StationeryRequestApiModel(
                        b.getString("RequestId"),
                        b.getString("RequestedBy"), "", "",
                        b.getString("RequestDate"),
                        b.getString("Status"), null
                ));
            }
        } catch (Exception e) {
            Log.e("StationeryRequest", "JSONArray error");
        }
        return (list);
    }

    /* Approve Request*/
    @Override
    public String ApproveStationeryRequest(StationeryRequestApiModel request) {
        String status = "Error at approve.";
        try {
            String url = String.format("%s/%s/%s/", baseURL, "stationeryrequest", "approve"); //url to controller
            Gson gson = new Gson();
            String json = gson.toJson(request);
            Log.i("Json", json);
            String result = JSONParser.postStream(url, true, json);
            Log.i("PostResult", result);
            String re = result.trim();
            if (re.equals("true")) {
                status = "Successfully approved.";
            }

        } catch (Exception e) {
            Log.e("JsonPost", e.toString());
        }
        Log.e("status", status);
        return status;
    }

    /* Reject Request*/
    @Override
    public String RejectStationeryRequest(StationeryRequestApiModel request) {
        String status = "Error at reject.";
        try {
            String url = String.format("%s/%s/%s/", baseURL, "stationeryrequest", "reject"); //url to controller
            Gson gson = new Gson();
            String json = gson.toJson(request);
            Log.i("Json", json);
            String result = JSONParser.postStream(url, true, json);
            Log.i("PostResult", result);
            String re = result.trim();
            if (re.equals("true")) {
                status = "Successfully rejected.";
            }
        } catch (Exception e) {
            Log.e("JsonPost", e.toString());
        }
        Log.e("status", status);
        return status;
    }

    //endregion

    // region Author: Teh Li Heng for Managing retrievals of clerk from warehouse
    @Override
    public ArrayList<StationeryRetrievalApiModel> RetrievalListGet() {
        try {
            //http://192.168.1.100/team7ad/api/
            String url = String.format("%sclerk/getretrievallist", baseURL);
            String result = JSONParser.getStream(url);
            Log.i("Json", result);

            Type stationeryType = new TypeToken<ArrayList<StationeryRetrievalApiModel>>() {
            }.getType();
            Gson gson = new Gson();
            ArrayList<StationeryRetrievalApiModel> sortedList = gson.fromJson(result, stationeryType);
            sortedList.sort(Comparator.comparing(StationeryRetrievalApiModel::getDescription));
            return sortedList;
        } catch (Exception e) {
            Log.e("Login", e.toString());
        }
        return null;
    }

    @Override
    public String RetrievalListSet(List<StationeryRetrievalApiModel> models) {
        String status = "Error at saving.";
        try {
            SetRetrievalApiModel apiModel = new SetRetrievalApiModel(session.getUserid(), models);

            //http://192.168.1.100/team7ad/api/
            String url = String.format("%sclerk/setretrievallist/", baseURL);
            Log.i("Url", url);
            Gson gson = new Gson();
            String json = gson.toJson(apiModel);
            Log.i("Json", json);
            String result = JSONParser.postStream(url, true, json);
            Log.i("PostResult", result);

            if (result != null && result != "")
                status = "Successfully saved.";

        } catch (Exception e) {
            Log.e("JsonPost", e.toString());
        }
        return status;
    }

    //endregion

    //region Author: Zan Tun Khine (Approve/Reject PO & Acknowledge Delivery)

    @Override
    public List<PendingPO> GetPendingPO() {
        String url = String.format("%s/pendingpo", baseURL);
        List<PendingPO> listPO = new ArrayList<>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(url);
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                listPO.add(new PendingPO(
                        b.getString("PONo"),
                        b.getString("SupplierId"),
                        b.getString("Status"),
                        b.getString("OrderedBy"),
                        b.getString("Date"),
                        b.getString("Amount"),
                        b.getString("ApprovedBy")));
            }
        } catch (Exception e) {
            Log.e("PendingPO", "JSONArray error");
        }
        return (listPO);
    }

    @Override
    public void ApproveRejectPO(PendingPO po, int btn) {
        JSONObject jpo = new JSONObject();

        try {
            jpo.put("PONo", po.get("PONo"));
            //jpo.put("SupplierId",po.get("SupplierId"));
            //jpo.put("Status",po.get("Status"));
            //jpo.put("OrderedBy",po.get("OrderedBy"));
            //jpo.put("Date",po.get("Date"));
            //jpo.put("Amount",po.get("Amount"));
            jpo.put("ApprovedBy", po.get("ApprovedBy"));

        } catch (Exception e) {
            Log.e("PendingPO", "Error");
        }

        if (btn == R.id.poButtonApprove)
            JSONParser.postStream(baseURL + "/pendingpo/approve",true, jpo.toString());

        else if (btn == R.id.poButtonReject)
            JSONParser.postStream(baseURL + "/pendingpo/reject", true,jpo.toString());
    }

    @Override
    public List<PendingPODetails> GetPendingPODetails(String pono) {
        String url = String.format("%s/pendingpo/%s", baseURL, pono);
        List<PendingPODetails> listPendingPO = new ArrayList<>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(url);
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                listPendingPO.add(new PendingPODetails(
                        b.getString("Description"),
                        b.getString("ItemId"),
                        b.getString("Quantity"),
                        b.getString("Remarks"),
                        b.getString("TransactionRef"),
                        String.format("%,.2f", b.getDouble("UnitPrice")),
                        b.getString("PONo"),
                        b.getString("SupplierId"),
                        b.getString("Status"),
                        b.getString("OrderedBy"),
                        b.getString("Date"),
                        String.format("%,.2f", b.getDouble("Amount")),
                        String.format("%,.2f", b.getDouble("UnitAmount"))));
            }
        } catch (Exception e) {
            Log.e("PendingPODetails", "JSONArray error");
        }
        return (listPendingPO);
    }

    @Override
    public List<String> GetPendingPOList() {
        String url = String.format("%s/ackdelivery/pendingdelivery", baseURL);
        List<String> listPO = new ArrayList<>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(url);
            for (int i = 0; i < a.length(); i++) {
                String po = a.getString(i);
                listPO.add(po);
            }
        } catch (Exception e) {
            Log.e("PendingDelivery", "JSONArray error");
        }
        return (listPO);
    }

    @Override
    public void ConfirmDO(List<AckDeliveryDetails> ackDOList) {

        JSONArray jpoArr = new JSONArray();
        try {

            for (AckDeliveryDetails ackDO : ackDOList) {
                JSONObject jpo = new JSONObject();
                jpo.put("ItemId", ackDO.get("ItemId"));
                jpo.put("Quantity", ackDO.get("Quantity"));
//                jpo.put("Remarks", ackDO.get("Remarks"));
//                jpo.put("UnitPrice", ackDO.get("UnitPrice"));
                jpo.put("DelOrderNo", ackDO.get("DelOrderNo"));
                jpo.put("SupplierId", ackDO.get("SupplierId"));
                jpo.put("AcceptedBy", ackDO.get("AcceptedBy"));
                jpo.put("PONo", ackDO.get("PONo"));
                jpoArr.put(jpo);
                Log.d("Json",jpoArr.toString());
            }

        } catch (Exception e) {
            Log.e("AckDeliveryDetails", "Error");
        }
        JSONParser.postStream(baseURL + "ackdelivery/addmm",true, jpoArr.toString());
    }

    //endregion


    //region Cheng Zongpei
    public List<String> adjustmentGetCategories(){
        String url = String.format("http://%s/team7ad/adjustment/categories", host);
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
        String url = String.format("http://%s/team7ad/adjustment/items/%s", host, category);
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
        String url = String.format("http://%s/team7ad/adjustment/item/%s", host, itemId);
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

    public String adjustmentGetEmail(double amount){
        String url = String.format("http://%s/team7ad/adjustment/email/%f",host,amount);
        return JSONParser.getStream(url);
    }

    public String adjustmentSet(List<AdjustmentInfo> adjustment){

        JSONArray array = new JSONArray();
        try{
            for(AdjustmentInfo info : adjustment){
                JSONObject object = new JSONObject();
                object.put("itemId",info.itemId);
                object.put("quantity",info.quantity);
                object.put("remark", info.remark);
                array.put(object);
            }
        }catch(Exception e){
            Log.e("error","Json save error");
        }

        String result = JSONParser.postStream(String.format("http://%s/team7ad/adjustment/save", host), true, array.toString());
        return result;
    }

    //endregion
}
