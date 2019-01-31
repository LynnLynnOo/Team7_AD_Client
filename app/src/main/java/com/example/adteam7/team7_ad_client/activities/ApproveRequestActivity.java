package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.RequestDetailAdaptor;
import com.example.adteam7.team7_ad_client.data.RequestTransactionDetailApiModel;
import com.example.adteam7.team7_ad_client.data.SessionManager;
import com.example.adteam7.team7_ad_client.data.StationeryRequestApiModel;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;
import com.example.adteam7.team7_ad_client.network.SendMailTask;

import java.util.List;

//region Author:Gao Jiaxue
public class ApproveRequestActivity extends AppCompatActivity {

    SessionManager session = SessionManager.getInstance();
    APIDataAgent agent = new APIDataAgentImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_request);
        /* get requestid*/
        Intent i = getIntent();
        String rid = i.getStringExtra("rid");
        Log.e("Rid", rid);
        new AsyncCallerGet().execute(rid);
        /*after click approve button*/
        final Button appbutton = findViewById(R.id.reqButtonApprove);
        appbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AsyncCallerApprove().execute(rid);
            }
        });
        /*after click reject button*/
        final Button rejbutton = findViewById(R.id.reqButtonReject);
        rejbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AsyncCallerReject().execute(rid);
            }
        });
    }

    void show(StationeryRequestApiModel request) {
        TextView txt1 = findViewById(R.id.text1);
        txt1.setText(request.getRequestId());
        TextView txt2 = findViewById(R.id.text2);
        txt2.setText(request.getRequestedBy());
        TextView txt3 = findViewById(R.id.text3);
        txt3.setText(request.getStatus());
        TextView txt4 = findViewById(R.id.text4);
        txt4.setText(request.getRequestDate());
        List<RequestTransactionDetailApiModel> transactionDetails = request.getRequestTransactionDetailApiModels();

        // ConstraintLayout approveReqView = findViewById(R.id.approveRequest_layout);
        /* LinearLayout approveReqView = findViewById(R.id.items);*/

        double sum = 0;
        for (int i = 0; i < transactionDetails.size(); i++) {
            int quantity = Integer.parseInt(transactionDetails.get(i).getQuantity());
            double unitPrice = Double.parseDouble(transactionDetails.get(i).getUnitPrice());
            String s = Double.toString(unitPrice);
            Log.e("i", s);
            sum = sum + quantity * unitPrice;
        }
        String total = new Double(sum).toString();
        TextView reqtotal = findViewById(R.id.text5);
        reqtotal.setText(total);
        if (!"Pending Approval".equals(request.getStatus().trim())) {
            String x = Boolean.toString("Pending Approval".equals(request.getStatus().trim()));
            Log.e("equal", x);
            View app = findViewById(R.id.reqButtonApprove);
            app.setVisibility(View.GONE);
            View rej = findViewById(R.id.reqButtonReject);
            rej.setVisibility(View.GONE);
        }
    }

    private class AsyncCallerApprove extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = session.getUsername();
            StationeryRequestApiModel exactReq = agent.GetStationeryRequest(params[0]);
            exactReq.setApprovedBy(name);
            String result = agent.ApproveStationeryRequest(exactReq);
            Log.e("result", result);
            String title = "Request Approved!";
            String body = "Your request was approved!";
            new SendMailTask(ApproveRequestActivity.this).execute("team7logicdb@gmail.com", title, body);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ApproveRequestActivity.this, result, Toast.LENGTH_SHORT).show();
            Log.e("Approve", "no wrong till here");
            Intent intent = new Intent(getApplicationContext(), ViewRequestActivity.class);
            startActivity(intent);
        }
    }

    private class AsyncCallerReject extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = session.getUsername();
            StationeryRequestApiModel exactReq = agent.GetStationeryRequest(params[0]);
            exactReq.setApprovedBy(name);
            String result = agent.RejectStationeryRequest(exactReq);
            String title = "Request Rejected!";
            String body = "Your request was rejected!";
            new SendMailTask(ApproveRequestActivity.this).execute("team7logicdb@gmail.com", title, body);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ApproveRequestActivity.this, result, Toast.LENGTH_SHORT).show();
            Log.e("Reject", "no wrong till here");
            Intent intent = new Intent(getApplicationContext(), ViewRequestActivity.class);
            startActivity(intent);
        }
    }

    private class AsyncCallerGet extends AsyncTask<String, Void, StationeryRequestApiModel> {
            @Override
            protected StationeryRequestApiModel doInBackground(String... params) {
                return agent.GetStationeryRequest(params[0]);
            }

            @Override
            protected void onPostExecute(StationeryRequestApiModel req) {
                List<RequestTransactionDetailApiModel> result = req.getRequestTransactionDetailApiModels();
                RequestDetailAdaptor adapter = new RequestDetailAdaptor(getApplicationContext(), result);
                Log.i("already", "view");
                ListView list = (ListView) findViewById(R.id.req_item_listview);
                list.setAdapter(adapter);
                Log.e("PostExecute", "no wrong till here");
                show(req);
            }
    }

}
//endregion
